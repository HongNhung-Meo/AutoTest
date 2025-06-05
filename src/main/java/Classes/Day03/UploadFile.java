package Classes.Day03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class UploadFile {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/upload/");

        // đường dẫn tệp cần tải lên
        String filePath = "buoi1.docx";
        File file = new File(filePath);
        String absPath = file.getAbsolutePath();

        // tìm phần tử input type="file" và gửi đường dẫn tệp tuyệt đối
        WebElement uploadFile = driver.findElement(By.id("uploadfile_0"));
        uploadFile.sendKeys(absPath);

        // đồng ý với các điều khoản
        WebElement termsCheckbox = driver.findElement(By.id("terms"));
        termsCheckbox.click();

        // nhấn nút upload
        WebElement uploadButton = driver.findElement(By.id("submitbutton"));
        uploadButton.click();

        // đợi ktra kết quả
        Thread.sleep(3000);

        // lấy và in dòng kết quả
        WebElement result = driver.findElement(By.id("res"));
        System.out.println("Thông báo: " + result.getText());

        //đóng
        driver.quit();
    }
}
