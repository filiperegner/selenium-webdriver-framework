package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.herokuapp.theinternet.base.TestUtilities;

public class ExceptionsTests extends TestUtilities {

	@Test(priority = 1)
	public void notVisibleTest() {
		// open page http://the-internet.herokuapp.com/dynamic_loading/1

		// String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/1");
		log.info("Page" + "http://the-internet.herokuapp.com/dynamic_loading/1" + "is open");

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
		log.info("Page" + "http://the-internet.herokuapp.com/dynamic_loading/1" + "is open");

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
		log.info("Page " + "http://the-internet.herokuapp.com/dynamic_loading/1" + " is open");

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

		// navigate to page
		driver.get("http://the-internet.herokuapp.com/dynamic_controls");

		// create two WebElements: button enable and textField
		WebElement enableButton = driver.findElement(By.xpath("//button[contains(text(),'Enable')]"));
		WebElement textField = driver.findElement(By.xpath("(//input)[2]"));

		enableButton.click();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(textField));

		textField.sendKeys("Text Field Test");
		Assert.assertEquals(textField.getAttribute("value"), "Text Field Test");
	}

}
