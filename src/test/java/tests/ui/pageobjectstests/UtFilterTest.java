package tests.ui.pageobjectstests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.ui.pageobjectstests.unitickets.UtMainPage;
import tests.ui.pageobjectstests.unitickets.UtSearchPage;

public class UtFilterTest extends BaseTest {

    @BeforeEach
    public void openSite() {
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void test() {
        int expectedDayForward = 23;
        int expectedDayBack = 25;
        UtMainPage mainPage = new UtMainPage(driver);
        mainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDayForward(expectedDayForward)
                .setDayBack(expectedDayBack);
    }
}
