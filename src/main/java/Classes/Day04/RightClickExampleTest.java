package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class RightClickExampleTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");

        WebElement span = driver.findElement(By.xpath("//span[text()='right click me']"));

        Actions actions = new Actions(driver);
        actions.contextClick(span).perform();

        //lấy danh sách các tuỳ chọn trong menu chuột phải
        WebElement option = driver.findElement(By.xpath("//span[text()='Copy']"));

        option.click();
        Thread.sleep(3000);

        // xử lý cảnh báo xuất hiện sau right-Click
        String alertText = driver.switchTo().alert().getText();
        System.out.println("Alert text after right-Click: " + alertText);

        // đóng alert
        driver.switchTo().alert().accept();
        Thread.sleep(3000);


        driver.quit();
    }
}
