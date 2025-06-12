package Classes.Day05;

import org.apache.xmlbeans.soap.SOAPArrayType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class LoginPageV2Test {
    public static void main(String[] args) {
        // Đường dẫn file excel
        String excelFilePath = "DataLogin.xlsx";
        String sheetName = "Sheet1";
        // Đọc dữ liệu từ file excel
        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, sheetName);

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Duyệt qua từng bản ghi trong dữ liệu

        for (Map<String, String> rowData : excelData) {
            System.out.println("Dữ liệu hàng: " + rowData);
            String user = rowData.get("Username");
            String pass = rowData.get("Password");
            driver.get("https://www.saucedemo.com/");

            WebElement usernameInput = driver.findElement(By.id("user-name"));
            usernameInput.sendKeys(user);
            WebElement passwordInput = driver.findElement(By.id("password"));
            passwordInput.sendKeys(pass);
        }
        driver.quit();
    }
}
