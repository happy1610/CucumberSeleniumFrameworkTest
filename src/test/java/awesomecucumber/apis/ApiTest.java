package awesomecucumber.apis;

import awesomecucumber.models.Product;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    public void getCategories() {
        String endpoint = "http://localhost:80/api_testing/category/read.php";
        var response = given().
                when().get(endpoint).
                then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void getProducts() {
        String endpoint = "http://localhost:80/api_testing/product/read.php";
        var response = given().
                when().get(endpoint)
                .then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void getProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response = given().queryParam("id", 1).
                when().get(endpoint)
                .then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void createProduct() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String body = """
                {
                "id": 50,
                "name": "Camaro",
                "description": "Best car",
                "price": 1500,
                "category_id": 3
                }
                """;
        var response = given().body(body).
                when().post(endpoint)
                .then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void updateProduct() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String body = """
                {
                "id": 1001,
                "name": "Camaro Updated",
                "description": "Best car Updated",
                "price": 3000,
                "category_id": 3
                }
                """;
        var response = given().body(body).
                when().put(endpoint)
                .then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void deleteProduct() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = """
                {
                "id": 1001
                }
                """;
        var response = given().body(body).
                when().delete(endpoint)
                .then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void createSerializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "Chevrolet Camarooooo", "Best Car", 2000, 5);

        var response = given().body(product).
                when().post(endpoint).
                then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void updateSerializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        Product product = new Product(
                1005,"Chevrolet Camaro Jq!!!", "Best Car Updated", 2000, 3,"");

        var response = given().body(product).
                when().put(endpoint).
                then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void deleteSerializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        Product product = new Product(
                1006);

        var response = given().body(product).
                when().delete(endpoint).
                then();
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void getProductAssert() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response = given().queryParam("id", 1005).
                when().get(endpoint)
                .then().assertThat().statusCode(200).
                body("id",equalTo("1005")).
                body("name",equalTo("Chevrolet Camaro Updated")).
                body("description",equalTo("Best Car Updated")).
                body("price",equalTo("2000.00")).
                body("category_id",equalTo("3"));
        response.log().body();
        System.out.println("------------------------------------------------------------------------");
    }

    public void getProductSpecificAssert() {
        String endpoint = "http://localhost:80/api_testing/product/read.php";
        var response = given().
                when().get(endpoint)
                .then().log().body().assertThat().statusCode(200).
                body("records.size()",greaterThan(0)).
                body("records.id",everyItem(notNullValue())).
                body("records.name",everyItem(notNullValue())).
                body("records.description",everyItem(notNullValue())).
                body("records.price",everyItem(notNullValue())).
                body("records.category_id",everyItem(notNullValue()));
        System.out.println("------------------------------------------------------------------------");
    }

    public void getProductSpecificAssertIndex() {
        String endpoint = "http://localhost:80/api_testing/product/read.php";
        var response = given().
                when().get(endpoint)
                .then().log().body().assertThat().statusCode(200).
                body("records.size()",greaterThan(0)).
                body("records.id",everyItem(notNullValue())).
                body("records.name",everyItem(notNullValue())).
                body("records.description",everyItem(notNullValue())).
                body("records.price",everyItem(notNullValue())).
                body("records.category_id",everyItem(notNullValue())).
                body("records.id[0]",equalTo("1005"));
        System.out.println("------------------------------------------------------------------------");
    }

    public void getProductSpecificAssertHeaders() {
        String endpoint = "http://localhost:80/api_testing/product/read.php";
        var response = given().
                when().get(endpoint)
                .then().log().headers().assertThat().statusCode(200)
                        .header("Content-Type",equalTo("application/json; charset=UTF-8"));
        System.out.println("------------------------------------------------------------------------");
    }

    public void getDeserializedProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product expectedProduct = new Product(
                1005,
                "Chevrolet Camaro Jq!!!",
                "Best Car Updated",
                2000.00,
                3,
                "Active Wear - Unisex"
        );

        Product actualProduct = given().queryParam("id","1005").
                when().get(endpoint).
                as(Product.class);

        assertThat(actualProduct,samePropertyValuesAs(expectedProduct));
        System.out.println("------------------------------------------------------------------------");
    }
}
