package com.siemens.openemr.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

/*Opening/closing browser ✓
Giving driver to tests ✓
ThreadLocal for parallel runs ✓*/
	
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();	
	
	public static void initDriver(String browser) {
	    if (browser.equalsIgnoreCase("chrome")) {
	        WebDriverManager.chromedriver().setup();
	        driver.set(new ChromeDriver());
	    } else if (browser.equalsIgnoreCase("firefox")) {
	        WebDriverManager.firefoxdriver().setup();
	        driver.set(new FirefoxDriver());
	    } else if (browser.equalsIgnoreCase("edge")) {
	        WebDriverManager.edgedriver().setup();
	        driver.set(new EdgeDriver());
	    }
	    getDriver().manage().window().maximize();    
	}
	public static WebDriver getDriver() {
	    return driver.get();
	}
	
	public static void quitDriver() {
	    if (getDriver() != null) {
	        getDriver().quit();
	        driver.remove();
	    }
	}
}
