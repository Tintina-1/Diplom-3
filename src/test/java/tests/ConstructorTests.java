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
import java.util.List;


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
        mainPage.initializeSections();
    }


    @Test
    @Description("Успешная проверка отображения валидных булок при переходе со вкладки Начинки на вкладку Булки")
    public void testBunsSectionSwitch()  {
        mainPage.switchToTabsSequentially("Начинки", "Булки");

        List<String> expectedBuns = Arrays.asList(
                "Флюоресцентная булка R2-D3",
                "Краторная булка N-200i"
        );
        List<String> actualBuns = mainPage.getIngredientsInActiveTab();
        Assert.assertTrue("Не все ожидаемые булки отображены в активной вкладке",
                actualBuns.containsAll(expectedBuns));

    }


    @Test
    @Description("Успешный переход на вкладку Соусы и проверка отображения валидных соусов")
    public void testSaucesSection() {
        mainPage.switchToTab("Соусы");
        Assert.assertTrue("Вкладка 'Соусы' не активна", mainPage.isTabActive("Соусы"));
        List<String> expectedSauces = Arrays.asList(
                "Соус Spicy-X",
                "Соус фирменный Space Sauce",
                "Соус традиционный галактический",
                "Соус с шипами Антарианского плоскоходца"
        );

        List<String> actualSauces = mainPage.getIngredientsInActiveTab();
        Assert.assertTrue("Не все ожидаемые соусы отображены в активной вкладке",
                actualSauces.containsAll(expectedSauces));


    }


    @Test
    @Description("Успешный переход на вкладку Начинки и проверка отображения валидных начинок")
    public void testFillingsSection() {
        mainPage.switchToTab("Начинки");
        Assert.assertTrue("Вкладка 'Начинки' не активна", mainPage.isTabActive("Начинки"));
        List<String> expectedFillings = Arrays.asList(
                "Мясо бессмертных моллюсков Protostomia",
                "Говяжий метеорит (отбивная)",
                "Биокотлета из марсианской Магнолии",
                "Филе Люминесцентного тетраодонтимформа",
                "Хрустящие минеральные кольца",
                "Плоды Фалленианского дерева",
                "Кристаллы марсианских альфа-сахаридов",
                "Мини-салат Экзо-Плантаго",
                "Сыр с астероидной плесенью"
        );

        List<String> actualFillings = mainPage.getIngredientsInActiveTab();

        Assert.assertTrue("Не все ожидаемые начинки отображены в активной вкладке",
                actualFillings.containsAll(expectedFillings));
    }

    @After
    public void tearDownTest() {
        tearDown();
    }
}
