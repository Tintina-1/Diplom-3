package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;


public class ConstructorTests extends BaseTest {

    private MainPage mainPage;

    @Before
    public void setUpTest() {
        setUp("chrome");
        driver.get(baseUrl);
        mainPage = new MainPage(driver);
    }

    @Test
    public void testBunsSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();

        Assert.assertTrue("Ингредиенты в разделе 'Булки' не отображаются", mainPage.areBunsDisplayed());
    }

    @Test
    public void testSaucesSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickFillingsTab();
        mainPage.clickSaucesTab();

        Assert.assertTrue("Элементы в разделе 'Соусы' не отображаются", mainPage.areSaucesDisplayed());
    }

    @Test
    public void testFillingsSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickSaucesTab();
        mainPage.clickFillingsTab();

        Assert.assertTrue("Элементы в разделе 'Начинки' не отображаются", mainPage.areFillingsDisplayed());
    }

    @After
    public void tearDownTest() {
        tearDown();
    }
}
