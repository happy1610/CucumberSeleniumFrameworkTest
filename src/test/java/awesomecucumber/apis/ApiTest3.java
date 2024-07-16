package awesomecucumber.apis;

import com.rest.pojo.collection.*;
import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest3 {

    @DataProvider(name = "workspace")
    public Object[][] getWorkspace(){
        return new Object[][]{
                {"JQ1","desc1","personal"},
                {"JQ2","desc2","team"}
        };
    }

    public void getDeserializedProduct() {
        Workspace workspace = new Workspace("TestJQ From RestAssured v2","description","personal");
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        String endpoint = "https://api.getpostman.com";

        WorkspaceRoot deserializedWorkspaceRoot = given().
                baseUri(endpoint).
                queryParam("apikey","Your ApiKey").
                body(workspaceRoot).
                when().post("/workspaces").
                then().statusCode(200).
                extract().as(WorkspaceRoot.class);

        assertThat(deserializedWorkspaceRoot.getWorkspace().getName(), equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializedWorkspaceRoot.getWorkspace().getId(), notNullValue());

        System.out.println("------------------------------------------------------------------------");
    }

    //@Test(dataProvider = "workspace")
    public void getDeserializedProducts_DataProvider(String name, String description, String type) {
        Workspace workspace = new Workspace(name,description,type);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        String endpoint = "https://api.getpostman.com";

        WorkspaceRoot deserializedWorkspaceRoot = given().
                baseUri(endpoint).
                queryParam("apikey","Your ApiKey").
                body(workspaceRoot).
                log().body().
                when().post("/workspaces").
                then().statusCode(200).
                log().body().
                extract().as(WorkspaceRoot.class);

        assertThat(deserializedWorkspaceRoot.getWorkspace().getName(), equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializedWorkspaceRoot.getWorkspace().getId(), notNullValue());

        System.out.println("------------------------------------------------------------------------");
    }

    public void serializeComplexBody() {
        //region Serialize JSON Body
        ArrayList<Header> listHeaderRequest1 = new ArrayList<Header>();
        Header headerRequest1 = new Header("Header-Sample", "JQ", "text");
        listHeaderRequest1.add(headerRequest1);

        ArrayList<String> hostRequest1 = new ArrayList<String>();
        hostRequest1.add("postman-echo");
        hostRequest1.add("com");
        ArrayList<String> pathRequest1 = new ArrayList<String>();
        pathRequest1.add("get");
        ArrayList<String> pathRequest2 = new ArrayList<String>();
        pathRequest2.add("post");
        Url urlRequest1 = new Url("https://postman-echo.com/get", "https", hostRequest1, pathRequest1);
        Url urlRequest2 = new Url("https://postman-echo.com/post", "https", hostRequest1, pathRequest2);

        Request Request1 = new Request("GET", listHeaderRequest1, urlRequest1);
        Request Request2 = new Request("POST", listHeaderRequest1, urlRequest2);

        ArrayList<Object> response = new ArrayList<Object>();

        ArrayList<Item> listItemsRequest1 = new ArrayList<Item>();
        Item itemRequest1 = new Item("Sample Get Request 1", Request1, response);
        listItemsRequest1.add(itemRequest1);

        ArrayList<Item> listItemsRequest2 = new ArrayList<Item>();
        Item itemRequest2 = new Item("Sample Post Request 2", Request2, response);
        listItemsRequest2.add(itemRequest2);

        ArrayList<Folder> listFolderRequest1 = new ArrayList<Folder>();
        Folder folderRequest1 = new Folder("Sample Folder 1", listItemsRequest1);
        Folder folderRequest2 = new Folder("Sample Folder 2", listItemsRequest2);
        listFolderRequest1.add(folderRequest1);
        listFolderRequest1.add(folderRequest2);

        Info info = new Info("a3595dde-84bb-4fdc-ac0f-a96f6e755cc0", "Sample Collection",
                "https://schema.getpostman.com/json/collection/v2.1.0/collection.json", "9098265");

        Collection collectionRequest1 = new Collection(info, listFolderRequest1);

        CollectionRoot collectionRoot1 = new CollectionRoot(collectionRequest1);
        //endregion

        CollectionRoot deserializedCollectionRoot = given().
                baseUri("https://api.getpostman.com").
                queryParam("apikey", "Your ApiKey").
                body(collectionRoot1).
                log().body().
                when().post("/collections").
                then().statusCode(200).
                log().body().
                extract().as(CollectionRoot.class);

        System.out.println("Collection Id: " + deserializedCollectionRoot.getCollection().getId());
        System.out.println("Collection Name: " + deserializedCollectionRoot.getCollection().getName());
        System.out.println("Collection Uid: " + deserializedCollectionRoot.getCollection().getUid());
    }
}
