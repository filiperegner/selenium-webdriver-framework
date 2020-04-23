package com.herokuapp.theinternet.dropdowntests;

import org.testng.annotations.Test;

import com.herokuapp.theinternet.base.TestUtilities;
import com.herokuapp.theinternet.pages.DropdownPage;
import com.herokuapp.theinternet.pages.WelcomePage;

public class DropdownTest extends TestUtilities{

	@Test
	public void optionTwoTest() {
		log.info("Start optionTwoTest");
		
		// open main page
		WelcomePage welcomePage = new WelcomePage(driver, log);
		welcomePage.openPage();
		
		// click on dropdown link
		DropdownPage dropdownPage = welcomePage.clickDropdownLink();
		
		// select option 2
		dropdownPage.selectOption(2);
		
		// verify option 2 is selected
	}

}
