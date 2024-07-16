package awesomecucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductsStepsDef {

    @Given("^a user in the Home Page within \"([^\"]*)\" section$")
    public void a_user_in_the_home_page_within_something_section(String tittleHomePage){

    }

    @When("^search for (.+)$")
    public void search_for(String productName){

    }

    @When("^add to the cart the (.+) just found$")
    public void add_to_the_cart_the_just_found(String productName){

    }

    @When("^Add to the cart a second (.+)$")
    public void add_to_the_cart_a_second(String secondProductName){
    }

    @When("^Add to the cart a third (.+)$")
    public void add_to_the_cart_a_third(String thirdProductName){
    }

    @When("^navigate to Your Cart section$")
    public void navigate_to_your_cart_section(){
    }

    @Then("^the product (.+) should not be listed on Product Section$")
    public void the_product_should_not_be_listed_on_product_section(String productName){

    }

    @Then("^the product (.+) should be listed on Product Section$")
    public void the_product_should_be_listed_on_product_section(String productName){
    }

    @Then("^the user should be located in \"([^\"]*)\" section$")
    public void the_user_should_be_located_in_something_section(String tittleYourCartPage){
    }

    @Then("^the product (.+) should be listed on Your Cart Section with expected (.+)$")
    public void the_product_should_be_listed_on_your_cart_section_with_expected(String productName, String productPrice){

    }
}

