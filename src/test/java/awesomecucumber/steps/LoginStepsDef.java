package awesomecucumber.steps;

import awesomecucumber.pages.ProductsPage;
import awesomecucumber.constants.EndPoints;
import awesomecucumber.factory.DriverManager;
import awesomecucumber.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Objects;


public class LoginStepsDef {
    private WebDriver driver;
    private boolean result = false;
    ProductsPage productsPage;

    @Given("I'm on the Login Page")
    public void i_m_on_the_login_page() {
        driver = DriverManager.getDriver();
        new LoginPage(driver).loadEndpoint(EndPoints.BASEPAGE.url);
    }

    @When("set the UserName and Password")
    public void set_the_username_and_password() {
        LoginPage.setUserName();
        LoginPage.setUserPass();
    }

    @When("set a wrong UserName and Password")
    public void setAWrongUserNameAndPassword() {
        LoginPage.setWrongUserName();
        LoginPage.setUserPass();
    }

    @And("click LoginButton")
    public void clickLoginButton() {
        productsPage = LoginPage.clickLoginButton();
    }

    @Then("the user is on {string} Page")
    public void theUserIsOnPage(String pageName) {
        productsPage = new ProductsPage(driver);
        Assert.assertEquals(pageName, productsPage.getTittlePage());
    }

    @Then("the system throws the error {string} message")
    public void theSystemThrowsTheErrorMessage(String loginErrorMessage) {
        if (Objects.equals(LoginPage.getTextLoginErrorMessage(), loginErrorMessage)) {
            result = true;
        }
        Assert.assertTrue(result);
        result = false;
    }


}
