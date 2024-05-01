package services;

import assertions.AssertableResponse;
import io.restassured.http.ContentType;
import models.usercontroller.FullUser;

import static io.restassured.RestAssured.given;

public class UserService {

    public AssertableResponse register(FullUser user) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then());
    }
}
