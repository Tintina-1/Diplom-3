package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import io.qameta.allure.Description;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConstructorTests extends BaseTest {

    private MainPage mainPage;
    private String browser;

    public ConstructorTests(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        });
    }

    @Before
    public void setUpTest() {
        setUp(browser);
        driver.get(baseUrl);
        mainPage = new MainPage(driver);
        mainPage.waitForMainPageToLoad();
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.initializeSections();
    }

    @Test
    @Description("Успешный переход на вкладку Булки и проверка отображения валидных булок")
    public void testBunsSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickFillingsTab();
        mainPage.clickBunsTab();

        Assert.assertTrue("Элементы в разделе 'Булки' не отображаются", mainPage.areBunsDisplayed());
        Assert.assertTrue("Булка 'Флюоресцентная булка R2-D3' отсутствует", mainPage.isBunPresent("Флюоресцентная булка R2-D3"));
        Assert.assertTrue("Булка 'Краторная булка N-200i' отсутствует", mainPage.isBunPresent("Краторная булка N-200i"));
    }

    @Test
    @Description("Успешный переход на вкладку Соусы и проверка отображения валидных соусов")
    public void testSaucesSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickSaucesTab();

        Assert.assertTrue("Элементы в разделе 'Соусы' не отображаются", mainPage.areSaucesDisplayed());
        Assert.assertTrue("Соус традиционный галактический отсутствует", mainPage.isSaucePresent("Соус традиционный галактический"));
        Assert.assertTrue("Соус 'Соус с шипами Антарианского плоскоходца' отсутствует", mainPage.isSaucePresent("Соус с шипами Антарианского плоскоходца"));
    }

    @Test
    @Description("Успешный переход на вкладку Начинки и проверка отображения валидных начинок")
    public void testFillingsSection() {
        mainPage.waitForIngredientTabsToDisplay();
        mainPage.clickFillingsTab();

        Assert.assertTrue("Элементы в разделе 'Начинки' не отображаются", mainPage.areFillingsDisplayed());
        Assert.assertTrue("Начинка 'Биокотлета из марсианской Магнолии' отсутствует", mainPage.isFillingPresent("Биокотлета из марсианской Магнолии"));
        Assert.assertTrue("Начинка 'Филе Люминесцентного тетраодонтимформа' отсутствует", mainPage.isFillingPresent("Филе Люминесцентного тетраодонтимформа"));
    }

    @After
    public void tearDownTest() {
        tearDown();
    }
}
