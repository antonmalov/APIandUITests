package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumTests {
    private WebDriver driver;
    private String downloadFolder = System.getProperty("user.dir") + File.separator + "build" + File.separator + "downloadFiles";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, String> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFolder);
        options.setExperimentalOption("prefs", prefs);

        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void simpleUiTest() {
        driver.get("https://www.youtube.com/channel/UCL8ZVHWNBkXbdxfiFXH7krw");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Oleh Pendrak - YouTube";
        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void simpleFormTest() {
        String expectedName = "Tomas Anderson";
        String expectedEmail = "tomas@matrix.ru";
        String expectedCurrentAddress = "USA LA";
        String expectedPermanentAddress = "USA Miami";

        driver.get("http://85.192.34.140:8081/");
        WebElement elementCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementCard.click();

        WebElement elementTextBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        elementTextBox.click();

        WebElement userName = driver.findElement(By.id("userName"));
        WebElement userEmail = driver.findElement(By.id("userEmail"));
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        WebElement btnSubmit = driver.findElement(By.id("submit"));

        userName.sendKeys(expectedName);
        userEmail.sendKeys(expectedEmail);
        currentAddress.sendKeys(expectedCurrentAddress);
        permanentAddress.sendKeys(expectedPermanentAddress);
        btnSubmit.click();

        WebElement nameNew = driver.findElement(By.id("name"));
        WebElement emailNew = driver.findElement(By.id("email"));
        WebElement currentAddressNew = driver.findElement(By.xpath("//div[@id='output']//p[@id='currentAddress']"));
        WebElement permanentAddressNew = driver.findElement(By.xpath("//div[@id='output']//p[@id='permanentAddress']"));

        String actualName = nameNew.getText();
        String actualEmail = emailNew.getText();
        String actualCurrentAddress = currentAddressNew.getText();
        String actualPermanentAddress = permanentAddressNew.getText();

        Assertions.assertTrue(actualName.contains(expectedName));
        Assertions.assertTrue(actualEmail.contains(expectedEmail));
        Assertions.assertTrue(actualCurrentAddress.contains(expectedCurrentAddress));
        Assertions.assertTrue(actualPermanentAddress.contains(expectedPermanentAddress));
    }

    @Test
    public void uploadFileTest() {
        driver.get("http://85.192.34.140:8081/");
        WebElement elementCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementCard.click();

        WebElement elementUpAndDown = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementUpAndDown.click();

        WebElement btnUpload = driver.findElement(By.id("uploadFile"));
        btnUpload.sendKeys(System.getProperty("user.dir") + "/src/test/resources/threadqa.jpeg");

        WebElement uploadedFakePath = driver.findElement(By.id("uploadedFilePath"));

        Assertions.assertTrue(uploadedFakePath.getText().contains("threadqa.jpeg"));
    }

    @Test
    public void downloadFileTest() {
        driver.get("http://85.192.34.140:8081/");
        WebElement elementCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementCard.click();

        WebElement elementUpAndDown = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementUpAndDown.click();

        WebElement btnDownload = driver.findElement(By.id("downloadButton"));
        btnDownload.click();
    }
}
