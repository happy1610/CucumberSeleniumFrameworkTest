package steps;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
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
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Hook {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<Map<Integer, List<TestStep>>> steps = ThreadLocal.withInitial(() -> new HashMap<>());
    private static ThreadLocal<Map<Integer, Integer>> currentStepIndex = ThreadLocal.withInitial(() -> new HashMap<>());
    private static ThreadLocal<Map<Integer, String>> currentStepName = ThreadLocal.withInitial(() -> new HashMap<>());
    public static ExtentReports extent;
    public static ExtentTest test;

    public Hook() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Before
    public void initializeTest(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        test = extent.createTest(scenario.getName());

        Field f = scenario.getClass().getDeclaredField("delegate");
        f.setAccessible(true);
        TestCaseState testCaseState = (TestCaseState) f.get(scenario);
        Field testCaseField = testCaseState.getClass().getDeclaredField("testCase");
        testCaseField.setAccessible(true);
        TestCase testCase = (TestCase) testCaseField.get(testCaseState);
        List<TestStep> testStepList = testCase.getTestSteps();
        steps.get().put((int) Thread.currentThread().getId(), new ArrayList<>());
        System.out.println("Initializing steps for thread: " + Thread.currentThread().getId());
        for (TestStep testStep : testStepList) {
            if (testStep instanceof PickleStepTestStep) {
                steps.get().get((int) Thread.currentThread().getId()).add(testStep);
            }
        }


        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        driver.set(new ChromeDriver(chromeOptions));
        driver.get().manage().window().maximize();
    }

    public void beforeEveryStep(Scenario scenario) {
        PickleStepTestStep testStep = (PickleStepTestStep) steps.get().get(currentStepIndex.get());
        String stepName = testStep.getStep().getText();
        System.out.println("Execute step: " + stepName);
        currentStepName.get().put((int) Thread.currentThread().getId(), stepName);
    }


    @AfterStep
    public void afterEveryStep(Scenario scenario) {
        try {
            // Capture full-page screenshot
            String fullScreenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BASE64);
            int threadId = (int) Thread.currentThread().getId();
            if (scenario.isFailed()) {
                test.log(Status.FAIL, currentStepName.get().get(threadId), com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(fullScreenshot).build());
            } else {
                test.log(Status.PASS, currentStepName.get().get(threadId), com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(fullScreenshot).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentStepIndex.get().put((int) Thread.currentThread().getId(), currentStepIndex.get().getOrDefault((int) Thread.currentThread().getId(), 0) + 1);
    }

    @After
    public void tearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println(scenario.getName());
            test.fail("Scenario failed: " + scenario.getName());
        } else {
            test.pass("Scenario passed: " + scenario.getName());
        }
        driver.get().quit();
        currentStepIndex.remove();
        currentStepName.remove();
        steps.remove();
    }

    @AfterClass
    public static void afterClass(){
        extent.flush();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }
}
