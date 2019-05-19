package com.cco.takenoko.client.consumer;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.springframework.http.HttpStatus;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/**
 * The {@code ConnectionServiceConsumerStepDefinitions} class defines the Gherkin steps (in the scenarios
 * of the Cucumber <i>feature</i> "Is a client Connected?") as Java methods.
 * 
 * @author oualid benazzouz
 */
public class ConnectionServiceConsumerStepDefinitions extends IntegrationTest {
	

	@When("the client {int} makes a request of connection to the server")
	public void the_client_makes_a_request_of_connection_to_the_server(Integer id) {
	    
		consumeConnectionService(id);
	}
	
	@Then("the client receives a status code of {int}")
	public void the_client_receives_a_status_code_of(Integer statusCode) throws Throwable {

		HttpStatus currentStatusCode = responseEntityConnection.getStatusCode();
		assertThat("Status code is incorrect: " + responseEntityConnection.getBody(), currentStatusCode.value(), equalTo(statusCode));
		
	}
	
	
	

}
