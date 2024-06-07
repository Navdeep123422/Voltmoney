package voltmoney.voltmoney;



import org.testng.annotations.Test;
import org.testng.Assert;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class LoginTest {
	
	    WebDriver driver;
	    WebDriverWait wait;

	    @BeforeClass
	    public void setUp() {
	        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
	        driver = new ChromeDriver();
	        driver.get("https://www.saucedemo.com/");
	        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    }

	    @Test
	    public void testValidLogin() {
	        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
	        usernameField.sendKeys("standard_user");

	        WebElement passwordField = driver.findElement(By.id("password"));
	        passwordField.sendKeys("secret_sauce");

	        WebElement loginButton = driver.findElement(By.id("login-button"));
	        loginButton.click();

	        wait.until(ExpectedConditions.urlContains("inventory.html"));
	        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed for valid credentials");
	    }

	    @Test
	    public void testInvalidLogin() {
	        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
	        usernameField.sendKeys("standard_user");

	        WebElement passwordField = driver.findElement(By.id("password"));
	        passwordField.sendKeys("wrong_password");

	        WebElement loginButton = driver.findElement(By.id("login-button"));
	        loginButton.click();

	        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
	        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"), "Error message not displayed for invalid credentials");
	    }

	    @AfterMethod
	    public void clearFields() {
	        try {
	            driver.navigate().refresh();
	            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
	            usernameField.clear();

	            WebElement passwordField = driver.findElement(By.id("password"));
	            passwordField.clear();
	        } catch (Exception e) {
	            System.err.println("Exception during clearFields: " + e.getMessage());
	            // Ensure browser is in a known state
	            driver.get("https://www.saucedemo.com/");
	        }
	    }

	    @AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	}