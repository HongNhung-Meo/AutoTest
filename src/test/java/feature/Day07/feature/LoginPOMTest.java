package feature.Day07.feature;

import feature.Day07.action.LoginPage;
import feature.Day07.untils.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class LoginPOMTest extends Hook {
    LoginPage loginPage;

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testLoginFunctionality(String username, String password, String expectedResult, String expectedErrorMsg) {
        // check title & URL đầu trang
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isLogoDisplayed(), "Logo missing on inventory page!");
    }

    @Test
    public void testLoginWithInvalidUsername() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String err = loginPage.getErrorMessage();
        System.out.println(err);


    }
}
