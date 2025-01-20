package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;

import java.time.Duration;

public class PersonalCabinetPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private final By userNamePersonal = By.name("Name");
    private final By constructorButton = By.xpath("//p[contains(text(),'Конструктор')]");
    private final By logoButton = By.xpath("//header[@class='AppHeader_header__X9aJA pb-4 pt-4']");
    private final By logOutButton = By.cssSelector(".Account_button__14Yp3");


    public PersonalCabinetPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ожидание окончания загрузки страницы и кликабельности кнопки Конструктор")
    public void clickConstructorButton() {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        WebElement clickConstructorReady = wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        clickConstructorReady.click();
    }

    @Step("Ожидание окончания загрузки страницы и кликабельности кнопки логотипа")
    public void clickLogoButton() {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        WebElement clickLogoReady = wait.until(ExpectedConditions.elementToBeClickable(logoButton));
        clickLogoReady.click();
    }

    @Step("Ожидание окончания загрузки страницы и кликабельности кнопки Выход")
    public void clickLogOutButton() {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        WebElement clickLogoReady = wait.until(ExpectedConditions.elementToBeClickable(logOutButton));
        clickLogoReady.click();
    }

}