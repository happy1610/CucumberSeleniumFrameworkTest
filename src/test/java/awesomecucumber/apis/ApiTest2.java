package awesomecucumber.apis;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest2 {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        PrintStream FileOutputStream = new PrintStream("requestAndResponseInfo.log");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                addFilter(new RequestLoggingFilter(FileOutputStream)).
                addFilter(new ResponseLoggingFilter(FileOutputStream));
        requestSpecification = requestSpecBuilder.build();
    }


    public static int validate_get_status_code() {
        int statusCode = given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                queryParam("id", 1005).
                when().
                get("/product/read_one.php").
                then().
                log().all().
                assertThat().statusCode(200).extract().statusCode();
        System.out.println("------------------------------------------------------------------------");
        return statusCode;
    }

    public static int validate_get_status_code_Postman() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("foo1", "jq1");
        queryParams.put("foo2", "jq2");
        queryParams.put("foo3", "jq3");

        int statusCode = given().
                baseUri("https://postman-echo.com").
                header("Accept", "*/*").
                queryParams(queryParams).
                when().
                get("/get").
                then().
                log().all().
                assertThat().statusCode(200).extract().statusCode();
        System.out.println("------------------------------------------------------------------------");
        return statusCode;
    }

    public void multiple_query_parameters() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("foo1", "jq1");
        queryParams.put("foo2", "jq2");
        queryParams.put("foo3", "jq3");

        given().
                baseUri("https://postman-echo.com").
                header("Accept", "*/*").
                queryParams(queryParams).
                when().
                get("/get").
                then().
                log().body().
                assertThat().statusCode(200);
        System.out.println("------------------------------------------------------------------------");
    }

    public void multiple_value_query_parameters() {
        given().
                baseUri("https://postman-echo.com").
                header("Accept", "*/*").
                queryParams("foo1","jq1, jq2, jq3").
                when().
                get("/get").
                then().
                log().body().
                assertThat().statusCode(200);
        System.out.println("------------------------------------------------------------------------");
    }

    public void path_parameter() {
        given().
                baseUri("https://petstore.swagger.io").
                pathParam("petId", "9223372036854646836").
                when().
                get("/v2/pet/{petId}").
                then().
                log().body().
                assertThat().statusCode(200);
        System.out.println("------------------------------------------------------------------------");
    }

    public void validate_get_response_body() {
        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                queryParam("id", 1005).
                when().
                get("/product/read.php").
                then().
                log().all().
                assertThat().statusCode(200).
                body("records.id", hasItems("1", "2", "3"),
                        "records.name", hasItems("Chevrolet Camaro Jq!!!", "Bamboo Thermal Ski Coat", "Cross-Back Training Tank"),
                        "records.description[0]", equalTo("Best Car Updated"),
                        "records.name", hasItem("Chevrolet Camaro"));
        System.out.println("------------------------------------------------------------------------");
    }

    public void validate_get_response_body_not_has_item() {
        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                when().
                get("/product/read.php").
                then().
                log().all().
                assertThat().statusCode(200).
                body("records.id", hasItems("1", "2", "3"),
                        "records.name", hasItems("Chevrolet Camaro Jq!!!", "Bamboo Thermal Ski Coat", "Cross-Back Training Tank"),
                        "records.description[0]", equalTo("Best Car Updated"),
                        "records.name", hasItem("Chevrolet Camaro"),
                        "records.name", not(hasItem("RestAssured")));
        System.out.println("------------------------------------------------------------------------");
    }

    public void extract_response() {
        Response response = given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                queryParam("id", 1005).
                when().
                get("/product/read_one.php").
                then().
                assertThat().statusCode(200).
                extract().response();
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asString());
    }

    public void extract_single_response() {
        ArrayList<String> response = given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(200).
                extract().response().path("records.name"); //With Groovy GPath

        System.out.println("Size: " + response.size());
        for (String name : response) {
            System.out.println(name);
        }
    }

    public void hamcrest_assert_on_extracted_response() {
        String response = given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(200).
                extract().response().path("records[0].name");

        assertThat(response,equalTo("Chevrolet Camaro Jq!!!"));
        Assert.assertEquals(response,"Chevrolet Camaro Jq!!!");

        System.out.println("------------------------------------------------------------------------");
    }

    public void collection_is_not_empty() {
        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(200).
                body("records", is(not(emptyArray())));

        System.out.println("------------------------------------------------------------------------");
    }

    public void collection_size() {
        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(200).
                body("records", hasSize(20));

        System.out.println("------------------------------------------------------------------------");
    }

    public void collection_everyItem_starts_with() {
        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(200).
                body("records", not(startsWith("Hello")));

        System.out.println("------------------------------------------------------------------------");
    }

    public void collection_has_key_value_and_entry() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                log().headers().
                when().
                get("/product/read.php").
                then().
                log().body().
                assertThat().statusCode(200).
                body("records[0]", hasKey("id"),
                        "records[0]", hasValue("Best Car Updated"),
                        "records[0]", hasEntry("id", "1005"));

        System.out.println("------------------------------------------------------------------------");
    }

    public void log_if_error() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("http://localhost:80/api_testing").
                header("Accept", "*/*").
                log().all().
                when().
                get("/product/read.p"). //Bad endpoint
                then().
                log().ifError().
                assertThat().statusCode(200).
                body("records[0]", hasKey("id"),
                        "records[0]", hasValue("Best Car Updated"),
                        "records[0]", hasEntry("id","1005"));

        System.out.println("------------------------------------------------------------------------");
    }

    public void log_only_if_validation_fails() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("http://localhost:80/api_testing").
                headers("Accept", "*/*","Content-Type","application/json").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(8000).
                body("records[0]", hasKey("id"),
                        "records[0]", hasValue("Best Car Updated"),
                        "records[0]", hasEntry("id","1005"));

        System.out.println("------------------------------------------------------------------------");
    }

    public void log_blacklist_header() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("http://localhost:80/api_testing").
                headers("Accept", "*/*","Content-Type","application/json").
                config(config.logConfig(LogConfig.logConfig().blacklistHeader("Accept","Content-Type"))).
                log().all().
                when().
                get("/product/read.php").
                then().
                assertThat().statusCode(200).
                body("records[0]", hasKey("id"),
                        "records[0]", hasValue("Best Car Updated"),
                        "records[0]", hasEntry("id","1005"));

        System.out.println("------------------------------------------------------------------------");
    }

    public void oauth_token() {
        System.out.println("------------------------------------------------------------------------");

        String response = given().
                baseUri("https://dev-ai2vidk6.us.auth0.com").
                contentType("application/x-www-form-urlencoded; charset=utf-8").
                formParam("client_id", "BSiWAOBjwWZyomfDhzp2yX1qktY5ecdD").
                formParam("client_secret", "your client secret").
                formParam("audience", "udemy-api").
                formParam("grant_type", "client_credentials").
                when().
                post("/oauth/token").
                then().
                log().body().
                assertThat().statusCode(200).
                headers("Content-Type","application/json","Server","cloudflare").
                extract().response().path("access_token");

        System.out.println("--------------------------- " + response + " -------------------------------");
    }

    public void oauth_token_encoder_config() {
        System.out.println("------------------------------------------------------------------------");

        String response = given().
                baseUri("https://dev-ai2vidk6.us.auth0.com").
                config(config().encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                formParam("client_id", "BSiWAOBjwWZyomfDhzp2yX1qktY5ecdD").
                formParam("client_secret", "your client secret").
                formParam("audience", "udemy-api").
                formParam("grant_type", "client_credentials").
                when().
                post("/oauth/token").
                then().
                log().body().
                assertThat().statusCode(200).
                headers("Content-Type","application/json","Server","cloudflare").
                extract().response().path("access_token");

        System.out.println("--------------------------- " + response + " -------------------------------");
    }

    public void oauth_token_with_Json_file() {
        System.out.println("------------------------------------------------------------------------");

        File file = new File("src/main/resources/Authentication.json");

        String response = given().
                baseUri("https://dev-ai2vidk6.us.auth0.com").
                header("content-type","application/json").
                body(file).
                when().
                post("/oauth/token").
                then().
                log().body().
                assertThat().statusCode(200).
                headers("Content-Type","application/json","Server","cloudflare").
                extract().response().path("access_token");

        System.out.println("--------------------------- " + response + " -------------------------------");
    }

    public void create_workspace_postman() {
        System.out.println("------------------------------------------------------------------------");

        String body = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"TestJQ\",\n" +
                "        \"description\": \"Just a test\",\n" +
                "        \"type\": \"personal\"\n" +
                "    }\n" +
                "}";

        String response = given().
                baseUri("https://api.getpostman.com").
                queryParam("apikey", "your api key").
                body(body).
                when().
                post("/workspaces").
                then().
                log().body().
                assertThat().statusCode(200).
                extract().body().path("workspace.name");

        System.out.println("--------------------- " + response + " ----------------------------------");
    }

    public void create_workspace_postman_Json_File() {
        System.out.println("------------------------------------------------------------------------");

        File file = new File("src/main/resources/CreateWorkspacePayload.json");

        String response = given().
                baseUri("https://api.getpostman.com").
                header("content-type","application/json").
                queryParam("apikey", "your api key").
                body(file).
                when().
                post("/workspaces").
                then().
                log().body().
                assertThat().statusCode(200).
                extract().body().path("workspace.name");

        System.out.println("--------------------- " + response + " ----------------------------------");
    }

    public void multipart_form_data() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("https://postman-echo.com").
                multiPart("foo1", "JQ1").
                multiPart("foo2", "JQ2").
                when().
                post("/post").
                then().
                log().body().
                assertThat().statusCode(200);

        System.out.println("--------------------------------------------------");
    }

    public void validateJsonSchema() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("https://postman-echo.com").
                queryParam("foo1", "JQ1").
                queryParam("foo2", "JQ2").
                queryParam("foo3", "JQ3").
                headers("x-forwarded-proto","https","x-forwarded-port","443","host","postman-echo.com",
                        "accept","*/*", "accept-encoding","gzip, deflate, br","cookie","sails.sid=s%3A6AT2cZVCOQ1mo4vCsopmi1c7PI27K8_W.vBaUbaodCGgtiTjlQNUU%2FFh3mOZExj3fGiYhk4HY%2Bow").
                log().headers().
                when().
                get("/get").
                then().
                log().body().
                assertThat().statusCode(200).
                body(matchesJsonSchemaInClasspath("EchoGetSchema.json"));

        System.out.println("--------------------------------------------------");
    }

    public void requestLoggingFilter() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter()).
                queryParam("foo1", "JQ1").
                queryParam("foo2", "JQ2").
                queryParam("foo3", "JQ3").
                headers("x-forwarded-proto","https","x-forwarded-port","443","host","postman-echo.com",
                        "accept","*/*", "accept-encoding","gzip, deflate, br","cookie","sails.sid=s%3A6AT2cZVCOQ1mo4vCsopmi1c7PI27K8_W.vBaUbaodCGgtiTjl").
                when().
                get("/get").
                then().
                assertThat().statusCode(200);

        System.out.println("--------------------------------------------------");
    }

    public void responseLoggingFilter() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("https://postman-echo.com").
                filter(new ResponseLoggingFilter()).
                queryParam("foo1", "JQ1").
                queryParam("foo2", "JQ2").
                queryParam("foo3", "JQ3").
                headers("x-forwarded-proto","https","x-forwarded-port","443","host","postman-echo.com",
                        "accept","*/*", "accept-encoding","gzip, deflate, br","cookie","3A6AT2cZVCOQ1mo4vC").
                when().
                get("/get").
                then().
                log().cookies().
                assertThat().statusCode(200);

        System.out.println("--------------------------------------------------");
    }

    public void specificLoggingFilter() {
        System.out.println("------------------------------------------------------------------------");

        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter(LogDetail.COOKIES)).
                filter(new ResponseLoggingFilter(LogDetail.COOKIES)).
                queryParam("foo1", "JQ1").
                queryParam("foo2", "JQ2").
                queryParam("foo3", "JQ3").
                headers("x-forwarded-proto","https","x-forwarded-port","443","host","postman-echo.com",
                        "accept","*/*", "accept-encoding","gzip, deflate, br","cookie","3A6AT2cZVCOQ1mo4vC").
                when().
                get("/get").
                then().
                assertThat().statusCode(200);

        System.out.println("--------------------------------------------------");
    }

    public void loggingFilter() throws FileNotFoundException {
        System.out.println("------------------------------------------------------------------------");

        PrintStream FileOutputStream = new PrintStream("restAssured.log");
        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter(FileOutputStream)).
                filter(new ResponseLoggingFilter(FileOutputStream)).
                queryParam("foo1", "JQ1").
                queryParam("foo2", "JQ2").
                queryParam("foo3", "JQ3").
                headers("x-forwarded-proto","https","x-forwarded-port","443","host","postman-echo.com",
                        "accept","*/*", "accept-encoding","gzip, deflate, br","cookie","3A6AT2cZVCOQ1mo4vC").
                when().
                get("/get").
                then().
                assertThat().statusCode(200);

        System.out.println("--------------------------------------------------");
    }

    public void loggingFilter_specificInfo() throws FileNotFoundException {
        System.out.println("------------------------------------------------------------------------");

        given(requestSpecification).
                baseUri("https://postman-echo.com").
                queryParam("foo1", "JQ1").
                queryParam("foo2", "JQ2").
                queryParam("foo3", "JQ3").
                headers("x-forwarded-proto","https","x-forwarded-port","443","host","postman-echo.com",
                        "accept","*/*", "accept-encoding","gzip, deflate, br","cookie","3A6AT2cZVCOQ1mo4vC").
                when().
                get("/get").
                then().
                assertThat().statusCode(200);

        System.out.println("--------------------------------------------------");
    }
}
