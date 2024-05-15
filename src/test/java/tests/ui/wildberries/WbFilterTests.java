package tests.ui.wildberries;

import org.junit.jupiter.api.Test;
import tests.ui.wildberries.pages.MainPage;

public class WbFilterTests extends BaseTest {

    @Test
    public void searchResultTest() {
        String expectedItem = "телефон";
        MainPage mainPage = new MainPage(driver);
        mainPage.searchItem(expectedItem);
    }
}
