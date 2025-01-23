package tests;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.RegisterPage;

import static org.junit.Assert.*;

import utils.ApiUtils;
import utils.TestDataUtils;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RegisterTests extends BaseTest {

    private RegisterPage registerPage;
    private ApiUtils apiUtils;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String authToken;
    private String browser;

    public RegisterTests(String browser) {
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
        driver.get(baseUrl + "register");
        registerPage = new RegisterPage(driver);
        apiUtils = new ApiUtils(baseUrl);

        // Генерация уникальных данных для теста
        userName = TestDataUtils.generateUniqueName();
        userEmail = TestDataUtils.generateUniqueEmail();
        userPassword = TestDataUtils.generateUniquePassword();
    }

    @Test
    @Description("Успешная регистрация с валидным именем, почтой и паролем более чем из 6 символов.")
    public void testSuccessfulRegistration() {
        registerPage.register(userName, userEmail, userPassword);
        registerPage.waitForRedirectionToLoginPage();

        assertTrue("Пользователь не был перенаправлен на страницу логина.",
                driver.getCurrentUrl().contains("login"));

        authToken = apiUtils.loginUser(userEmail, userPassword);
        assertNotNull("Token не должен быть null", authToken);
    }

    @Test
    @Description("Получение ошибки Некорректный Пароль для некорректного пароля. Минимальный пароль — шесть символов.")
    public void testRegistrationWithShortPassword() {
        String shortPassword = "123";
        authToken = apiUtils.registerUser(userName, userEmail, shortPassword);
        registerPage.enterName(userName);
        registerPage.enterEmail(userEmail);
        registerPage.enterPassword(shortPassword);
        registerPage.clickRegisterButton();

        assertTrue("Ошибка для короткого пароля не отображается.",
                registerPage.isErrorDisplayed());
        assertEquals("Некорректный пароль", registerPage.getErrorText());
    }

    @After
    public void tearDownTest() {
        if (authToken != null) {
            apiUtils.deleteUser(authToken);
        }
        tearDown();
    }
}
