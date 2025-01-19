package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By nameFields = By.name("name");
    private final By passwordField = By.name("Пароль");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By errorText = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/p");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterName(String userName) {
        driver.findElements(nameFields).get(0).sendKeys(userName);
    }

    public void enterEmail(String userEmail) {
        driver.findElements(nameFields).get(1).sendKeys(userEmail);
    }

    public void enterPassword(String userPassword) {
        driver.findElement(passwordField).sendKeys(userPassword);
    }

    public void fillRegistrationForm(String userName, String userEmail, String userPassword) {
        enterName(userName);
        enterEmail(userEmail);
        enterPassword(userPassword);
    }

    public void register(String userName, String userEmail, String userPassword) {
        fillRegistrationForm(userName, userEmail, userPassword);
        clickRegisterButton();
    }

    public void waitForRedirectionToLoginPage() {
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    public void clickRegisterButton() {

        WebElement registerButtonReady = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButtonReady.click();
    }

    public String getErrorText() {
        return driver.findElement(errorText).getText();
    }

    public boolean isErrorDisplayed() {
        return driver.findElement(errorText).isDisplayed();
    }

}
