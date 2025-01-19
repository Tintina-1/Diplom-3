package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.RegisterPage;
import static org.junit.Assert.*;
import utils.ApiUtils;
import utils.TestDataUtils;

public class RegisterTests extends BaseTest {

    private RegisterPage registerPage;
    private ApiUtils apiUtils;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String authToken;

    @Before
    public void setUpTest() {
        setUp(System.getProperty("browser", "chrome"));
        driver.get(baseUrl + "register");
        registerPage = new RegisterPage(driver);
        apiUtils = new ApiUtils(baseUrl);

        // Генерация уникальных данных для теста
        userName = TestDataUtils.generateUniqueName();
        userEmail = TestDataUtils.generateUniqueEmail();
        userPassword = TestDataUtils.generateUniquePassword();
    }

    @Test
    public void testSuccessfulRegistration() {
        registerPage.register(userName, userEmail, userPassword);
        registerPage.waitForRedirectionToLoginPage();

        assertTrue("Пользователь не был перенаправлен на страницу логина.",
                driver.getCurrentUrl().contains("login"));

        authToken = apiUtils.loginUser(userEmail, userPassword);
        assertNotNull("Token не должен быть null", authToken);
    }

    @Test
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
