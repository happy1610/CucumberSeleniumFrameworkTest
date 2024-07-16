package awesomecucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.List;


public class ProductsPage extends BasePage{
    @FindBy(className = "title") private WebElement tittlePage;
    @FindBy(id = "shopping_cart_container")private WebElement yourCartButton;

    List<String> nameProductList = new ArrayList<>();
    String firstBlockForAddCartXpath = "//div[@id='inventory_container']//div[@class='inventory_item']//div[@class='inventory_item_description']//div[@class='inventory_item_label']//a//div/following::div[@class=\"pricebar\"][";

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getTittlePage() {
        return wait.until(ExpectedConditions.visibilityOf(tittlePage))
                .getText();
    }
}
