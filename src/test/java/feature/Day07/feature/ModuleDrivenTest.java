package feature.Day07.feature;

import feature.Day07.action.LoginPage;
import feature.Day07.untils.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ModuleDrivenTest extends Hook {
    LoginPage loginPage;

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(driver);
    }

    public void inputData(String user, String pass) {
        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
    }

    public void clickLogin() {
        loginPage.clickLogin();
    }

    @Test
    public void inputValidUserAndPass() {

        inputData("standard_user", "secret_sauce");
        clickLogin();

    }

    @Test
    public void inputInValidUserAndPass() {

        inputData("standard_user1", "secret_sauce");
        clickLogin();

        WebElement error = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(error.isDisplayed());

    }


}
