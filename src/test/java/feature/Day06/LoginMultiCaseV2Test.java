package feature.Day06;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class LoginMultiCaseV2Test {
    WebDriver driver;

    @BeforeMethod // mỗi test mở lại trang mới
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        // Đường dẫn file excel
        String excelFilePath = "loginData.xlsx";
        String sheetName = "Sheet1";
        // Đọc dữ liệu từ file excel
        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, sheetName);

        Object[][] data = new Object[excelData.size()][4];

        for (int i = 0; i < excelData.size(); i++) {
            Map<String, String> row = excelData.get(i);
            data[i][0] = row.get("Username");
            data[i][1] = row.get("Password");
            data[i][2] = row.get("ExpectedResult");
            data[i][3] = row.get("ExpectedErrorMessage");
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLoginFunctionality(String username, String password, String expectedResult, String expectedErrorMsg) {
        // check title & URL đầu trang
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // điền dữ liệu
        WebElement userInput = driver.findElement(By.id("user-name"));
        WebElement passInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        //hiển thị đúng trên giao diện người dùng
        Assert.assertTrue(userInput.isDisplayed(), "Username field not displayed!");
        Assert.assertTrue(passInput.isDisplayed(), "Password field not displayed!");
        Assert.assertTrue(loginButton.isDisplayed(), "Login button not displayed!");

        userInput.sendKeys(username);
        passInput.sendKeys(password);
        loginButton.click();

        //kiểm tra theo kì vọng
        if (expectedResult.equals("success")) {
            String actualURL = driver.getCurrentUrl();
            Assert.assertEquals(actualURL, "https://www.saucedemo.com/inventory.html", "Login failed when expected to succeed!");

            //check Logo & Item
            WebElement logo = driver.findElement(By.className("app_logo"));
            Assert.assertTrue(logo.isDisplayed(), "Logo missing on inventory page!");

            int itemCount = driver.findElements(By.className("inventory_item")).size();
            Assert.assertTrue(itemCount > 0, "No products displayed!");
        } else {
            //có lỗi, kiểm tra error message
            WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");

            Assert.assertTrue(errorMsg.getText().contains(expectedErrorMsg), "Unexpected error message text!");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
