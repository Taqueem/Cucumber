package stepDefinitions;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import easy.util.BaseUtils;

public class FirstDefine extends BaseUtils {
	

	@Given("^User is on Home Page$")
	public void user_is_on_HP() throws Throwable{
		System.out.println("asklaksla");
		setBrowser();
	}
	
	@When("^User Navigate to LogIn Page$")
	public void navigation() throws Throwable{
		System.out.println("ajksjak");
	}
	
	@Given("^User clicks on the button$")
	public void user_clicks_on_the_button() throws Throwable {
		System.out.println("Chutiya");
	    // Write code here that turns the phrase above into concrete actions

	}

	@When("^user clicks on submit$")
	public void user_clicks_on_submit() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Chutiya2");

	}
}
