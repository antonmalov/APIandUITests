package tests.API.userscontroller;
import assertions.AssertableResponse;
import assertions.Condition;
import assertions.Conditions;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import listener.CustomTpl;
import models.usercontroller.FullUser;
import models.usercontroller.Info;
import models.usercontroller.JwtAuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;
import static io.restassured.RestAssured.given;

public class UserControllerTests {

    public static UserService userService;
    public static Random random;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://85.192.34.140:8080";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(),
                CustomTpl.customLogFilter().withCustomTemplates());
        userService = new UserService();
        random = new Random();
    }

    private FullUser getRandomUser() {
        int randomNumber = Math.abs(random.nextInt());
        return FullUser.builder()
                .login("loginAnt" + randomNumber)
                .pass("newPass")
                .build();
    }

    @Test
    public void positiveRegisterTest() {
        FullUser user = getRandomUser();
        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));
    }

    @Test
    public void negativeRegisterLoginExistTest() {
        int randomNumber = Math.abs(random.nextInt());
        FullUser user = FullUser.builder()
                .login("loginAnt" + randomNumber)
                .pass("newPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());

        info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Login already exist", info.getMessage());
    }

    @Test
    public void negativeRegisterWithoutPasswordTest() {
        int randomNumber = Math.abs(random.nextInt());
        FullUser user = FullUser.builder()
                .login("loginAnt" + randomNumber)
                .build();

        new AssertableResponse(given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then())
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }

    @Test
    public void negativeRegisterWithoutLoginTest() {
        FullUser user = FullUser.builder()
                .pass("newPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Missing login or password", info.getMessage());
    }

    @Test
    public void positiveAuthTest() {
        JwtAuthData authData = new JwtAuthData("admin", "admin");

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest() {
        int randomNumber = Math.abs(random.nextInt());
        FullUser user = FullUser.builder()
                .login("loginAnt" + randomNumber)
                .pass("newPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Assertions.assertNotNull(token);
    }

    @Test
    public void negativeAuthTest() {
        JwtAuthData authData = new JwtAuthData("sdfsdfsdf", "sdfsdfsd");

        given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(401);
    }

    @Test
    public void positiveGetUserInfoTest() {
        JwtAuthData authData = new JwtAuthData("admin", "admin");

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        given().auth().oauth2(token)
                .get("/api/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void negativeGetUserInfoInvalidJwtTest() {
        given().auth().oauth2("some values")
                .get("/api/user")
                .then()
                .statusCode(401);
    }

    @Test
    public void negativeGetUserInfoWithoutJwtTest() {
        given()
                .get("/api/user")
                .then()
                .statusCode(401);
    }
    @Test
    public void positiveChangeUserPassTest() {
        int randomNumber = Math.abs(random.nextInt());
        FullUser user = FullUser.builder()
                .login("loginAnt" + randomNumber)
                .pass("newPass")
                .build();

        given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Map<String, String> newPass = new HashMap<>();
        String updatePass = "updatePass";
        newPass.put("password", updatePass);

        Info info = given().contentType(ContentType.JSON).auth().oauth2(token)
                .body(newPass)
                .put("/api/user")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User password successfully changed", info.getMessage());


        authData.setPassword(updatePass);

        token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        FullUser updatedUser = given().auth().oauth2(token)
                .get("/api/user")
                .then()
                .statusCode(200)
                .extract().as(FullUser.class);

        Assertions.assertNotEquals(user.getPass(), updatedUser.getPass());
    }

    @Test
    public void negativeChangeAdminPassTest() {
        JwtAuthData authData = new JwtAuthData("admin", "admin");

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Map<String, String> newPass = new HashMap<>();
        String updatePass = "updatePass";
        newPass.put("password", updatePass);

        Info info = given().contentType(ContentType.JSON).auth().oauth2(token)
                .body(newPass)
                .put("/api/user")
                .then()
                .statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Cant update base users", info.getMessage());
    }

    @Test
    public void negativeDeleteAdminTest() {
        JwtAuthData authData = new JwtAuthData("admin", "admin");

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Info info = given().auth().oauth2(token)
                .delete("/api/user")
                .then()
                .statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Cant delete base users", info.getMessage());
    }

    @Test
    public void positiveDeleteUserTest() {
        int randomNumber = Math.abs(random.nextInt());
        FullUser user = FullUser.builder()
                .login("loginAnt" + randomNumber)
                .pass("newPass")
                .build();

        given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(201);

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Info info = given().auth().oauth2(token)
                .delete("/api/user")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User successfully deleted", info.getMessage());
    }

    @Test
    public void positiveGetAllUsersTest() {
        List<String> users = given()
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<String>>() {
                });

        Assertions.assertTrue(users.size() > 3);
    }
}
