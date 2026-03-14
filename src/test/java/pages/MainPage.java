package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Логотип
    @FindBy(css = "a.header-main__logo")
    private WebElement logo;

    // Кнопка "Продукты" в главном меню
    @FindBy(xpath = "//button[contains(@class, 'main-nav__list-btn') and contains(text(), 'Продукты')]")
    private WebElement productsMenuButton;

    // Видимая область выпадающего меню (продукты)
    @FindBy(css = "div.nav-dropdown")
    private WebElement navDropdown;

    // Ссылка "Документация" в главном меню
    @FindBy(xpath = "//a[@href='https://docs.selectel.ru/']")
    private WebElement docsLink;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isLogoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(logo)).isDisplayed();
    }


    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickDocsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(docsLink)).click();
    }
    public void clickProductsMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(productsMenuButton)).click();
    }

    public boolean isNavDropdownDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(navDropdown)).isDisplayed();
    }






}