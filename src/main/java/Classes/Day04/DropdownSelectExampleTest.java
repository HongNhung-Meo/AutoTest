package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownSelectExampleTest {
    public static void main(String[] args) throws InterruptedException {
        //khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");

        //tìm phần tử dropdown
        WebElement interest = driver.findElement(By.id("Solution_Interest__c"));

        //sử dụng select để tương tác với dropdown
        Select selectInterest = new Select(interest);

        //chọn 1 tuỳ chọn bằng giá trị
        selectInterest.selectByValue("Visual Testing");

        //chọn 1 tuỳ chọn bằng văn bản hiển thị
//        selectInterest.selectByVisibleText("Visual Testing");

        //chọn 1 tuỳ chọn bằng chỉ số index
//        selectInterest.selectByIndex(1);
        System.out.println("Interest selected successfully");

        Thread.sleep(3000);
        driver.quit();
    }
}
