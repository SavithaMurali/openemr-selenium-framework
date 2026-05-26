package com.siemens.openemr.utils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
	
	private static final String BASE_URL = "https://demo.openemr.io/openemr";
	
	@BeforeClass
	public void setUp() {
	    WebDriverFactory.initDriver("chrome");
	    WebDriverFactory.getDriver().get(BASE_URL);
	}
	
	@AfterClass
	public void tearDown() {
	    WebDriverFactory.quitDriver();
	}
	
}
