package tests.ui.wildberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;
    private final By searchField = By.id("searchInput");
    private final By cartBtn = By.xpath("//a[@data-wba-header-name='Cart']");
    private final By loginBtn = By.xpath("//a[@data-wba-header-name='Login']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchItem(String item) {
        driver.findElement(searchField).click();
        driver.findElement(searchField).sendKeys(item);
        driver.findElement(searchField).sendKeys(Keys.ENTER);
    }
}
