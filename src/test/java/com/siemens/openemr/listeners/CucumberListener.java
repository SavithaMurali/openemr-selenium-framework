package com.siemens.openemr.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.siemens.openemr.utils.ExtentReportManager;
import com.siemens.openemr.utils.WebDriverFactory;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberListener implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        ExtentTest extentTest = ExtentReportManager.getInstance()
            .createTest(scenarioName);
        ExtentReportManager.setTest(extentTest);
    }

    private void onTestStepFinished(TestStepFinished event) {
        ExtentTest extentTest = ExtentReportManager.getTest();
        if (extentTest == null) return;

        io.cucumber.plugin.event.Status cucumberStatus = event.getResult().getStatus();

        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getText();

            if (cucumberStatus == io.cucumber.plugin.event.Status.FAILED) {
                extentTest.log(Status.FAIL, stepText);
                takeScreenshot(extentTest);
            } else if (cucumberStatus == io.cucumber.plugin.event.Status.PASSED) {
                extentTest.log(Status.PASS, stepText);
            } else {
                extentTest.log(Status.SKIP, stepText);
            }
        }
    }

    private void onTestCaseFinished(TestCaseFinished event) {
        ExtentTest extentTest = ExtentReportManager.getTest();
        if (extentTest == null) return;

        io.cucumber.plugin.event.Status cucumberStatus = event.getResult().getStatus();

        if (cucumberStatus == io.cucumber.plugin.event.Status.FAILED) {
            takeScreenshot(extentTest);
            extentTest.log(Status.FAIL, "Scenario FAILED");
        } else if (cucumberStatus == io.cucumber.plugin.event.Status.PASSED) {
            extentTest.log(Status.PASS, "Scenario PASSED");
        } else {
            extentTest.log(Status.SKIP, "Scenario SKIPPED");
        }

        WebDriverFactory.quitDriver();
    }

    private void onTestRunFinished(TestRunFinished event) {
        ExtentReportManager.flushReport();
    }
    private void takeScreenshot(ExtentTest extentTest) {
        try {
            if (WebDriverFactory.getDriver() == null) return;

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            
            String screenshotDir = System.getProperty("user.dir")
                + "/test-output/screenshots/";
            String screenshotPath = screenshotDir + "screenshot_" + timestamp + ".png";
            
            // Relative path from reports/ to screenshots/
            String relativePath = "../screenshots/screenshot_" + timestamp + ".png";

            new File(screenshotDir).mkdirs();
            File srcFile = ((TakesScreenshot) WebDriverFactory.getDriver())
                .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(screenshotPath));

            extentTest.fail("Screenshot on failure",
                MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());

        } catch (IOException e) {
            extentTest.log(Status.WARNING,
                "Could not capture screenshot: " + e.getMessage());
        }
    }
}