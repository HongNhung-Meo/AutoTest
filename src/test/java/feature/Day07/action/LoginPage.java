package feature.Day07.action;

import feature.Day07.ui.LoginPageUI;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
// constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(LoginPageUI.USERNAME_FIELD).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(LoginPageUI.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(LoginPageUI.LOGIN_BUTTON).click();
    }

    public String getErrorMessage() {
        return driver.findElement(LoginPageUI.ERROR_MESSAGE).getText();
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(LoginPageUI.APP_LOGO).isDisplayed();
    }

    public int getInventoryItemCount() {
        return driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
    }
}

