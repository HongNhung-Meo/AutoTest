package assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import untils.ExcelUntils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class RequestDemoTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    @DataProvider(name = "formData")
    public Object[][] formData() {
        // Đường dẫn file excel
        String excelFilePath = "formData.xlsx";
        String sheetName = "Sheet1";
        // Đọc dữ liệu từ file excel
        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, sheetName);

        Object[][] data = new Object[excelData.size()][12];

        for (int i = 0; i < excelData.size(); i++) {
            Map<String, String> row = excelData.get(i);
            data[i] = new Object[]{row.get("Business Email"), row.get("First Name"), row.get("Last Name"), row.get("Company"), row.get("Phone Number"), row.get("Country"), row.get("State"), row.get("Interest"), row.get("Comments"), Boolean.parseBoolean(row.get("Checkbox")), row.get("ExpectedResult"), row.get("ExpectedErrorMessage")};
        }
        return data;
    }

    @Test(dataProvider = "formData")
    public void testForm(String email, String firstName, String lastName, String company, String phone, String country, String state, String interest, String comment, boolean checkbox, String expectedResult, String expectedErrorMsg) {
        // check title & URL đầu trang
        Assert.assertEquals(driver.getTitle(), "Request a Sauce Labs Demo", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/request-demo", "URL mismatch!");

        // kiểm tra email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
        fillInput(emailInput, email);
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("mktoButton")));
        submitButton.click();
        if (!expectedResult.equals("success") && expectedErrorMsg.toLowerCase().contains("email")) {
            WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ValidMsgEmail")));
            Assert.assertTrue(emailError.getText().contains(expectedErrorMsg), "Expected email error message not shown!");
            return;
        }

        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName")));
        WebElement lastNameInput = driver.findElement(By.id("LastName"));
        WebElement companyInput = driver.findElement(By.id("Company"));
        WebElement phoneInput = driver.findElement(By.id("Phone"));
        WebElement countryDropdown = driver.findElement(By.id("Country"));
        WebElement interestDropdown = driver.findElement(By.id("Solution_Interest__c"));
        WebElement commentInput = driver.findElement(By.id("Sales_Contact_Comments__c"));

        List<WebElement> fields = List.of(emailInput, firstNameInput, lastNameInput, phoneInput, countryDropdown, interestDropdown, commentInput, submitButton);
        for (WebElement field : fields) {
            Assert.assertTrue(field.isDisplayed(), field.getAttribute("id") + "not displayed!");
        }

        fillInput(firstNameInput, firstName);
        fillInput(lastNameInput, lastName);
        fillInput(companyInput, company);
        fillInput(phoneInput, phone);
        fillInput(commentInput, comment);
        selectDropdownByVisibleText(countryDropdown, country);
        selectDropdownByVisibleText(interestDropdown, interest);

        // kiểm tra checkbox khi chọn state
        boolean shouldShowCheckbox = true;
        if (country.equalsIgnoreCase("United States")) {
            WebElement stateDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("State")));
            Assert.assertTrue(stateDropdown.isDisplayed(), "State dropdown not displayed!");

            wait.until(driver -> new Select(stateDropdown).getOptions().size() > 1);

            if (state != null && !state.isEmpty()) {
                selectDropdownByVisibleText(stateDropdown, state);
                shouldShowCheckbox = false;
            }
        }
        // kiểm tra checkbox
        if (shouldShowCheckbox) {
            try {
                WebElement checkboxLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("label[for='mktoCheckbox_46340_0']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxLabel);
                wait.until(ExpectedConditions.elementToBeClickable(checkboxLabel));

                WebElement checkboxInput = driver.findElement(By.id("mktoCheckbox_46340_0"));
                if (checkbox != checkboxInput.isSelected()) {
                    checkboxLabel.click();
                }
            } catch (Exception e) {
                Assert.fail("Checkbox expected but not found or clickable.");
            }
        }
        submitButton.click();

        //kiểm tra theo kì vọng
        boolean isThankYouPage;
        try {
            wait.until(ExpectedConditions.urlContains("thank-you-contact"));
            isThankYouPage = true;
        } catch (Exception e) {
            isThankYouPage = false;
        }

        if (expectedResult.equals("success")) {
            Assert.assertTrue(isThankYouPage, "Expected success, but not redirected to thank you page!");

            String actualURL = driver.getCurrentUrl();
            Assert.assertEquals(actualURL, "https://saucelabs.com/thank-you-contact?concierge=true", "Form submission failed when expected to succeed!");

            WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='THANK YOU']")));
            Assert.assertTrue(thankYouMessage.isDisplayed(), "Thank You message not displayed!");
        } else {
            // Trường hợp form thiếu vaslidate
            boolean uiBug = false;

            if (isThankYouPage) {
                System.out.println("BUG: Expected failure but form submitted successfully!");
                uiBug = true;
            }

            List<WebElement> errorMsgs = driver.findElements(By.className("mktoErrorMsg"));
            if (errorMsgs.isEmpty()) {
                System.out.println("BUG UI: No error message displayed! Expected: " + expectedErrorMsg);
                uiBug = true;
            } else {
                boolean match = false;
                for (WebElement errMsg : errorMsgs) {
                    if (errMsg.getText().contains(expectedErrorMsg)) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    System.out.println("BUG UI: Error message shown but incorrect content. Expected: " + expectedErrorMsg);
                    uiBug = true;
                }
            }
            if (uiBug) {
                Assert.fail("Form submitted or error message missing when it should have failed!");
            }
        }
    }

    private void fillInput(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public void selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
        if (visibleText == null || visibleText.trim().isEmpty()) {
            return;
        }
        try {
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText.trim());
        } catch (Exception e) {
            System.out.println("Unable to find option with text: '" + visibleText + "' in dropdown.");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}