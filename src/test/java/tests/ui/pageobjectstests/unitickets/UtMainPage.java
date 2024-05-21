package tests.ui.pageobjectstests.unitickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tests.ui.pageobjectstests.BasePage;

public class UtMainPage extends BasePage {

    private final By cityFromField = By.xpath("//input[@placeholder='Откуда']");
    private final By listOfCityFrom = By.xpath("//div[@class='origin field active']//div[@class='city']");
    private final By cityToField = By.xpath("//input[@placeholder='Куда']");
    private final By listOfCityTo = By.xpath("//div[@class='destination field active']//div[@class='city']");
    private final By dateForward = By.xpath("//input[@placeholder='Туда']");
    private final By dateBack = By.xpath("//input[@placeholder='Обратно']");
    private final String dayInCalendar = "//span[text()='%d']";
    private final By searchBtn = By.xpath("//div[@class='search_btn']");
    public UtMainPage(WebDriver driver) {
        super(driver);
    }

    public UtMainPage setCityFrom(String city) {
        driver.findElement(cityFromField).click();
        driver.findElement(cityFromField).clear();
        driver.findElement(cityFromField).sendKeys(city);
        waitForTextPresentedInList(listOfCityFrom, city).click();
        return this;
    }

    public UtMainPage setCityTo(String city) {
        driver.findElement(cityToField).clear();
        driver.findElement(cityToField).sendKeys(city);
        waitForTextPresentedInList(listOfCityTo, city).click();
        return this;
    }

    private WebElement getDay(int day) {
        By dayLocator = By.xpath(String.format(dayInCalendar, day));
        return driver.findElement(dayLocator);
    }

}
