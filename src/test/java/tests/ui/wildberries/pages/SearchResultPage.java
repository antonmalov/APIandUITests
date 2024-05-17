package tests.ui.wildberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {

    private WebDriver driver;
    private final By allFiltersBtn = By.xpath("//button[@class='dropdown-filter__btn dropdown-filter__btn--all']");
    private final By startPrice = By.xpath("//input[@name='startN']");
    private final By endPrice = By.xpath("//input[@name='endN']");
    private final By applyFilterBtn = By.xpath("//button[@class='filters-desktop__btn-main btn-main']");
    private final By items = By.xpath("//div[@class='product-card-list']//article");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickFilters() {
        driver.findElement(allFiltersBtn).click();
    }

    public void setMinPrice(Integer minPrice) {
        driver.findElement(startPrice).clear();
        driver.findElement(startPrice).sendKeys(String.valueOf(minPrice));
    }

    public void setMaxPrice(Integer maxPrice) {
        driver.findElement(endPrice).clear();
        driver.findElement(endPrice).sendKeys(String.valueOf(maxPrice));
    }

    public void clickApplyFilter() {
        driver.findElement(applyFilterBtn).click();
    }

    public void openItem() {
        driver.findElements(items).get(0).click();
    }




}
