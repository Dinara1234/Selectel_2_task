package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectelTests {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "https://selectel.ru";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Тест проверяет, что заголовок главной страницы содержит слово "Selectel"
    @Test
    public void testPageTitle() {
        MainPage mainPage = new MainPage(driver);
        String title = mainPage.getPageTitle();
        assertTrue(title.contains("Selectel"), "Заголовок страницы должен содержать 'Selectel'");
    }

    // Тест проверяет, что логотип отображается на главной странице
    @Test
    public void testLogoIsDisplayed() {
        MainPage mainPage = new MainPage(driver);
        assertTrue(mainPage.isLogoDisplayed(), "Логотип не отображается на главной странице");
    }

    // Тест проверяет, что ссылка "Документация" открывается в новой вкладке и ведёт на docs.selectel.ru
    @Test
    public void testDocsLinkOpensInNewTab() {
        MainPage mainPage = new MainPage(driver);
        String originalWindow = driver.getWindowHandle();
        mainPage.clickDocsLink();

        wait.until(d -> d.getWindowHandles().size() > 1);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("docs.selectel.ru"), "Ссылка на документацию ведёт не на docs.selectel.ru");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    // Тест проверяет, что меню "Продукты" открывается
    @Test
    public void testProductsMenuOpens() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProductsMenu();
        assertTrue(mainPage.isNavDropdownDisplayed(), "Выпадающее меню 'Продукты' не появилось");
    }






}
