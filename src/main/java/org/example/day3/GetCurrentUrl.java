package org.example.day3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GetCurrentUrl {
    static WebDriver driver =null;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // mở website
        driver.get("https://saucelabs.com/");
        // điều hướng trang
        driver.navigate().to("https://www.google.com/");
        // quay lại trang trước
    //    driver.navigate().back();
        // tiến tới trang cũ, sau khi back
        driver.navigate().forward();
        driver.navigate().refresh();

        driver.close();
    }
}
