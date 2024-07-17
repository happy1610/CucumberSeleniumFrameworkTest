package awesomecucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

@CucumberOptions(
        plugin = { "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        features = "src/test/resources/awesomecucumber",
        glue = {"awesomecucumber"}
)
public class MyRunnerTest extends AbstractTestNGCucumberTests {
    public static String browserName;

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @Parameters({"browserName"})
    @BeforeClass
    public static void setBrowser(String browser) throws Exception {
        browserName =  browser.toUpperCase();
    }

    @AfterClass
    public static void quit(){
        browserName = null;
    }
}
