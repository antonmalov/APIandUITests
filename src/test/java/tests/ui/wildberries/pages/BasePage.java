package tests.ui.wildberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    private By pageLoader = By.xpath("//div[@class='general-preloader']");

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    public void waitPageLoads() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
    }

    public void waitForElementUpdated(By locator) {
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
    }


}
