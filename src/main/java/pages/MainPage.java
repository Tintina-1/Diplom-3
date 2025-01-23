package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalCabinetButton = By.xpath("//p[contains(text(),'Личный Кабинет')]");
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG tab_tab_type_current')]");


    private WebElement bunsSection;
    private WebElement saucesSection;
    private WebElement fillingsSection;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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


    public void switchToTab(String tabName) {
        By tabLocator = By.xpath(String.format("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='%s']]", tabName));
        WebElement tabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(tabLocator));
        wait.until(ExpectedConditions.elementToBeClickable(tabElement));

        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabElement);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", tabElement);

        wait.until(driver -> {
            String activeTabText = driver.findElement(activeTab).getText();
            return activeTabText.equals(tabName);
        });
    }

    public void switchToTabsSequentially(String... tabNames) {
        for (String tabName : tabNames) {
            switchToTab(tabName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab));
            waitForSomeTime(2);
        }
    }

    public void waitForSomeTime(int seconds) {
        wait.until(driver -> {
            try {
                Thread.sleep(seconds * 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    @Step("Проверка названия активной вкладки")
    public boolean isTabActive(String tabName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab));
        WebElement activeTabElement = driver.findElement(activeTab);
        return activeTabElement.getText().equals(tabName);
    }


    @Step("Получение текущей активной секции")
    private WebElement getActiveSection() {
        String activeTabName = driver.findElement(activeTab).getText();

        switch (activeTabName) {
            case "Булки":
                return bunsSection;
            case "Соусы":
                return saucesSection;
            case "Начинки":
                return fillingsSection;
            default:
                throw new IllegalStateException("Неизвестная активная вкладка: " + activeTabName);
        }
    }

    public List<String> getIngredientsInActiveTab() {
        WebElement activeSection = getActiveSection();
        List<WebElement> ingredientElements = activeSection.findElements(By.cssSelector(".BurgerIngredient_ingredient__text__yp3dH"));
        return ingredientElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}
