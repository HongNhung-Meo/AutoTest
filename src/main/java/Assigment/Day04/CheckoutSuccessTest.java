package Assigment.Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutSuccessTest {

    static WebDriver driver = null;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.saucedemo.com/");

// Step 1: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            System.out.println("Đăng nhập thành công");

// Step 2: Chọn tìm kiếm droplist Price (low to high)
            WebElement sortPrice = driver.findElement(By.className("product_sort_container"));
            Select selectSort = new Select(sortPrice);
            selectSort.selectByValue("lohi");
            System.out.println("Sắp xếp giá theo low to high ");

// Step 3: Add to cart 2 sản phẩm bất kì
            driver.findElement(By.xpath("//button[contains(@id,'onesie')]")).click();
            driver.findElement(By.xpath("//button[contains(@id,'bolt-t-shirt')]")).click();

// EXPECT: Badge giỏ hàng hiển thị số lượng là 2
            WebElement cartBadge = wait.until(ExpectedConditions.elementToBeClickable((By.className("shopping_cart_badge"))));
            if (cartBadge.getText().equals("2")) {
                System.out.println("Giỏ hàng hiển thị số 2");
            } else {
                System.out.println("Số sản phẩm hiển thị ở giỏ hàng không đúng" + cartBadge);
            }

// Step 4: Click vào giỏ hàng
            driver.findElement(By.className("shopping_cart_link")).click();

// EXPECT 1: hiển thị đúng 2 sản phẩm với tên và giá tiền đúng
            Items();

// EXPECT 2: Màn hình có hiển thị 2 button Remove
            List<WebElement> removeBtn = driver.findElements(By.xpath("//button[text()='Remove']"));
            if (removeBtn.size() == 2) {
                System.out.println("Có hiển thị đủ 2 nút Remove");
            } else {
                System.out.println("Không hiển thị đủ 2 nút Remove");
            }

// Step 5: Click Checkout và nhập các thông tin Firts name, Last name, Zip code
            wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
            driver.findElement(By.id("first-name")).sendKeys("Meo");
            driver.findElement(By.id("last-name")).sendKeys("Meo");
            driver.findElement(By.id("postal-code")).sendKeys("2511");

// Step 6: Click continute
            wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();

// EXPECT 1: Danh sách sản phẩm hiển thị lại đúng
            Items();

// EXPECT 2: Shipping info
            WebElement shippingInfo = driver.findElement(By.xpath("//div[contains(text(),'Free Pony')]"));
            if (shippingInfo.getText().equals("Free Pony Express Delivery!")) {
                System.out.println("Shipping: Free Pony Express Delivery!");
            } else {
                System.out.println("Shipping không đúng");
            }

// EXPECT 3 & 4: Kiểm tra tổng tiền (item total + tax = total)
            double itemTotal = Double.parseDouble(driver.findElement(By.className("summary_subtotal_label")).getText().replace("Item total: $", ""));
            double tax = Double.parseDouble(driver.findElement(By.className("summary_tax_label")).getText().replace("Tax: $", ""));
            double total = Double.parseDouble(driver.findElement(By.className("summary_total_label")).getText().replace("Total: $", ""));
            System.out.printf("Tổng sản phẩm: %.2f, Thuế: %.2f, Tổng cộng: %.2f\n", itemTotal, tax, total);

            if (Math.abs(itemTotal + tax - total) < 0.01) {
                System.out.println("Hiển thị đúng tổng tiền");
            } else {
                System.out.println("Hiển thị sai tổng tiền");

            }

// EXPECT 5: Button Finish hiển thị
            if (driver.findElement(By.id("finish")).isDisplayed()) {
                System.out.println("Nút Finish hiển thị");
            } else {
                System.out.println("Nút Finish không hiển thị");
            }

// Step 7: Click Finish
            wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();

// EXPECT 1-4: Trang hoàn tất hiển thị đúng
            boolean completeHeader = driver.getPageSource().contains("Checkout: Complete!");
            boolean thankYouMsg = driver.getPageSource().contains("Thank you for your order!");
            boolean dispatchMsg = driver.getPageSource().contains("Your order has been dispatched");
            boolean backButton = driver.findElement(By.id("back-to-products")).isDisplayed();

            if (completeHeader && thankYouMsg && dispatchMsg && backButton) {
                System.out.println("Trang hoàn tất hiển thị đủ thông tin");
            } else {
                System.out.println("Thiếu thông tin trên trang hoàn tất");
            }

        } catch(Exception e){
            System.out.println("Lỗi khi tìm phần tử thao tác: " + e.getMessage());

        } finally {
            driver.quit();
        }
    }
    public static void Items() {
        List<WebElement> overviewItems = driver.findElements(By.className("cart_item"));
        if (overviewItems.size() == 2) {
            System.out.println("Có 2 sản phẩm trong giỏ hàng");
            for (WebElement item : overviewItems) {
                String name = item.findElement(By.className("inventory_item_name")).getText();
                String price = item.findElement(By.className("inventory_item_price")).getText();
                System.out.println("Sản phẩm: " + name + ", giá: " + price);
            }
        } else {
            System.out.println("Không có đúng 2 sản phẩm trong giỏ" + overviewItems.size());
        }
    }
}

