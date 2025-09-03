package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginPage {
    WebDriver driver;

    // Locators
    private By emailField = By.xpath("//*[@id=\"edit-account\"]");
    private By passwordField = By.xpath("//*[@id=\"edit-password\"]");
    private By loginBtn = By.xpath("//*[@id=\"edit-submit\"]");
    private By errorMsg = By.xpath("//*[@id=\"edit-account-error\"]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }
}
