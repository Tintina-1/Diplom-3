package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalCabinetButton = By.xpath("//p[contains(text(),'Личный Кабинет')]");


    private final By bunsTab = By.cssSelector("div.tab_tab__1SPyG.tab_tab_type_current__2BEPc");
    private final By saucesTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Соусы']]");;
    private final By fillingsTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Начинки']]");;
    private final By bunsSection = By.cssSelector("ul.BurgerIngredients_ingredients__list__2A-mT");
    private final By saucesSection = By.cssSelector("ul.BurgerIngredients_ingredients__list__2A-mT");
    private final By fillingsSection = By.cssSelector("ul.BurgerIngredients_ingredients__list__2A-mT");


    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickPersonalCabinetButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement clickPersonalCabinetReady = wait.until(ExpectedConditions.elementToBeClickable(personalCabinetButton));
        clickPersonalCabinetReady.click();
    }

    public void waitForMainPageToLoad() {
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
    }

    public void waitForIngredientTabsToDisplay() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(bunsTab));
    }

    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    public void clickSaucesTab() {
        driver.findElement(saucesTab).click();
    }

    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    public boolean areBunsDisplayed() {
        List<WebElement> buns = driver.findElements(bunsSection);
        return !buns.isEmpty();
    }

    public boolean areSaucesDisplayed() {
        List<WebElement> sauces = driver.findElements(saucesSection);
        return !sauces.isEmpty();
    }

    public boolean areFillingsDisplayed() {
        List<WebElement> fillings = driver.findElements(fillingsSection);
        return !fillings.isEmpty();
    }
}
