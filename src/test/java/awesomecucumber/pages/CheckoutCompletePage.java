package awesomecucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class CheckoutCompletePage extends BasePage{
    @FindBy(xpath = "//*[@id=\"checkout_complete_container\"]/h2") private static WebElement msgCompleteHeader;
    @FindBy(xpath = "//*[@id=\"checkout_complete_container\"]/div") private static WebElement msgCompleteText;
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }
    public void verifyCompleteCheckout() {
        String expectedHeader = "Thank you for your order!";
        String expectedText = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
        String actualHeader = wait.until(ExpectedConditions.visibilityOf(msgCompleteHeader)).getText();
        String actualText = wait.until(ExpectedConditions.visibilityOf(msgCompleteText)).getText();
        Assert.assertEquals(actualHeader, expectedHeader,"Verify Header");
        Assert.assertEquals(actualText, expectedText,"Verify Text");
        System.out.println("===================Checkout Completed====================");
    }
}
