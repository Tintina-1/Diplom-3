package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalCabinetButton = By.xpath("//p[contains(text(),'Личный Кабинет')]");

    private final By bunsTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Булки']]");
    private final By saucesTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Соусы']]");
    private final By fillingsTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Начинки']]");

    private WebElement bunsSection;
    private WebElement saucesSection;
    private WebElement fillingsSection;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void initializeSections() {
        List<WebElement> sections = driver.findElements(By.cssSelector("ul.BurgerIngredients_ingredients__list__2A-mT"));
        if (sections.size() >= 3) {
            bunsSection = sections.get(0);
            saucesSection = sections.get(1);
            fillingsSection = sections.get(2);
        } else {
            throw new IllegalStateException("Не удалось найти все секции ингредиентов. Убедитесь, что они отображаются на странице.");
        }
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Ожидание кликабельности кнопки Личный кабинет")
    public void clickPersonalCabinetButton() {
        WebElement clickPersonalCabinetReady = wait.until(ExpectedConditions.elementToBeClickable(personalCabinetButton));
        clickPersonalCabinetReady.click();
    }

    public void waitForMainPageToLoad() {
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
    }

    @Step("Ожидание отображения вкладок ингредиентов")
    public void waitForIngredientTabsToDisplay() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(bunsTab));
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

    @Step("Проверка наполнения данными вкладки Булки")
    public boolean areBunsDisplayed() {
        return bunsSection.isDisplayed();
    }

    @Step("Проверка наполнения данными вкладки Соусы")
    public boolean areSaucesDisplayed() {
        return saucesSection.isDisplayed();
    }

    @Step("Проверка наполнения данными вкладки Начинки")
    public boolean areFillingsDisplayed() {
        return fillingsSection.isDisplayed();
    }

    @Step("Проверка наличия конкретной булки")
    public boolean isBunPresent(String bunName) {
        List<WebElement> buns = bunsSection.findElements(By.cssSelector(".BurgerIngredient_ingredient__1TVf6"));
        for (WebElement bun : buns) {
            String currentBunName = bun.findElement(By.cssSelector(".BurgerIngredient_ingredient__text__yp3dH")).getText();
            if (currentBunName.equals(bunName)) {
                return true;
            }
        }
        return false;
    }

    @Step("Проверка наличия конкретного соуса")
    public boolean isSaucePresent(String sauceName) {
        List<WebElement> sauces = saucesSection.findElements(By.cssSelector(".BurgerIngredient_ingredient__1TVf6"));
        for (WebElement sauce : sauces) {
            String currentSauceName = sauce.findElement(By.cssSelector(".BurgerIngredient_ingredient__text__yp3dH")).getText();
            if (currentSauceName.equals(sauceName)) {
                return true;
            }
        }
        return false;
    }

    @Step("Проверка наличия конкретной начинки")
    public boolean isFillingPresent(String fillingName) {
        List<WebElement> fillings = fillingsSection.findElements(By.cssSelector(".BurgerIngredient_ingredient__1TVf6"));
        for (WebElement filling : fillings) {
            String currentFillingName = filling.findElement(By.cssSelector(".BurgerIngredient_ingredient__text__yp3dH")).getText();
            if (currentFillingName.equals(fillingName)) {
                return true;
            }
        }
        return false;
    }
}
