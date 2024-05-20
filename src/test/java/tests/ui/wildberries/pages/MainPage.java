package tests.ui.wildberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage{
    private final By searchField = By.id("searchInput");
    private final By cartBtn = By.xpath("//a[@data-wba-header-name='Cart']");
    private final By loginBtn = By.xpath("//a[@data-wba-header-name='Login']");

    public MainPage(WebDriver driver) {
        super(driver);
        waitPageLoads();
    }

    public SearchResultPage searchItem(String item) throws InterruptedException {
        driver.findElement(searchField).click();
        driver.findElement(searchField).sendKeys(item);
        driver.findElement(searchField).sendKeys(Keys.ENTER);
        return new SearchResultPage(driver);
    }
}
