package tests.ui.wildberries;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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
    }
}
