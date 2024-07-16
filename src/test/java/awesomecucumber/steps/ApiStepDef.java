package awesomecucumber.steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import awesomecucumber.apis.*;
import org.testng.Assert;

public class ApiStepDef {

    public int statusCode;


    @When("send a Get request to the Endpoint")
    public void sendAGetRequestToTheEndpoint() {
        statusCode = ApiTest2.validate_get_status_code_Postman();
    }

    @Then("status code is {int}")
    public void statusCodeIs(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, statusCode);
    }
}
