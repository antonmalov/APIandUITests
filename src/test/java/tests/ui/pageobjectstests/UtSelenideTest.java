package tests.ui.pageobjectstests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import tests.ui.pageobjectstests.unitickets.UtMainSelenidePage;

public class UtSelenideTest {

    @Test
    public void selenideTest() {
        int expectedDayForward = 26;
        int expectedDayBack = 28;
        Selenide.open("https://uniticket.ru/");
        UtMainSelenidePage mainPage = new UtMainSelenidePage();
        mainPage.setCityFrom("Москва")
                .setCityTo("Дубай")
                .setDayForward(expectedDayForward)
                .setDayBack(expectedDayBack)
                .search()
                .waitForPage()
                .waitForTitleDisappear()
                .assertMainDayForward(expectedDayForward)
                .assertMainDayBack(expectedDayBack)
                .assertAllDaysForwardShouldHaveDay(expectedDayForward)
                .assertAllDaysBackShouldHaveDay(expectedDayBack);
    }
}
