package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeTest(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) {
		// create driver

		switch (browser) {
		case "chrome":
			System.setProperty("webDriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webDriver.gecko.driver", "src/main/resources/geckodriver");
			driver = new FirefoxDriver();
		default:
			System.out.println("Do not know to start " + browser + ", starting chrome instead");
			System.setProperty("webDriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;
		}

		// sleep 3 seconds just to see the steps on test run session.
		sleep(3000);

		// maximize browser window
		driver.manage().window().maximize();
		
		//implicit wait
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {

		// open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page " + url + " is open");

		// user name field
		WebElement userName = driver.findElement(By.id("username"));
		userName.sendKeys("tomsmith");

		// password field
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		// login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();

		// sleep 3 seconds just to see the steps on test run session.
		sleep(3000);

		String expectedUrl = "http://the-internet.herokuapp.com/secure";

		String actualUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUrl, expectedUrl, "Actual page is not same as expected.");

		WebElement successMessage = driver.findElement(By.xpath("//div[@id='flash']"));

		String expectedMessage = "You logged into a secure area!";

		String actualMessage = successMessage.getText();

		// Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not
		// same as expected");

		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message.\nActual message: " + actualMessage
						+ "\nExpected Message: " + expectedMessage);

		WebElement logoutButton = driver.findElement(By.xpath(("//a[@class='button secondary radius']")));
		Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not visible");

	}

	@Parameters({ "username", "password", "expectedMessage" })

	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage) {

		// open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page " + url + " is open");

		// incorrect user name field
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);

		// password field
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(password);

		// login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();

		// sleep 3 seconds just to see the steps on test run session.
		sleep(3000);

		WebElement errorMessage = driver.findElement(By.id("flash"));

		String actualErrorMessage = errorMessage.getText();

		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual error message does not contain expected message.\nActual message: " + actualErrorMessage
						+ "\nExpected Message: " + expectedErrorMessage);
	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// close browser
		driver.quit();
	}
}
