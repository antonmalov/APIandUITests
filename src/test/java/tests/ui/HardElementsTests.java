package tests.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class HardElementsTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

//    @AfterEach
//    public void tearDown() {
//        driver.close();
//    }

    @Test
    public void authTest() {
        String expectedText = "Basic Auth";
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        String actualText = driver.findElement(By.xpath("//h3")).getText();

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    public void jsAlertTest() {
        String expectedTextToAlert = "I am a JS Alert";
        String expectedResultText = "You successfully clicked an alert";
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        String actualTextToAlert = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        String actualResultText = driver.findElement(By.id("result")).getText();

        Assertions.assertEquals(expectedTextToAlert, actualTextToAlert);
        Assertions.assertEquals(expectedResultText, actualResultText);
    }

    @Test
    public void jsConfirmOkTest() {
        String expectedTextToAlert = "I am a JS Confirm";
        String expectedResultText = "You clicked: Ok";
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        String actualTextToAlert = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        String actualResultText = driver.findElement(By.id("result")).getText();

        Assertions.assertEquals(expectedTextToAlert, actualTextToAlert);
        Assertions.assertEquals(expectedResultText, actualResultText);
    }

    @Test
    public void jsConfirmCancelTest() {
        String expectedTextToAlert = "I am a JS Confirm";
        String expectedResultText = "You clicked: Cancel";
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        String actualTextToAlert = driver.switchTo().alert().getText();
        driver.switchTo().alert().dismiss();
        String actualResultText = driver.findElement(By.id("result")).getText();

        Assertions.assertEquals(expectedTextToAlert, actualTextToAlert);
        Assertions.assertEquals(expectedResultText, actualResultText);
    }

    @Test
    public void jsPromptTest() {
        String expectedTextToAlert = "I am a JS prompt";
        String expectedResultText = "You entered: Input text";
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        String actualTextToAlert = driver.switchTo().alert().getText();
        driver.switchTo().alert().sendKeys("Input text");
        driver.switchTo().alert().accept();
        String actualResultText = driver.findElement(By.id("result")).getText();

        Assertions.assertEquals(expectedTextToAlert, actualTextToAlert);
        Assertions.assertEquals(expectedResultText, actualResultText);
    }

    @Test
    public void iFrameTest() {
        driver.get("https://mail.ru/");
        driver.findElement(By.xpath("//button[@class='resplash-btn resplash-btn_primary resplash-btn_mailbox-big mkiojld__1jdtl7f']")).click();

        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(iframe);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("test");
    }

    @Test
    public void sliderDragAndDropTest() {
        driver.get("http://85.192.34.140:8081/");
        driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Widgets']")).click();

        driver.findElement(By.xpath("//span[text()='Slider']")).click();

        WebElement elementSlider = driver.findElement(By.xpath("//input[@type='range']"));
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(elementSlider, 60, 0).build().perform();
    }

    @Test
    public void sliderTest() {
        driver.get("http://85.192.34.140:8081/");
        driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Widgets']")).click();

        driver.findElement(By.xpath("//span[text()='Slider']")).click();

        WebElement elementSlider = driver.findElement(By.xpath("//input[@type='range']"));
        int expectedValue = 85;
        int currentValue = Integer.parseInt(elementSlider.getAttribute("value"));
        int valueToMove = expectedValue - currentValue;

        for (int i = 0; i < valueToMove; i++) {
            elementSlider.sendKeys(Keys.ARROW_RIGHT);
        }

        int actualValue = Integer.parseInt(driver.findElement(By.id("sliderValue")).getAttribute("value"));
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
