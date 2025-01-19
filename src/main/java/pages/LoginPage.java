package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By emailField = By.name("name");
    private final By passwordField = By.name("Пароль");
    private final By registerButton = By.xpath("//a[contains(text(),'Зарегистрироваться')]");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By forgotPasswordButton = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Задаем время ожидания
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void fillLoginForm(String email, String password){
        enterEmail(email);
        enterPassword(password);
    }

    public void login(String email, String password) {
        fillLoginForm(email, password);
        clickLoginButton();
    }

    public void clickLoginButton() {
        WebElement loginButtonReady =  wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButtonReady.click();
    }

    public void clickForgotPassword() {
        driver.findElement(forgotPasswordButton).click();
    }
}
