package awesomecucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage{
    @FindBy(id = "checkout") private static WebElement btnCheckout;
    public CartPage(WebDriver driver) {
        super(driver);
    }
    public static Checkout1Page clickCheckout(){
        wait.until(ExpectedConditions.elementToBeClickable(btnCheckout)).click();
        return new Checkout1Page(driver);
    }
}
