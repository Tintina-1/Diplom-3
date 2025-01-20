package tests;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.PersonalCabinetPage;
import utils.ApiUtils;
import utils.TestDataUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LogOutTests extends BaseTest {

    private ApiUtils apiUtils;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String authToken;
    private String expectedUrl;
    private PersonalCabinetPage personalCabinetPage;
    private String browser;

    public LogOutTests(String browser) {
        this.browser = browser;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][] {
                { "chrome" },
                { "yandex" }
        });
    }

    @Before
    public void setUpTest() {
        setUp(browser);
        driver.get(baseUrl);

        apiUtils = new ApiUtils(baseUrl);
        personalCabinetPage = new PersonalCabinetPage(driver);

        userName = TestDataUtils.generateUniqueName();
        userEmail = TestDataUtils.generateUniqueEmail();
        userPassword = TestDataUtils.generateUniquePassword();

        authToken = apiUtils.registerUser(userName, userEmail, userPassword);


        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalCabinetButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userEmail, userPassword);
        mainPage.clickPersonalCabinetButton();

        expectedUrl = "https://stellarburgers.nomoreparties.site/account/profile";
    }


    @Test
    @Description("Успешный выход по кнопке Выйти в личном кабинете, переход на страницу логина.")
    public void testLogOutFromProfile() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        personalCabinetPage.clickLogOutButton();
        wait.until(ExpectedConditions.urlToBe(baseUrl + "login"));

        Assert.assertEquals("Редирект не на страницу логина", baseUrl + "login", driver.getCurrentUrl());
    }


    @After
    public void tearDownTest() {
        // Удаление пользователя через API после теста
        if (authToken != null) {
            apiUtils.deleteUser(authToken);
        }
        tearDown();
    }
}