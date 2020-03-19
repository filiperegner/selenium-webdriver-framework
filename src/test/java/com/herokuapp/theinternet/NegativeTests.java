package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTests {

	@Test
	public void incorrectUsernameTest() {

		// create driver
		System.setProperty("webDriver.chrome.driver", "src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();

		// sleep 3 seconds
		sleep(3000);

		// maximize browser window
		driver.manage().window().maximize();

		// open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page " + url + " is open");

		// incorrect user name field
		WebElement userName = driver.findElement(By.id("username"));
		userName.sendKeys("incorrect");

		// password field
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		// login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();

		sleep(3000);

		WebElement errorMessage = driver.findElement(By.id("flash"));

		String expectedErrorMessage = "Your username is invalid!";

		String actualErrorMessage = errorMessage.getText();

		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual error message does not contain expected message.\nActual message: " + actualErrorMessage
						+ "\nExpected Message: " + expectedErrorMessage);

		driver.close();
	}

	@Test
	public void incorrectPasswordTest() {

		// create driver
		System.setProperty("webDriver.chrome.driver", "src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();

		// sleep 3 seconds
		sleep(3000);

		// maximize browser window
		driver.manage().window().maximize();

		// open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page " + url + " is open");

		// incorrect user name field
		WebElement userName = driver.findElement(By.id("username"));
		userName.sendKeys("tomsmith");

		// password field
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("incorrect");

		// login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();

		sleep(3000);

		WebElement errorMessage = driver.findElement(By.id("flash"));

		String expectedErrorMessage = "Your password is invalid!";

		String actualErrorMessage = errorMessage.getText();

		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual message does not contain expected message.\nActual message: " + actualErrorMessage
						+ "\nExpected Message: " + expectedErrorMessage);

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
