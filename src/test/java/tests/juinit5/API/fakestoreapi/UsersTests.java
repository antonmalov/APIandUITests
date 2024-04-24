package tests.juinit5.API.fakestoreapi;

import io.restassured.response.Response;
import models.fakeapiuser.Address;
import models.fakeapiuser.Geolocation;
import models.fakeapiuser.Name;
import models.fakeapiuser.UserRoot;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        Integer userId = (Integer) 2;
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
        given().queryParam("limit", Collections.singleton(limitSize))
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .statusCode(200)
                .body("", hasSize(limitSize));
    }

    @Test
    public void getAllUsersSortByDescTest() {
        String sortType = "desc";
        Response sortedResponse = given().queryParam("sort", sortType)
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Response notSortedResponse = given()
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .extract().response();

        List<Integer> sortedListIds = sortedResponse.jsonPath().getList("id");
        List<Integer> notSortedListIds = notSortedResponse.jsonPath().getList("id");

        List<Integer> sortedByCode = notSortedListIds.stream().sorted(Comparator.reverseOrder()).toList();

        assertNotEquals(sortedListIds, notSortedListIds);
        assertEquals(sortedByCode, sortedListIds);
    }

    @Test
    public void addNewUserTest() {
        Name name = new Name("Anton", "Malov");
        Geolocation geolocation = new Geolocation("-45.4532", "34.2345");
        Address address = Address.builder()
                .geolocation(geolocation)
                .city("Moscow")
                .street("street")
                .number(4556)
                .zipcode("12345-2365")
                .build();

        UserRoot bodyRequest = UserRoot.builder()
                .address(address)
                .email("some@email")
                .username("ant")
                .password("somePass")
                .name(name)
                .phone("79898989898")
                .build();

        given().body(bodyRequest)
                .post("https://fakestoreapi.com/users")
                .then().log().all()
                .statusCode(200)
                .body("id", notNullValue());




    }
}
