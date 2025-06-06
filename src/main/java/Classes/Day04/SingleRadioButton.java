package Classes.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SingleRadioButton {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/radio.html");

        WebElement radioButton = driver.findElement(By.id("vfb-7-2"));
        radioButton.click();
        Thread.sleep(3000);

        // lấy giá trị và trạng thái của button
        String value = radioButton.getAttribute("value");
        boolean isSelected = radioButton.isSelected();

        //in ra giá trị và trạng thái đã chọn
        System.out.println("Checkbox value selected: " + value);
        // in trạng thái
        System.out.println("Is selected? " + isSelected);

        driver.quit();


    }
}
