package com.siemens.openemr.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.siemens.openemr.stepDefs",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"
    },
    monochrome = true,
    tags = "@Regression"
    		
)
public class TestRunner extends AbstractTestNGCucumberTests {
}