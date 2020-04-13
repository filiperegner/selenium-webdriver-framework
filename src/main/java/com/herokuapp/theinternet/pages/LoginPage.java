package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject {

	private By userNameLocator = By.id("username");
	private By passwordLocator = By.name("password");
	private By loginButtonLocator = By.tagName("button");
	private By errorMessageLocator = By.id("flash");


	public LoginPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	public SecureAreaPage login(String username, String password) {
		log.info("Executing Login with username [" + username + "] and password [" + password + "]");
		type(username, userNameLocator);
		type(password, passwordLocator);
		click(loginButtonLocator);
		return new SecureAreaPage(driver, log);

	}

	public void negativeLogIn(String username, String password) {
		log.info("Executing Negative LogIn with username [" + username + "] and password [" + password + "]");
		type(username, userNameLocator);
		type(password, passwordLocator);
		click(loginButtonLocator);
	}

	/** Wait for error message to be visible on the page */
	public void waitForErrorMessage() {
		waitForVisibilityOf(errorMessageLocator, 5);
	}

	public String getErrorMessageText() {
		return find(errorMessageLocator).getText();
	}
}
