package tests.APIAutomation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReqresApiTest {

    private final String API_KEY = "reqres-free-v1"; // In final value as it's fixed for all tests

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test(description = "Create a new user via POST and assert status 201")
    public void createUserTest() {
        String requestBody = """
                {
                    "name":"morpheus",
                    "job":"leader"
                }
                """;

        Response response = given()
                .header("x-api-key", API_KEY)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 201, "Status code should be 201");
        System.out.println("POST Response: " + response.asPrettyString());
    }

    @Test(description = "GET users with delay=3 and validate first user fields")
    public void getUsersWithDelayTest() {
        Response response = given()
                .header("x-api-key", API_KEY)
                .queryParam("delay", 3)
                .when()
                .get("/api/users")
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");

        // Extract first user in data array
        Map<String, Object> firstUser = response.jsonPath().getMap("data[0]");

        // Assert fields not null
        Assert.assertNotNull(firstUser.get("id"), "id should not be null");
        Assert.assertNotNull(firstUser.get("email"), "email should not be null");
        Assert.assertNotNull(firstUser.get("first_name"), "first_name should not be null");
        Assert.assertNotNull(firstUser.get("avatar"), "avatar should not be null");

        System.out.println("GET Response First User: " + firstUser);
    }
}
