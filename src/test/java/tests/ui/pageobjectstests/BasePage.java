package tests.ui.pageobjectstests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    public void waitPageLoadsWb() {
        By pageLoader = By.xpath("//div[@class='general-preloader']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
    }

    public void waitForElementUpdated(By locator) {
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
    }

    public WebElement waitForTextPresentedInList(By list, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(list));
       return driver.findElements(list).stream()
                .filter(x -> x.getText().contains(value))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не найден город " + value));
    }

    public void waitForTextMatchesRegex(By locator, String regex) {
        Pattern pattern = Pattern.compile(regex);
        wait.until(ExpectedConditions.textMatches(locator, pattern));
    }

    public void waitForElementDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementAppear(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Integer getDigitFromWebElement(WebElement webElement) {
        String text = webElement.getText().replaceAll("[^0-9.]", "");
        return Integer.parseInt(text);
    }

    public List<Integer> getDigitsFromList(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator).stream()
                .filter(x -> x.isDisplayed())
                .map(x -> getDigitFromWebElement(x))
                .collect(Collectors.toList());
    }
}
