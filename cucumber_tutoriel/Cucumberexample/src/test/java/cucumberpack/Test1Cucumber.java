package cucumberpack;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:Login/LoginTest.feature",glue = "cucumberpack",plugin= {"html:target/test-report","json:target/json-report.json"})                                      
public class Test1Cucumber {

}
