package org.example.challenge.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/resources/bdd/wheretheissapi.feature",
        glue = {"org.example.challenge.stepDefinition"},
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
        monochrome =true)
public class TestRunner {
}