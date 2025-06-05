package Assigment.Day03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Case02 {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement email = driver.findElement(By.id("Email"));
        email.sendKeys("meo@gmail.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName")));

        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys("Hong Nhung");

        WebElement lastName = driver.findElement(By.id("LastName"));
        lastName.sendKeys("Pham");

        WebElement company = driver.findElement(By.id("Company"));
        company.sendKeys("DevPro");

        WebElement phone = driver.findElement(By.id("Phone"));
        phone.sendKeys("0337561033");

        WebElement country = driver.findElement(By.id("Country"));
        Select selectCountry = new Select(country);
        selectCountry.selectByVisibleText("Vietnam");

//        WebElement interest = driver.findElement(By.id("Solution_Interest__c"));
//        Select selectInterest = new Select(interest);
//        selectInterest.selectByVisibleText("Visual Testing");

        WebElement comments = driver.findElement(By.id("Sales_Contact_Comments__c"));
        comments.sendKeys("Testing");

        WebElement checkbox = driver.findElement(By.id("mktoCheckbox_46340_0"));
        checkbox.click();

        WebElement button = driver.findElement(By.className("mktoButton"));
        button.click();

        Thread.sleep(3000);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ValidMsgSolution_Interest__c")));
            System.out.println("Trường Interest chưa chọn");
        } catch (Exception e) {
            System.out.println("Không có thông báo lỗi");
        }

        driver.quit();
    }
}
