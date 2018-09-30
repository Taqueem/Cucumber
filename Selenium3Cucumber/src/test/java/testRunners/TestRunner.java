package testRunners;

import java.util.List;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features= {"src/test/resources/featureFiles"},glue= {"stepDefinitions"})

public class TestRunner {

}
