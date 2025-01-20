package tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class BaseTest {

    protected WebDriver driver;
    protected String baseUrl = "https://stellarburgers.nomoreparties.site/";

    public void setUp(String browser) {
        switch (browser.toLowerCase()) {
            case "yandex":
                // Указываем путь к YandexDriver
                File yandexDriver = new File("/home/inna/Downloads/yandexdriver-24.12.1.704-linux/yandexdriver");
                if (yandexDriver.exists()) {
                    // Устанавливаем путь к драйверу
                    System.setProperty("webdriver.chrome.driver", yandexDriver.getAbsolutePath());
                    ChromeOptions yandexOptions = new ChromeOptions();

                    // Указываем бинарный файл для Yandex браузера в Linux
                    yandexOptions.setBinary("/usr/bin/yandex-browser");

                    // Инициализируем ChromeDriver с указанными опциями для Yandex
                    driver = new ChromeDriver(yandexOptions);
                } else {
                    throw new IllegalStateException("YandexDriver not found!");
                }
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
