package com.siemens.openemr.stepDefs;

import com.siemens.openemr.pages.LoginPage;
import com.siemens.openemr.utils.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class LoginStepDefs {

    private LoginPage loginPage;
    private static final String BASE_URL = "https://demo.openemr.io/openemr";

    @Before
    public void setUp() {
        WebDriverFactory.initDriver("chrome");
        loginPage = new LoginPage(WebDriverFactory.getDriver());
    }

    @After
    public void tearDown() {
        WebDriverFactory.quitDriver();
    }

    @Given("I am on the OpenEMR login page")
    public void i_am_on_the_open_emr_login_page() {
        WebDriverFactory.getDriver().get(BASE_URL);
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        Assert.assertTrue(
            WebDriverFactory.getDriver().getCurrentUrl().contains("main.php"),
            "Dashboard not loaded after login");
    }

    @Then("I should see the error message {string}")
    public void i_should_see_the_error_message(String expectedMessage) {
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage,
            "Error message mismatch");
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        Assert.assertTrue(
            WebDriverFactory.getDriver().getCurrentUrl().contains("login"),
            "Should stay on login page");
    }

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as_with_password(String username, String password) {
        WebDriverFactory.getDriver().get(BASE_URL);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @When("I navigate to the logout page")
    public void i_navigate_to_the_logout_page() {
        WebDriverFactory.getDriver().get(BASE_URL + "/interface/logout.php");
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        Assert.assertTrue(
            WebDriverFactory.getDriver().getCurrentUrl().contains("login"),
            "Not redirected to login page after logout");
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.enterPassword(password);
    }

    @Then("the password field should be masked")
    public void the_password_field_should_be_masked() {
        Assert.assertTrue(loginPage.isPasswordMasked(),
            "Password field is not masked");
    }

    @When("I click the eye icon")
    public void i_click_the_eye_icon() {
        loginPage.clickEyeIcon();
    }

    @Then("the password should be visible")
    public void the_password_should_be_visible() {
        Assert.assertTrue(loginPage.isPasswordVisible(),
            "Password is not visible after clicking eye icon");
    }

    @When("I click the eye icon again")
    public void i_click_the_eye_icon_again() {
        loginPage.clickEyeIcon();
    }
}