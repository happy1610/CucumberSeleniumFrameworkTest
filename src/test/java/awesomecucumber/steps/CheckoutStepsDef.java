package awesomecucumber.steps;

import awesomecucumber.constants.EndPoints;
import awesomecucumber.factory.DriverManager;
import awesomecucumber.pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class CheckoutStepsDef {
    private WebDriver driver;
    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;
    Checkout1Page checkout1Page;
    Checkout2Page checkout2Page;
    CheckoutCompletePage checkoutCompletePage;

    @Given("login_success")
    public void login_success() {
        driver = DriverManager.getDriver();
        new LoginPage(driver).loadEndpoint(EndPoints.BASEPAGE.url);
        LoginPage.setUserName();
        LoginPage.setUserPass();
        productsPage = loginPage.clickLoginButton();
    }

    @When("addCart_and_Checkout")
    public void addcartAndCheckout() {
        cartPage = productsPage.AddCartToCheckout();
    }

    @And("click Checkout")
    public void clickCheckout() {
        checkout1Page = cartPage.clickCheckout();
    }


    @And("Input Checkout info and Continue")
    public void input_and_clickContinue() {
        checkout2Page = checkout1Page.inputCheckoutInfo();
    }

    @And("Click Finish")
    public void click_Finish() {
        checkoutCompletePage = checkout2Page.clickFinish();
    }

    @Then("Checkout is completed")
    public void checkout_Complete() {
        checkoutCompletePage.verifyCompleteCheckout();
    }

}
