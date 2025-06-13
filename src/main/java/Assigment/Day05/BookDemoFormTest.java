package Assigment.Day05;

import org.openqa.selenium.By;
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            for (Map<String, String> rowData : excelData) {
                try {
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
                    emailInput.clear();
                    emailInput.sendKeys(email);

                    WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName")));
                    firstNameInput.clear();
                    firstNameInput.sendKeys(firstName);

                    WebElement lastNameInput = driver.findElement(By.id("LastName"));
                    lastNameInput.clear();
                    lastNameInput.sendKeys(lastName);

                    WebElement companyInput = driver.findElement(By.id("Company"));
                    companyInput.clear();
                    companyInput.sendKeys(company);

                    WebElement phoneInput = driver.findElement(By.id("Phone"));
                    phoneInput.clear();
                    phoneInput.sendKeys(phoneNumber);

                    selectDropdown(driver, "Country", country);

                    selectDropdown(driver, "Solution_Interest__c", interest);

                    WebElement commentsInput = driver.findElement(By.id("Sales_Contact_Comments__c"));
                    commentsInput.clear();
                    commentsInput.sendKeys(comments);

                    WebElement checkbox = driver.findElement(By.id("mktoCheckbox_46340_0"));
                    checkbox.click();

                    WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("mktoButton")));
                    submitButton.click();

                    WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='THANK YOU']")));
                    System.out.println("Thông báo: " + thankYouMessage.getText());
                    System.out.println("Đang ở trang: " + driver.getCurrentUrl());

                } catch (Exception e) {
                    System.out.println("Lỗi khi tìm phần tử thao tác: " + e.getMessage());
                }
            }
        } finally {
            driver.quit();
        }
    }

    public static void selectDropdown(WebDriver driver, String elementId, String visibleText) {
        WebElement dropDownElement = driver.findElement(By.id(elementId));
        Select dropDown = new Select(dropDownElement);
        dropDown.selectByVisibleText(visibleText);
    }
}