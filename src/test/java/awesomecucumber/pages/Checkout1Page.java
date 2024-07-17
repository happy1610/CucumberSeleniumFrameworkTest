package awesomecucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Checkout1Page extends BasePage{

    @FindBy(id = "first-name") private static WebElement txtFirstName;
    @FindBy(id = "last-name") private static WebElement txtLastName;
    @FindBy(id = "postal-code") private static WebElement txtPostalCode;
    @FindBy(id = "continue") private static WebElement btnContinue;
    public Checkout1Page(WebDriver driver) {
        super(driver);
    }
    public static Checkout2Page inputCheckoutInfo(){
        txtFirstName.sendKeys("Test");
        txtLastName.sendKeys("Test");
        txtPostalCode.sendKeys("Test");
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
        return new Checkout2Page(driver);
    }
}
