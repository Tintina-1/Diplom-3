package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    protected WebDriver driver;
    protected String baseUrl = "https://stellarburgers.nomoreparties.site/";

    public void setUp(String browser) {
        switch (browser.toLowerCase()) {
            case "yandex":
                WebDriverManager.chromedriver().setup();
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary("/usr/bin/yandex-browser");
                driver = new ChromeDriver(yandexOptions);
                break;
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
        }
        driver.manage().window().maximize();
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
