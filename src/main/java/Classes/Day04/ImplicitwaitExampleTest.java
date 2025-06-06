package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ImplicitwaitExampleTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Selenium sẽ đợi tối đa thời gian được chỉ định nếu chưa thấy phần tử.
        // Nếu thấy sớm hơn thì Selenium sẽ tiếp tục chạy không đợi đủ thời gian
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
        driver.get("https://saucelabs.com/request-demo");

        WebElement email = driver.findElement(By.id("Email1"));
        email.sendKeys("meo@gmail.com");

        WebElement button = driver.findElement(By.className("mktoButton"));
        button.click();

        } catch (Exception e) {
            System.out.println("Lỗi khi tìm phần tử thao tác: " + e.getMessage());

        } finally {
            driver.quit();
        }
    }
}
