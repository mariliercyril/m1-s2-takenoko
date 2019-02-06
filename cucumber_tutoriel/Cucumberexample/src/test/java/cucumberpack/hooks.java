package cucumberpack;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class hooks {
	
	@Before
	public void initalisation() {
		System.out.println("before hook");
	}
	
	
	@After
	public void fin() {
		System.out.println("After hook ");
	}

}
