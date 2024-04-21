package tests.juinit5.API.fakestoreapi;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UsersTests {
    @Test
    public void getAllUsersTests() {
        given().get("https://fakestoreapi.com/users")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void getSingleUserTest() {
        int userId = 2;
        given().pathParam("userId", userId)
                .get("https://fakestoreapi.com/users/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("address.zipcode", matchesPattern("\\d{5}-\\d{4}"));
    }

    @Test
    public void getAllUsersWithLimitTest() {
        int limitSize = 3;
        given().queryParam("limit", limitSize)
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .statusCode(200)
                .body("", hasSize(limitSize));
    }
}
