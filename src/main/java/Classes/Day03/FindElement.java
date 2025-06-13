package Classes.Day03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FindElement {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // mở website
        driver.get("https://saucelabs.com/request-demo");
// Find element
//  Tìm text tuyệt đối  thẻ <a>
//        WebElement element = driver.findElement(By.linkText("Try it free"));
//        element.click();

//  Tìm text tương đối
//        WebElement element = driver.findElement(By.partialLinkText("Try "));
//        element.click();

//        WebElement element = driver.findElement(By.xpath("//input[@id='Email']"));
//        element.sendKeys("meo@gmail.com");

//  Textbox element
        WebElement css = driver.findElement(By.cssSelector("#Email"));
        css.sendKeys("meo@gmail.com");


        Thread.sleep(3000);

        css.clear();
    }
}
