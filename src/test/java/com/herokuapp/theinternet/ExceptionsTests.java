package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

public class ExceptionsTests {

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

		// implicit wait
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(priority = 1)
	public void notVisibleTest() {
		// open page http://the-internet.herokuapp.com/dynamic_loading/1

		// String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/1");
		System.out.println("Page" + "http://the-internet.herokuapp.com/dynamic_loading/1" + "is open");

		// find locator for startButton and click on it

		// WebElement startButton = driver.findElement(By.id("start"));
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
		startButton.click();

		// get finish element text

		WebElement finishElement = driver.findElement(By.id("finish"));

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.visibilityOf(finishElement));

		String finishText = finishElement.getText();

		String expectedFinishText = "Hello World!";

		// compare actual finish element text with expected "Hello World!" using test NG
		Assert.assertTrue(finishText.contains(expectedFinishText), "Finish text: " + finishText);

		// Assert class

	}

	@Test(priority = 2)
	public void timeoutTest() {
		// open page http://the-internet.herokuapp.com/dynamic_loading/1

		// String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/1");
		System.out.println("Page" + "http://the-internet.herokuapp.com/dynamic_loading/1" + "is open");

		// find locator for startButton and click on it

		// WebElement startButton = driver.findElement(By.id("start"));
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
		startButton.click();

		// get finish element text

		WebElement finishElement = driver.findElement(By.id("finish"));

		WebDriverWait wait = new WebDriverWait(driver, 2);

		try {
			wait.until(ExpectedConditions.visibilityOf(finishElement));
		} catch (TimeoutException exception) {
			System.out.println("Exceptions catched: " + exception.getMessage());
			sleep(3000);
		}

		String finishText = finishElement.getText();

		String expectedFinishText = "Hello World!";

		// compare actual finish element text with expected "Hello World!" using test NG
		// Assert class
		Assert.assertTrue(finishText.contains(expectedFinishText), "Finish text: " + finishText);

	}

	@Test(priority = 3)
	public void noSuchElementTest() {
		// open page http://the-internet.herokuapp.com/dynamic_loading/2

		// String url = "http://the-internet.herokuapp.com/dynamic_loading/2";
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		System.out.println("Page " + "http://the-internet.herokuapp.com/dynamic_loading/1" + " is open");

		// find locator for startButton and click on it

		// WebElement startButton = driver.findElement(By.id("start"));
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
		startButton.click();

		WebDriverWait wait = new WebDriverWait(driver, 10);

		Assert.assertTrue(
				wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("finish"), "Hello World!")),
				"Could not verify expected text 'Hello World!' ");

//		WebElement finishElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
//
//		String finishText = finishElement.getText();
//
//		String expectedFinishText = "Hello World!";
//
//		// compare actual finish element text with expected "Hello World!" using test NG
//		// Assert class
//		Assert.assertTrue(finishText.contains(expectedFinishText), "Finish text: " + finishText);

	}

	@Test
	public void staleElementTest() {

		driver.get("http://the-internet.herokuapp.com/dynamic_controls");

		WebElement checkbox = driver.findElement(By.id("checkbox"));
		WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));

		WebDriverWait wait = new WebDriverWait(driver, 10);

		removeButton.click();

//		wait.until(ExpectedConditions.invisibilityOf(checkbox));
//		
//		Assert.assertFalse(checkbox.isDisplayed());

//		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkbox)),
//				"Checkbox is still visible, but shouldn´t be");

		Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkbox)),
				"Checkbox is still visible, but shouldn´t be");
		
		WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
		addButton.click();
		
		checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
		Assert.assertTrue(checkbox.isDisplayed(), "Checkbox is still visible, but should be");
		
	}
	
	@Test
	public void disableElementTest() {
		
		//navigate to page
		driver.get("http://the-internet.herokuapp.com/dynamic_controls");
		
		//create two WebElements: button enable and textField
		WebElement enableButton = driver.findElement(By.xpath("//button[contains(text(),'Enable')]"));
		WebElement textField = driver.findElement(By.xpath("(//input)[2]"));
		
		enableButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(textField));

		textField.sendKeys("Text Field Test");
		Assert.assertEquals(textField.getAttribute("value"), "Text Field Test");
	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// close browser
		driver.quit();
	}
}
