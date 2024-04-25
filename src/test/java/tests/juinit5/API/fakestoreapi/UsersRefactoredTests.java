package tests.juinit5.API.fakestoreapi;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import listener.CustomTpl;
import models.fakeapiuser.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Comparator;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class UsersRefactoredTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(),
                CustomTpl.customLogFilter().withCustomTemplates());
    }

    @Test
    public void getAllUsersTests() {
        given().get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSingleUserTest() {
        Integer userId = 2;
        UserRoot response = given().pathParam("userId", userId)
                .get("/users/{userId}")
                .then()
                .statusCode(200)
                .extract().as(UserRoot.class);

        Name name = given().pathParam("userId", userId)
                .get("/users/{userId}")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("name", Name.class);

        Assertions.assertEquals(userId, response.getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 10})
    public void getAllUsersWithLimitTest(int limitSize) {
        List<UserRoot> users = given().queryParam("limit", limitSize)
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().getList("", UserRoot.class);

        Assertions.assertEquals(limitSize, users.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 20, 40})
    public void getAllUsersWithLimitErrorParams(int limitSize) {
        List<UserRoot> users = given().queryParam("limit", limitSize)
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().getList("", UserRoot.class);

        Assertions.assertNotEquals(limitSize, users.size());
    }

    @Test
    public void getAllUsersSortByDescTest() {
        String sortType = "desc";
        List<UserRoot> usersSorted = given().queryParam("sort", sortType)
                .get("/users")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<UserRoot>>(){});

        List<UserRoot> usersNotSorted = given()
                .get("/users")
                .then()
                .extract().as(new TypeRef<List<UserRoot>>(){});

        List<Integer> sortedListIds = usersSorted
                .stream()
                .map(UserRoot::getId).toList();

        List<Integer> notSortedListIds = usersNotSorted
                .stream()
                .map(UserRoot::getId).toList();

        List<Integer> sortedByCode = notSortedListIds.stream().sorted(Comparator.reverseOrder()).toList();

        assertNotEquals(usersSorted, usersNotSorted);
        assertEquals(sortedListIds, sortedByCode);
    }

    private UserRoot getTestUser() {
        Name name = new Name("Anton", "Malov");
        Geolocation geolocation = new Geolocation("-45.4532", "34.2345");
        Address address = Address.builder()
                .geolocation(geolocation)
                .city("Moscow")
                .street("street")
                .number(4556)
                .zipcode("12345-2365")
                .build();

        return UserRoot.builder()
                .address(address)
                .email("some@email")
                .username("ant")
                .password("somePass")
                .name(name)
                .phone("79898989898")
                .build();
    }

    @Test
    public void addNewUserTest() {
        UserRoot user = getTestUser();

        Integer userIds = given().body(user)
                .post("/users")
                .then()
                .statusCode(200)
                .extract().jsonPath().getInt("id");

        Assertions.assertNotNull(userIds);
    }

    @Test
    public void updateUserTest() {
        UserRoot user = getTestUser();
        String oldPassword = user.getPassword();
        user.setPassword("updatePassword");

        UserRoot updatedUser = given()
                .body(user)
                .pathParam("userId", user.getId())
                .put("/users/{userId}" )
                .then()
                .statusCode(200)
                .extract().as(UserRoot.class);

        Assertions.assertNotEquals(updatedUser.getPassword(), oldPassword);
    }

    @Test
    public void authUserTest() {
        AuthData authData = new AuthData("jimmie_k", "klein*#%*");

        String token = given()
                .contentType(ContentType.JSON)
                .body(authData)
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");

        Assertions.assertNotNull(token);
    }
}
