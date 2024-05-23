package tests.ui.pageobjectstests.unitickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.ui.pageobjectstests.BasePage;
import tests.ui.pageobjectstests.BaseTest;

import java.util.List;

public class UtSearchPage extends BasePage {
    private final By titleLoader = By.xpath("//div[@class='countdown-title']");
    private final By priceSelectedMain = By.xpath("//li[@class='price--current']");
    private final By selectedDayForward = By.xpath("//li[@class='price--current']//span[@class='prices__date'][1]");
    private final By selectedDayBack = By.xpath("//li[@class='price--current']//span[@class='prices__date'][2]");
    private final By listOfForwardDays = By.xpath("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][1]");
    private final By listOfBackDays = By.xpath("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][3]");

    public UtSearchPage(WebDriver driver) {
        super(driver);
    }

    public List<Integer> getDaysForward() {
        return getDigitsFromList(listOfForwardDays);
    }

    public List<Integer> getDaysBack() {
        return getDigitsFromList(listOfBackDays);
    }

    public Integer getMainDayForward() {
        return getDigitFromWebElement(driver.findElement(selectedDayForward));
    }

    public Integer getMainDayBack() {
        return getDigitFromWebElement(driver.findElement(selectedDayBack));
    }

    public void waitForPage() {
        waitForElementAppear(selectedDayForward);
        waitForTextMatchesRegex(priceSelectedMain, "\\d+");
    }

    public void waitForTitleDisappear() {
        waitForElementDisappear(titleLoader);
    }
}
