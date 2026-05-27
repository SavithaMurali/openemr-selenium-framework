package com.siemens.openemr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By usernameField = By.id("authUser");
    private By passwordField = By.id("clearPass");
    private By loginButton   = By.id("login-button");
    private By errorMessage  = By.cssSelector("p.text-danger.font-weight-bold");
    private By eyeIcon       = By.id("password-icon");

    // Actions
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }

    public boolean isPasswordMasked() {
        WebElement passField = driver.findElement(passwordField);
        return passField.getAttribute("type").equals("password");
    }

    public void clickEyeIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(eyeIcon));
        driver.findElement(eyeIcon).click();
    }

    public boolean isPasswordVisible() {
        WebElement passField = driver.findElement(passwordField);
        return passField.getAttribute("type").equals("text");
    }
}