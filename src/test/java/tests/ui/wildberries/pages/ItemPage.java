package tests.ui.wildberries.pages;

import org.openqa.selenium.*;

public class ItemPage extends BasePage {
    private final By itemHeaderName = By.xpath("//h1[@class='product-page__title']");
    private final By itemPrice = By.xpath("//ins[@class='price-block__final-price wallet']']");

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName() {
        return driver.findElement(itemHeaderName).getText();
    }

    public Integer getItemPrice() {
        WebElement itemPriceElement = driver.findElement(itemPrice);
        String priceText = (String) js.executeScript("return arguments[0].textContent;", itemPriceElement);

        return Integer.parseInt(priceText.replaceAll("[^0-9.]", ""));
    }
}
