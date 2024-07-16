package awesomecucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    @FindBy(id = "user-name") private static WebElement userNameField;
    @FindBy(id = "password") private static WebElement userPassField;
    @FindBy(id = "login-button") private static WebElement loginButton;
    @FindBy(xpath = "//h3[@data-test='error']") private static WebElement loginErrorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static void setUserName(){
        userNameField.sendKeys("standard_user");
    }

    public static void setWrongUserName(){
        userNameField.sendKeys("WrongUserName");
    }

    public static void setUserPass(){
        userPassField.sendKeys("secret_sauce");
    }

    public static ProductsPage clickLoginButton(){
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new ProductsPage(driver);
    }

    public static String getTextLoginErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(loginErrorMessage))
                .getText();
    }

    public static void setUserNameDB(String userNameDB){
        userNameField.sendKeys(userNameDB);
    }

    public static void setUserPassDB(String userPassDB){
        userPassField.sendKeys(userPassDB);
    }
}