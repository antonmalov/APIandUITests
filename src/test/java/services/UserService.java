package services;

import assertions.AssertableResponse;
import io.restassured.http.ContentType;
import models.usercontroller.FullUser;
import models.usercontroller.JwtAuthData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserService {

    public AssertableResponse register(FullUser user) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then());
    }

    public AssertableResponse login(FullUser user) {
        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then());
    }

    public AssertableResponse getUserInfo(String token) {
        return new AssertableResponse(given().auth().oauth2(token)
                .get("/api/user")
                .then());
    }

    public AssertableResponse getUserInfo() {
        return new AssertableResponse(given()
                .get("/api/user")
                .then());
    }

    public AssertableResponse updatePassword(String token, String newPassword) {
        Map<String, String> password = new HashMap<>();
        password.put("password", newPassword);

        return new AssertableResponse(given().contentType(ContentType.JSON).auth().oauth2(token)
                .body(password)
                .put("/api/user")
                .then());
    }

    public AssertableResponse deleteUser(String token) {
        return new AssertableResponse(given().auth().oauth2(token)
                .delete("/api/user")
                .then());
    }

    public AssertableResponse getAllUsers() {
        return new AssertableResponse(given()
                .get("/api/users")
                .then());
    }
}
