package feature.Day07.feature;

import feature.Day07.action.LoginPage;
import feature.Day07.ui.LoginPageUI;
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

public class ModuleDataTest extends Hook {
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

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        String excelFilePath = "loginData.xlsx";

        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, "Sheet1");
        Object[][] data = new Object[excelData.size()][1]; // Mỗi row là 1 Map<String, String>
        for (int i = 0; i < excelData.size(); i++) {
            data[i][0] = excelData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(Map<String, String> rowData) {
        String username = rowData.getOrDefault("Username", "");
        String password = rowData.getOrDefault("Password", "");
        String expectedResult = rowData.getOrDefault("ExpectedResult", "").toLowerCase();
        String errorMsg = rowData.getOrDefault("ExpectedErrorMessage", "");

        inputData(username, password);
        clickLogin();

        if ("success".equals(expectedResult)) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed unexpectedly!");
            Assert.assertTrue(loginPage.isLogoDisplayed(), "Logo không hiển thị!");
            Assert.assertTrue(loginPage.getInventoryItemCount() > 0, "Không có sản phẩm!");

        } else if ("failure".equals(expectedResult)) {
            WebElement error = driver.findElement(LoginPageUI.ERROR_MESSAGE);
            Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi!");
            Assert.assertTrue(error.getText().contains(errorMsg), "Thông báo lỗi sai!");
        } else {
            Assert.fail("ExpectedResult trong Excel phải là 'success' hoặc 'failure'");
        }
    }
}
