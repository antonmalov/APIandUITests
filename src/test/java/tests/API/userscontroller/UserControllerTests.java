package tests.API.userscontroller;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import listener.CustomTpl;
import models.usercontroller.FullUser;
import models.usercontroller.Info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserControllerTests {
    public static Random random;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://85.192.34.140:8080";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(),
                CustomTpl.customLogFilter().withCustomTemplates());
        random = new Random();
    }

    @Test
    public void positiveRegisterTest() {
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

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Missing login or password", info.getMessage());
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
}
