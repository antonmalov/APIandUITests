package tests.ui.pageobjectstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.ui.pageobjectstests.BaseTest;
import tests.ui.pageobjectstests.wbpages.ItemPage;
import tests.ui.pageobjectstests.wbpages.MainPage;

public class WbFilterTests extends BaseTest {

    @BeforeEach
    public void openSait() {
        driver.get("https://www.wildberries.ru/");
    }

    @Test
    public void searchResultTest() throws InterruptedException {
        String expectedItem = "iphone";
        Integer expectedPriceMin = 36000;
        Integer expectedPriceMax = 60000;

        ItemPage itemPage = new MainPage(driver)
                .searchItem(expectedItem)
                .clickFilters()
                .setMinPrice(expectedPriceMin)
                .setMaxPrice(expectedPriceMax)
                .clickApplyFilter()
                .openItem();

        String actualName = itemPage.getItemName();
        Assertions.assertTrue(actualName.toLowerCase().contains(expectedItem.toLowerCase()));
    }
}
