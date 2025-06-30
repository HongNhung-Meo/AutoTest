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

public class DataDrivenTest extends Hook {
    LoginPage loginPage;
    String excelFilePath = "loginData.xlsx";
    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(driver);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, "Sheet1");
        Object[][] data = new Object[excelData.size()][1]; // Mỗi row là 1 Map<String, String>
        for (int i = 0; i < excelData.size(); i++) {
            data[i][0] = excelData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(Map<String, String> rowData) {
        String username = rowData.get("Username");
        String password = rowData.get("Password");
        String expectedResult = rowData.get("ExpectedResult");
        String expectedErrorMsg = rowData.get("ExpectedErrorMessage");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed unexpectedly!");
        } else if ("failure".equalsIgnoreCase(expectedResult)) {
            WebElement error = driver.findElement(By.xpath("//h3[@data-test='error']"));
            Assert.assertTrue(error.isDisplayed(), "Expected error message not displayed!");
        } else {
            Assert.fail("ExpectedResult trong Excel phải là 'success' hoặc 'failure'");
        }
    }
}
