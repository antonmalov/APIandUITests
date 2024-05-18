package tests.ui.wildberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import tests.ui.wildberries.pages.ItemPage;
import tests.ui.wildberries.pages.MainPage;
import tests.ui.wildberries.pages.SearchResultPage;

public class WbFilterTests extends BaseTest {

    @Test
    public void searchResultTest() {
        String expectedItem = "iphone";
        Integer expectedPriceMin = 36000;
        Integer expectedPriceMax = 60000;

        MainPage mainPage = new MainPage(driver);
        mainPage.searchItem(expectedItem);

        SearchResultPage resultPage = new SearchResultPage(driver);
        resultPage.clickFilters();
        resultPage.setMinPrice(expectedPriceMin);
        resultPage.setMaxPrice(expectedPriceMax);
        resultPage.clickApplyFilter();
        resultPage.openItem();

        ItemPage itemPage = new ItemPage(driver);
        String actualName = itemPage.getItemName();
        //Integer actualPrice = itemPage.getItemPrice();

        Assertions.assertTrue(actualName.toLowerCase().contains(expectedItem.toLowerCase()));
        //Assertions.assertTrue(actualPrice >= expectedPriceMin && actualPrice <= expectedPriceMax);
    }
}
