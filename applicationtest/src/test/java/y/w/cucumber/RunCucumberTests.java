package y.w.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = { "classpath:features"},
        glue={"classpath:y.w.cucumber.stepdef", // Cucumber to find steps
              "classpath:y.w.cucumber.typeregistry"}) // Cucumber to find TypeRegistry
public class RunCucumberTests extends CucumberSpringBase
{
}