package com.saucedemo.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class loginTests {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@Test(priority = 1)
	public void testLoginElementsPresence() {

		// Check presence of User name field
		WebElement usernameField = driver.findElement(By.id("user-name"));
		Assert.assertTrue(usernameField.isDisplayed(), "Username field is not displayed");

		// Check presence of password field
		WebElement passwordField = driver.findElement(By.id("password"));
		Assert.assertTrue(passwordField.isDisplayed(), "Password field is not displayed");

		// Check presence of login button
		WebElement loginButton = driver.findElement(By.id("login-button"));
		Assert.assertTrue(loginButton.isDisplayed(), "Login button is not displayed");
	}

	@Test(priority = 2)
	public void testValidLogin() {
		// Enter valid credentials
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Check if the login was successful (expected: A div containing 'Swag Labs')
		WebElement successMessage = driver.findElement(By.xpath("//div[contains(text(), 'Swag Labs')]"));
		Assert.assertTrue(successMessage.isDisplayed(), "'Swag Labs' is not displayed after a successful login");
	}

	@Test(priority = 3)
	public void testInvalidLogin() {
		// Enter wrong credentials [username: "abc" |Password: "123"]

		driver.findElement(By.id("user-name")).sendKeys("abc");
		driver.findElement(By.id("password")).sendKeys("123");
		driver.findElement(By.id("login-button")).click();

		// Check if the error message is displayed
		WebElement errorMessage = driver.findElement(By.cssSelector("div.error-message-container.error"));
		Assert.assertTrue(errorMessage.isDisplayed());
		/*
		 * // check the error message's actual content against the expected content //
		 * String expectedMessage =
		 * "Epic sadface: Username and password do not match any user in this service";
		 * // String actualMessage = errorMessage.getText(); //
		 * Assert.assertEquals(actualMessage, expectedMessage, //
		 * "Invalid username/password Error message's content is not as expected!");
		 */
		WebElement Message = driver.findElement(By.xpath(
				"//h3[contains(text(), 'Epic sadface: Username and password do not match any user in this service')]"));
		Assert.assertTrue(Message.isDisplayed(), "error message is not displayed after an invalid login");
	}

	@Test(priority = 4)
	public void testEmptyUsername() {
		// Leave username empty
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Check if the empty username error message is displayed
		WebElement errorMessage = driver.findElement(By.cssSelector("div.error-message-container.error"));
		Assert.assertTrue(errorMessage.isDisplayed());
		/*
		 * // check the empty username error message's actual content against the
		 * expected // content // String expectedMessage =
		 * "Epic sadface: Username is required"; // String actualMessage =
		 * errorMessage.getText(); // Assert.assertEquals(actualMessage,
		 * expectedMessage, //
		 * "Empty Username Error message's content is not as expected!");
		 */
		WebElement Message = driver
				.findElement(By.xpath("//h3[contains(text(), 'Epic sadface: Username is required')]"));
		Assert.assertTrue(Message.isDisplayed(), "empty username error message is not displayed");
	}

	@Test(priority = 5)
	public void testEmptyPassword() {
		// Leave password empty
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("login-button")).click();

		// Check if the empty password error message is displayed
		WebElement errorMessage = driver.findElement(By.cssSelector("div.error-message-container.error"));
		Assert.assertTrue(errorMessage.isDisplayed());
		/*
		 * // check the empty password error message's actual content against the
		 * expected content String expectedMessage =
		 * "Epic sadface: Password is required"; String actualMessage =
		 * errorMessage.getText(); Assert.assertEquals(actualMessage, expectedMessage,
		 * "Empty Password Error message's content is not as expected!");
		 */
		WebElement Message = driver
				.findElement(By.xpath("//h3[contains(text(), 'Epic sadface: Password is required')]"));
		Assert.assertTrue(Message.isDisplayed(), "empty password error message is not displayed");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
