package steps;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.*;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Step;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestStep;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class Hook {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<List<TestStep>> steps = ThreadLocal.withInitial(ArrayList::new);
    private static ThreadLocal<Integer> currentStepIndex = ThreadLocal.withInitial(() -> 0);
    public static ExtentReports extent;
    public static ExtentTest test;

    public Hook() {
    }

    @Before
    public void initializeTest(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest(scenario.getName());


        Field f = scenario.getClass().getDeclaredField("delegate");
        f.setAccessible(true);
        TestCaseState testCaseState = (TestCaseState) f.get(scenario);
        Field testCaseField = testCaseState.getClass().getDeclaredField("testCase");
        testCaseField.setAccessible(true);
        TestCase testCase = (TestCase) testCaseField.get(testCaseState);
        List<TestStep> testStepList = testCase.getTestSteps();
        steps.set(new ArrayList<>());
        for (TestStep testStep : testStepList) {
            if (testStep instanceof PickleStepTestStep) {
                steps.get().add(testStep);
            }
        }


        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        driver.set(new ChromeDriver(chromeOptions));
        driver.get().manage().window().maximize();
    }

    @BeforeStep
    public void beforeEveryStep(Scenario scenario) {
        PickleStepTestStep testStep = (PickleStepTestStep) steps.get().get(currentStepIndex.get());
        String stepName = testStep.getStep().getText();
        System.out.println("Execute step: " + stepName);
        test.createNode(stepName);
    }


    @AfterStep
    public void afterEveryStep(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                // Capture full-page screenshot
                File fullScreenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
                byte[] fullFileContent = Files.readAllBytes(fullScreenshot.toPath());
                scenario.attach(fullFileContent, "image/png", "Full-page screenshot for step: " + scenario.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentStepIndex.set(currentStepIndex.get() + 1);
    }
    @After
    public void tearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println(scenario.getName());
            test.fail("Scenario failed: " + scenario.getName());
        }else {
            test.pass("Scenario passed: " + scenario.getName());
        }
        driver.get().quit();
        extent.flush();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }
}
