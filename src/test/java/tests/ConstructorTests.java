package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import io.qameta.allure.Description;


public class ConstructorTests extends BaseTest {

    private MainPage mainPage;

    @Before
    public void setUpTest() {
        setUp("chrome");
        driver.get(baseUrl);
        mainPage = new MainPage(driver);
    }

    @Test
    @Description("Успешный переход на вкладку Булки и проверка отображения валидных булок")
    public void testBunsSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();

        Assert.assertTrue("Ингредиенты в разделе 'Булки' не отображаются", mainPage.areBunsDisplayed());
    }

    @Test
    @Description("Успешный переход на вкладку Соусы и проверка отображения валидных соусов")
    public void testSaucesSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickFillingsTab();
        mainPage.clickSaucesTab();

        Assert.assertTrue("Элементы в разделе 'Соусы' не отображаются", mainPage.areSaucesDisplayed());
    }

    @Test
    @Description("Успешный переход на вкладку Начинки и проверка отображения валидных начинок")
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
