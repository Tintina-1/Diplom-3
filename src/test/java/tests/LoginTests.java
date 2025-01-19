package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import utils.ApiUtils;
import utils.TestDataUtils;



public class LoginTests extends BaseTest {

    private ApiUtils apiUtils;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String authToken;
    private RegisterPage registerPage;
    private MainPage mainPage;
    private LoginPage loginPage;

    @Before
    public void setUpTest() {
        setUp("chrome");
        driver.get(baseUrl);

        apiUtils = new ApiUtils(baseUrl);

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);

        userName = TestDataUtils.generateUniqueName();
        userEmail = TestDataUtils.generateUniqueEmail();
        userPassword = TestDataUtils.generateUniquePassword();
    }

    @Test
    public void loginThroughMainPageButton() {

        authToken = apiUtils.registerUser(userName, userEmail, userPassword);

        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userEmail, userPassword);

        mainPage.waitForMainPageToLoad();

        Assert.assertEquals("Редирект не на главную страницу", baseUrl, driver.getCurrentUrl());
    }

    @Test
    public void loginThroughPersonalCabinetButton() {
        authToken = apiUtils.registerUser(userName, userEmail, userPassword);

        mainPage.clickPersonalCabinetButton();
        loginPage.login(userEmail, userPassword);

        mainPage.waitForMainPageToLoad();

        Assert.assertEquals("Редирект не на главную страницу", baseUrl, driver.getCurrentUrl());
    }

    @Test
    public void loginThroughRegisterPageButton() {

        driver.get(baseUrl + "register");

        registerPage.register(userName, userEmail, userPassword);
        registerPage.waitForRedirectionToLoginPage();

        loginPage.login(userEmail, userPassword);

        mainPage.waitForMainPageToLoad();

        // Проверяем успешный вход
        Assert.assertEquals("Редирект не на главную страницу", baseUrl, driver.getCurrentUrl());
    }

    @Test
    public void loginThroughPasswordRecoveryButton() {
        authToken = apiUtils.registerUser(userName, userEmail, userPassword);
        driver.get(baseUrl + "login");

        loginPage.clickForgotPassword();

        ForgotPasswordPage passwordPage = new ForgotPasswordPage(driver);
        passwordPage.clickLoginButton();
        loginPage.login(userEmail, userPassword);

        mainPage.waitForMainPageToLoad();

        // Проверяем успешный вход
        Assert.assertEquals("Редирект не на главную страницу", baseUrl, driver.getCurrentUrl());
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

