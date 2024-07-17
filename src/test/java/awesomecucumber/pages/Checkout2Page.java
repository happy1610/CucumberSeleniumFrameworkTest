package awesomecucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Checkout2Page extends BasePage{
    @FindBy(id = "finish") private static WebElement btnFinish;
    public Checkout2Page(WebDriver driver) {
        super(driver);
    }
    public static CheckoutCompletePage clickFinish(){
        wait.until(ExpectedConditions.elementToBeClickable(btnFinish)).click();
        return new CheckoutCompletePage(driver);
    }
}
