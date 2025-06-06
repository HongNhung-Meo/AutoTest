package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DoubleClickExampleTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");

        WebElement button = driver.findElement(By.xpath("//button[@ondblclick='myFunction()']"));

        // tạo đối Actions tượng để thực hiện double-Click
        Actions actions = new Actions(driver);
        actions.doubleClick(button).perform();
        Thread.sleep(3000);

        // xử lý cảnh báo xuất hiện sau double-Click
        String alertText = driver.switchTo().alert().getText();
        System.out.println("Alert text after double-Click: " + alertText);

        // đóng alert
        driver.switchTo().alert().dismiss();
        Thread.sleep(3000);


        driver.quit();
    }
}
