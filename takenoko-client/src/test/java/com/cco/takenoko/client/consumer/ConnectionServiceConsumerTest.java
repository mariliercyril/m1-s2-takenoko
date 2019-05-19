package com.cco.takenoko.client.consumer;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


/**
 * The {@code ConnectionServiceConsumerTest} class allows JUnit to run the Cucumber tests
 * of the Connection service.
 * 
 * @author oualid benazzouz
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = "pretty")
public class ConnectionServiceConsumerTest {

}
