package y.w.cucumber.stepdef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import y.w.model.Person;
import y.w.model.Sales;
import y.w.model.Student;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CustomMapperStepdefs
 */
@Slf4j
public class CustomMapperStepdefs implements En
{
    ObjectMapper objectMapper = new ObjectMapper();

    List<List<String>> expected = Lists.newArrayList(
            Lists.newArrayList("1", "2", "3"),
            Lists.newArrayList("2", "2", "2"),
            Lists.newArrayList("3", "3", "3")
    );

    private List<List<String>> m;
    private Sales     s;
    private Sales     s1;

    public CustomMapperStepdefs()
    {
        Given("matrix", (DataTable dataTable) -> {
            m = dataTable.asLists();
        });
        When("I parse the data", () -> {
        });
        Then("I'll get a matrix", () -> {
            assertThat(m).containsAnyElementsOf(expected);
        });

        // Either of the follow ways works
        // Given("sales data {}", (String json) -> {
        //     s = objectMapper.readValue(new StringReader(json), Sales.class);
        //     log.info(objectMapper.writeValueAsString(s));
        // });
        Given("sales data {}", (Sales sales) -> {
            s = sales; //objectMapper.readValue(new StringReader(json), Sales.class);
            log.info(objectMapper.writeValueAsString(s));
        });
        When("the data is read", () -> {
        });
        Then("I'll get a sakes object", () -> {
            log.info(objectMapper.writeValueAsString(s));
        });

        Given("map a student directly {}", (Student stu) -> {
            log.info("This is student " + stu.getName() + " who has course " + stu.getCourses().get(0).getCourseName());
        });

        Given("I have a list of sales items below", (DataTable dataTable) -> {
            List<Sales> sales = dataTable.asList(Sales.class);
            for (Sales v : sales)
                log.info(objectMapper.writeValueAsString(v));
        });
    }

    /**
     * List<Type> can't be used in lambda. So has use old style.
     *
     * @param sales
     * @throws JsonProcessingException
     */
    @Given("list of sales objects in a table as below")
    public void listOfSalesObjectsInATableAsBelow(List<Sales> sales) throws JsonProcessingException
    {
        for (Sales s : sales)
            log.info(objectMapper.writeValueAsString(s));
    }

    @Given("map single student from string {}")
    public void map_single_student_from_string(String str) throws IOException
    {
        Student stu = objectMapper.readValue(new StringReader(str), Student.class);
        log.info("This is student " + stu.getName() + " who has course " + stu.getCourses().get(0).getCourseName());
    }


    @Given("persons as table entries \\(header as key and data in rows) of a table below")
    public void persons_as_table_entries_header_as_key_and_data_in_rows_of_a_table_below(List<Person> lp) throws IOException
    {
        for (Person p : lp)
            log.info("This is the person: " + p.toString());
    }

}
