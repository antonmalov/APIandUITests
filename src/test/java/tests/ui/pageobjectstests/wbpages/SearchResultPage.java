package tests.ui.pageobjectstests.wbpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.ui.pageobjectstests.BasePage;

public class SearchResultPage extends BasePage {
    private final By allFiltersBtn = By.xpath("//button[@class='dropdown-filter__btn dropdown-filter__btn--all']");
    private final By startPrice = By.xpath("//input[@name='startN']");
    private final By endPrice = By.xpath("//input[@name='endN']");
    private final By applyFilterBtn = By.xpath("//button[@class='filters-desktop__btn-main btn-main']");
    private final By items = By.xpath("//div[@class='product-card-list']//article");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultPage clickFilters() {
        driver.findElement(allFiltersBtn).click();
        return this;
    }

    public SearchResultPage setMinPrice(Integer minPrice) {
        driver.findElement(startPrice).clear();
        driver.findElement(startPrice).sendKeys(String.valueOf(minPrice));
        return this;
    }

    public SearchResultPage setMaxPrice(Integer maxPrice) {
        driver.findElement(endPrice).clear();
        driver.findElement(endPrice).sendKeys(String.valueOf(maxPrice));
        return this;
    }

    public SearchResultPage clickApplyFilter() {
        driver.findElement(applyFilterBtn).click();
        waitForElementUpdated(items);
        return this;
    }

    public ItemPage openItem() {
        driver.findElements(items).get(0).click();
        waitPageLoadsWb();
        return new ItemPage(driver);
    }




}
