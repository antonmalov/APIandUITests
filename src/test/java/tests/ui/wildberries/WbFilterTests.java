package tests.ui.wildberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.ui.wildberries.pages.ItemPage;
import tests.ui.wildberries.pages.MainPage;

public class WbFilterTests extends BaseTest {

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
