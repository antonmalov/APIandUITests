package tests.ui.pageobjectstests.unitickets;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtMainSelenidePage {
    private final SelenideElement cityFromField = $x("//input[@placeholder='Откуда']");
    private final ElementsCollection listOfCityFrom = $$x("//div[@class='origin field active']//div[@class='city']");
    private final SelenideElement cityToField = $x("//input[@placeholder='Куда']");
    private final ElementsCollection listOfCityTo = $$x("//div[@class='destination field active']//div[@class='city']");
    private final SelenideElement dateForward = $x("//input[@placeholder='Туда']");
    private final SelenideElement dateBack = $x("//input[@placeholder='Обратно']");
    private final String dayInCalendar = "//span[text()='%d']";
    private final SelenideElement searchBtn = $x("//div[@class='search_btn']");


    public UtMainSelenidePage setCityFrom(String city) {
        cityFromField.clear();
        cityFromField.sendKeys(city);
        cityFromField.click();
        listOfCityFrom.find(Condition.partialText(city)).click();
        return this;
    }

    public UtMainSelenidePage setCityTo(String city) {
        cityToField.clear();
        cityToField.sendKeys(city);
        listOfCityTo.find(Condition.partialText(city)).click();
        return this;
    }

    private SelenideElement getDay(int day) {
        return $x(String.format(dayInCalendar, day));

    }

    public UtMainSelenidePage setDayForward(int day) {
        dateForward.click();
        getDay(day).click();
        $x("//h2").click();
        return this;
    }

    public UtMainSelenidePage setDayBack(int day) {
        dateBack.click();
        getDay(day).click();
        $x("//h2").click();
        return this;
    }

    public UtSearchSelenidePage search() {
        searchBtn.click();
        return new UtSearchSelenidePage();
    }
}
