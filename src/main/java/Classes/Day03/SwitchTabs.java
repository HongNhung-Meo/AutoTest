package Classes.Day03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class SwitchTabs {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // mở website đầu tiên
        driver.get("https://saucelabs.com/request-demo");
        System.out.println("Tab 1 Title: " + driver.getTitle());

        // lưu ID của tab gốc
        String originalTab = driver.getWindowHandle();

        // mở tab mới (Google)
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.open('https://www.google.com/', '_blank');");

        // tự động tương tác: executeScript

        // lấy tất cả các ID tab
        Set<String> allTabs = driver.getWindowHandles();

        // chuyển sang tab mới
        for (String tab : allTabs) {
            if (!tab.equals(originalTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        // thao tác trên tab mới (Google)
        System.out.println("Tab 2 Title: " + driver.getTitle());
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Sauce Labs Request Demo");
        searchBox.submit();
        // đợi trang tải
        Thread.sleep(3000);

        // quay lại trang ban đầu
        driver.switchTo().window(originalTab);
        System.out.println("Back to Tab 1 Title: " + driver.getTitle());

        // Tương tác
        System.out.println("Tab 1 URL: " + driver.getCurrentUrl());

        // Đóng tất cả
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
            driver.close();
        }

    }
}
