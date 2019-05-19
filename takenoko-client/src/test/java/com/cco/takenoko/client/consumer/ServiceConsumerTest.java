package com.cco.takenoko.client.consumer;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;

/**
 * The {@code SubscriptionServiceConsumerTest} class allows JUnit to run the Cucumber tests
 * of subscription/inscription service.
 * 
 * @author cmarilier
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = "pretty")
public class ServiceConsumerTest {}
