package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

	@Test
	public void loginTest() {

		// create driver
		System.setProperty("webDriver.chrome.driver", "src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();

		// sleep 3 seconds
		sleep(3000);

		//maximize browser window
		driver.manage().window().maximize();

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

		// login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();

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

		logoutButton.click();

		// maximize browser window

		driver.close();
	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
