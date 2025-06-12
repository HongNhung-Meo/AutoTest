package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExplicitwaitExampleTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {

            driver.get("https://saucelabs.com/request-demo");

            // Chờ tối đa 10 giây cho đến khi điều kiện cụ thể thỏa mãn (ví dụ: phần tử có thể nhìn thấy, click được, tồn tại...).
            //Rất linh hoạt và chỉ áp dụng tại chỗ phần tử được gọi.
            // Tạo
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // chờ và tìm phần tử email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email1")));
            emailField.sendKeys("meo@gmail.com");

            // chờ và tìm nút submit
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("mktoButton")));
            submitButton.click();

        } catch (TimeoutException e) {
            System.out.println("Lỗi khi tìm phần tử thao tác: " + e.getMessage());

        } finally {
            driver.quit();
        }


    }
}
