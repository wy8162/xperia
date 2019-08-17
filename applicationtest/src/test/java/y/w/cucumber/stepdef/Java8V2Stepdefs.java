package y.w.cucumber.stepdef;

import cucumber.api.java8.En;

public class Java8V2Stepdefs implements En {
    public Java8V2Stepdefs() {
        Given("if today is {word}", (String s) -> {
            System.out.println(s);
        });
        When("probably I ask whether it's Friday yet", () -> {
        });
        Then("I might be told {word}", (String s) -> {
            System.out.println(s);
        });
    }
}
