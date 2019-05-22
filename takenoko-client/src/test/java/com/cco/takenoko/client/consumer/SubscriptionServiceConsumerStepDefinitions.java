package com.cco.takenoko.client.consumer;

import org.springframework.http.HttpStatus;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;

// Method "is(T value)" (is a shortcut to "is(equalTo(T value))")...
// (Crossed out because the method "is(java.lang.Class<T> type)" is deprecated.)
import static org.hamcrest.Matchers.is;

/**
 * The {@code SubscriptionServiceConsumerStepDefinitions} class defines the Gherkin steps (in the scenarios
 * of the Cucumber <i>feature</i> "Is a client inscribed?") as Java methods.
 * 
 * @author cmarilier
 */
public class SubscriptionServiceConsumerStepDefinitions extends IntegrationTest {

	@When("^the client (\\d+) makes a request of subscription$")
	public void the_client_makes_a_request_of_subscription(int id) throws Throwable {

		consumeSubscriptionService(id);
	}

	@Then("the client returns it status code of {int}")
	public void the_client_returns_it_status_code_of(Integer statusCode) throws Throwable {

		HttpStatus currentStatusCode = responseEntity.getStatusCode();
		assertThat("Status code is incorrect: " + responseEntity.getBody(), currentStatusCode.value(), is(statusCode));
	}

}
