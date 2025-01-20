package tests;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.PersonalCabinetPage;
import utils.ApiUtils;
import utils.TestDataUtils;

import java.time.Duration;

public class PersonalCabinetTests extends BaseTest {

    private ApiUtils apiUtils;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String authToken;
    private String expectedUrl;
    private PersonalCabinetPage personalCabinetPage;

    @Before
    public void setUpTest() {
        setUp("chrome");
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
    @Description("Успешный переход в личный кабинет по клику кнопку Личный Кабинет.")
    public void loginThroughPersonalCabinetButton() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("Не удаётся попасть на страницу Личного кабинета", expectedUrl, currentUrl);
    }

    @Test
    @Description("Успешный переход по клику на Конструктор из личного кабинета в конструктор ингредиентов на главной странице.")
    public void testConstructorRedirectFromProfile() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        personalCabinetPage.clickConstructorButton();

        wait.until(ExpectedConditions.urlToBe(baseUrl));

        Assert.assertEquals("Редирект не на главную страницу", baseUrl, driver.getCurrentUrl());
    }

    @Test
    @Description("Успешный переход по клику на логотип Stellar Burgers из личного кабинета на главную страницу.")
    public void testLogoRedirectFromProfile() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        personalCabinetPage.clickLogoButton();

        wait.until(ExpectedConditions.urlToBe(baseUrl));

        Assert.assertEquals("Редирект не на главную страницу", baseUrl, driver.getCurrentUrl());
    }

    @After
    public void tearDownTest() {
        if (authToken != null) {
            apiUtils.deleteUser(authToken);
        }
        tearDown();
    }
}

