package feature.Day06;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginMultiCaseTest {
    WebDriver driver;

    @BeforeClass // chỉ mở trang web duy nhất 1 lần
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginWithValidCredentials() {
        driver.get("https://www.saucedemo.com/");
        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin đúng
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Xác nhận vào trang inventory
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        // Kiểm tra logo & sản phẩm
        WebElement appLogo = driver.findElement(By.className("app_logo"));
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No products displayed!");
    }

    @Test
    public void testLoginWithInvalidUsername() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not shown!");
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"),
                "Unexpected error message: " + errorMessage.getText());
    }

    @Test
    public void testLoginWithInvalidPassword() {
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not shown!");
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"),
                "Unexpected error message: " + errorMessage.getText());

    }

    @Test
    public void testLoginWithEmptyUsername() {
        driver.get("https://www.saucedemo.com/");

        // Để trống username, chỉ nhập password
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Kiểm tra thông báo lỗi
        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not shown!");
        Assert.assertEquals(errorMessage.getText(), "Epic sadface: Username is required");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
