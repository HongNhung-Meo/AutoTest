package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class JavaScripExampleTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // tìm được element nhưng không thể click được, thì cách cuối để dùng nếu có trong html

        driver.get("https://saucelabs.com/request-demo");

        WebElement interest = driver.findElement(By.id("Solution_Interest__c"));

        // tạo đối tượng JavascriptExecutor, chuyển đổi driver thành JavascriptExecutor để thực thi các đoạn mã
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // thực thi đoạn mã JS và thay đổi giá trị của dropdown
        // thực hiện thay đổi giá trị chọn option ""
        js.executeScript("arguments[0].value='Visual Testing';", interest);

        Thread.sleep(3000);

        // kiểm tra giá trị đã được chọn
        String selectedValue = interest.getAttribute("value");
        System.out.println("Selected value from droplist: " + selectedValue);

        driver.quit();

    }
}
