package cucumberpack;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefinition {
	
    @Given("^I open login page$")
	public void i_open_login_page() throws Throwable {
	    System.out.println("Opening the login page");
	}

	@When("^I enter username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
	public void i_enter_username_as_and_password_as(String username, String password) throws Throwable {
	    System.out.println("Typing the username and the password");
	}

	@When("^I submit login page$")
	public void i_submit_login_page() throws Throwable {
	    System.out.println("I click on submit Button");
	}

	@Then("^I redirect to user home page$")
	public void i_redirect_to_user_home_page() throws Throwable {
	    System.out.println("then i redirect");
	} 
	
	@Then("^I am on login page$")
	public void i_am_on_login_page() throws Throwable {
		System.out.println("im on login page");
	}   
	

}
