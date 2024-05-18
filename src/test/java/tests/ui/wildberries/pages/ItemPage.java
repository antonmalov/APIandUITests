package tests.ui.wildberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ItemPage {
    private WebDriver driver;
    private final By itemHeaderName = By.xpath("//h1[@class='product-page__title']");
    private final By itemPrice = By.xpath("//span[@class='price-block__wallet-price']");

    public ItemPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getItemName() {
        return driver.findElement(itemHeaderName).getText();
    }

    public Integer getItemPrice() {
        String priceText = driver.findElement(itemPrice).getText();
        return Integer.parseInt(priceText.replaceAll("[^0-9.]", ""));
    }
}
