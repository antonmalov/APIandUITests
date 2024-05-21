package tests.ui.pageobjectstests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.ui.pageobjectstests.unitickets.UtMainPage;

public class UniticketTests extends BaseTest {

    @BeforeEach
    public void openSite() {
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void test() {
        UtMainPage ut = new UtMainPage(driver)
                .setCityFrom("Москва");
    }
}
