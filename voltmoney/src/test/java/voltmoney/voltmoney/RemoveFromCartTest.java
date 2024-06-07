package voltmoney.voltmoney;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class RemoveFromCartTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testRemoveFromCart() {
        try {
            // Login
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            usernameField.sendKeys("standard_user");

            WebElement passwordField = driver.findElement(By.id("password"));
            passwordField.sendKeys("secret_sauce");

            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();

            // Add item to cart
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")));
            addToCartButton.click();

            // Go to cart
            WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_badge")));
            cartIcon.click();

            // Remove item from cart
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='remove-sauce-labs-backpack']")));
            removeButton.click();

         // Verify item is removed from cart
            Boolean removedItem = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='cart_item']")));
            Assert.assertTrue(removedItem, "Item is not removed from cart");
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            Assert.fail("Failed to remove item from cart: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
