package tests.ui.pageobjectstests.unitickets;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.ui.pageobjectstests.BasePage;
import tests.ui.pageobjectstests.BaseTest;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtSearchSelenidePage {
    private final SelenideElement titleLoader =$x("//div[@class='countdown-title']");
    private final SelenideElement priceSelectedMain = $x("//li[@class='price--current']");
    private final SelenideElement selectedDayForward = $x("//li[@class='price--current']//span[@class='prices__date'][1]");
    private final SelenideElement selectedDayBack = $x("//li[@class='price--current']//span[@class='prices__date'][2]");
    private final ElementsCollection listOfForwardDays = $$x("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][1]");
    private final ElementsCollection listOfBackDays = $$x("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][3]");

    public UtSearchSelenidePage assertAllDaysForwardShouldHaveDay(int expectedForwardDay) {
        String day = String.valueOf(expectedForwardDay);
        listOfForwardDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchSelenidePage assertAllDaysBackShouldHaveDay(int expectedBackDay) {
        String day = String.valueOf(expectedBackDay);
        listOfBackDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchSelenidePage assertMainDayForward(int expectedForwardDay) {
        selectedDayForward.should(Condition.partialText(String.valueOf(expectedForwardDay)));
        return this;
    }

    public UtSearchSelenidePage assertMainDayBack(int expectedBackDay) {
        selectedDayBack.should(Condition.partialText(String.valueOf(expectedBackDay)));
        return this;
    }

    public UtSearchSelenidePage waitForPage() {
        priceSelectedMain.should(Condition.matchText("\\d+"));
        return this;
    }

    public UtSearchSelenidePage waitForTitleDisappear() {
        titleLoader.should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }
}
