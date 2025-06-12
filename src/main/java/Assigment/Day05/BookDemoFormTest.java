package Assigment.Day05;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import untils.ExcelUntils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class BookDemoFormTest {
    public static void main(String[] args) {

        String excelFilePath = "book.xlsx";
        String sheetName = "Sheet1";

        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, sheetName);

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            for (Map<String, String> rowData : excelData) {
                System.out.println("Dữ liệu hàng: " + rowData);
                String email = rowData.get("Business Email");
                String firstName = rowData.get("First Name");
                String lastName = rowData.get("Last Name");
                String company = rowData.get("Company");
                String phoneNumber = rowData.get("Phone Number");
                String country = rowData.get("Country");
                String interest = rowData.get("Interest");
                String comments = rowData.get("Comments");
                driver.get("https://saucelabs.com/request-demo");

                WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
                emailInput.sendKeys(email);

                WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName")));
                firstNameInput.sendKeys(firstName);

                WebElement lastNameInput = driver.findElement(By.id("LastName"));
                lastNameInput.sendKeys(lastName);

                WebElement companyInput = driver.findElement(By.id("Company"));
                companyInput.sendKeys(company);

                WebElement phoneInput = driver.findElement(By.id("Phone"));
                phoneInput.sendKeys(phoneNumber);

                WebElement countrySelect = driver.findElement(By.id("Country"));
                Select countryDropdown = new Select(countrySelect);
                countryDropdown.selectByVisibleText(country);

                WebElement interestSelect = driver.findElement(By.id("Solution_Interest__c"));
                Select interestDropdown = new Select(interestSelect);
                interestDropdown.selectByVisibleText(interest);

                WebElement commentsInput = driver.findElement(By.id("Sales_Contact_Comments__c"));
                commentsInput.sendKeys(comments);

                WebElement checkbox = driver.findElement(By.id("mktoCheckbox_46340_0"));
                checkbox.click();

                WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("mktoButton")));
                submitButton.click();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm phần tử thao tác: " + e.getMessage());

        } finally {
            driver.quit();
        }

    }

}
