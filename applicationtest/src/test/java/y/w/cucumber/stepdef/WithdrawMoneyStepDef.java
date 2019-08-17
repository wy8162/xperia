package y.w.cucumber.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WithdrawMoneyStepDef {
    @Given("I have {int} in my account")
    public void i_have_in_my_account(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Result " + int1);
    }

    @When("I choose to withdraw the fixed amount of {int}")
    public void i_choose_to_withdraw_the_fixed_amount_of(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Result " + int1);
    }

    @Then("^I should receive (\\d+) and the balance of my account should be (\\d+) and the (Receive \\d+ in cash)$")
    public void i_should_receive_and_the_balance_of_my_account_should_be_and_the(Integer int1, Integer int2, String s) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Result " + int1 + " " + int2 + " " + s);
    }
}
