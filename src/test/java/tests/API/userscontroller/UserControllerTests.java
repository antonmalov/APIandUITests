package tests.API.userscontroller;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import listener.CustomTpl;
import models.usercontroller.FullUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserService;
import java.util.List;
import java.util.Random;
import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;


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

    private FullUser getAdmin() {
        return FullUser.builder()
                .login("admin")
                .pass("admin")
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
        FullUser user = getRandomUser();
        userService.register(user);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Login already exist"));
    }

    @Test
    public void negativeRegisterWithoutPasswordTest() {
        FullUser user = getRandomUser();
        user.setPass(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }

    @Test
    public void negativeRegisterWithoutLoginTest() {
        FullUser user = getRandomUser();
        user.setLogin(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }

    @Test
    public void positiveAuthTest() {
        FullUser user = getAdmin();
        String token = userService.login(user)
                .should(hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest() {
        FullUser user = getRandomUser();
        userService.register(user);
        String token = userService.login(user)
                .should(hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void negativeAuthTest() {
        FullUser user = getRandomUser();
        userService.login(user).should(hasStatusCode(401));
    }

    @Test
    public void positiveGetUserInfoTest() {
        FullUser user = getAdmin();
        String token = userService.login(user).asJwt();
        userService.getUserInfo(token)
                .should(hasStatusCode(200));
    }

    @Test
    public void negativeGetUserInfoInvalidJwtTest() {
        userService.getUserInfo("fake jwt")
                .should(hasStatusCode(401));
    }

    @Test
    public void negativeGetUserInfoWithoutJwtTest() {
        userService.getUserInfo()
                .should(hasStatusCode(401));
    }
    @Test
    public void positiveChangeUserPassTest() {
        FullUser user = getRandomUser();
        String oldPass = user.getPass();

        userService.register(user);

        String token = userService.login(user).asJwt();

        String updatePass = "updatePass";

        userService.updatePassword(token, updatePass)
                .should(hasStatusCode(200))
                .should(hasMessage("User password successfully changed"));

        user.setPass(updatePass);

        token = userService.login(user).should(hasStatusCode(200)).asJwt();

        FullUser updatedUser = userService.getUserInfo(token).as(FullUser.class);

        Assertions.assertNotEquals(oldPass, updatedUser.getPass());
    }

    @Test
    public void negativeChangeAdminPassTest() {
        FullUser user = getAdmin();

        String token = userService.login(user).asJwt();

        String updatePass = "updatePass";

        userService.updatePassword(token, updatePass)
                .should(hasStatusCode(400))
                .should(hasMessage("Cant update base users"));
    }

    @Test
    public void negativeDeleteAdminTest() {
        FullUser user = getAdmin();

        String token = userService.login(user).asJwt();

        userService.deleteUser(token)
                .should(hasStatusCode(400))
                .should(hasMessage("Cant delete base users"));
    }

    @Test
    public void positiveDeleteUserTest() {
        FullUser user = getRandomUser();
        userService.register(user);
        String token = userService.login(user).asJwt();

        userService.deleteUser(token)
                .should(hasStatusCode(200))
                .should(hasMessage("User successfully deleted"));
    }

    @Test
    public void positiveGetAllUsersTest() {
        List<String> users = userService.getAllUsers()
                .should(hasStatusCode(200))
                .asList(String.class);
        Assertions.assertTrue(users.size() > 3);
    }
}
