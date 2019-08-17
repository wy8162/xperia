package y.w.cucumber.stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TypeReference;
import io.cucumber.java.en.Given;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * DatatableTestStepDefs
 *
 * https://github.com/cucumber/cucumber/blob/master/datatable/README.md
 *
 * @author ywang
 * @date 8/8/2019
 */
@Log4j
public class DatatableTestStepDefs
{
    @Given("fill up the new account form with the following data without header")
    public void fill_up_the_new_account_form_with_the_following_data_without_header(DataTable dt) {
        List<String> list = dt.asList(String.class);
        for (String s : list)
            log.info(s);
    }

    @Given("fill up the new account form with the following data with header")
    public void fill_up_the_new_account_form_with_the_following_data_with_header(DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        System.out.println(list.get(0).get("First Name"));
        System.out.println(list.get(0).get("Last Name"));
        System.out.println(list.get(0).get("Phone No"));
    }

    @Given("^I open Facebook URL and create new accounts with below data$")
    public void i_open_Facebook_URL_and_create_new_accounts_with_below_data(DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        for(int i=0; i<list.size(); i++) {
            System.out.println(list.get(i).get("First Name"));
            System.out.println(list.get(i).get("Last Name"));
        }
    }

    @Given("Case 1 - Input data as List<List<String>>")
    public void table_to_be_converted_to_List_List_String(DataTable dataTable) {
        List<List<String>> lists = dataTable.asLists(String.class);
        for (List<String> l : lists)
            System.out.println(StringUtils.join(l, ","));
    }

    @Given("Case 2 - Input data as List<Map<String, String>>")
    public void table_to_be_converted_to_a_single_Map_String_String(DataTable dataTable) {
        List<Map<String, String>> lists = dataTable.asList(Map.class);
        for (Map<String, String> m : lists)
            System.out.println(String.format("firstName = %s, lastName = %s, birthDate = %s",
                                             m.get("firstName"), m.get("lastName"), m.get("birthDate")));
    }

    @Given("Case 3 - Table to Map<String, String>")
    public void table_to_map_String(DataTable dataTable) {

        Map<String, String> m = dataTable.asMap(String.class, String.class);

        for (String k : m.keySet())
            System.out.println(String.format("%s : %s", k, m.get(k)));
    }

    @Given("Case 4 - Table to Map<String, List<String>>")
    public void table_to_map_list(DataTable dataTable) {
        Map<String, List<String>> m = dataTable.asMap(String.class, new TypeReference<List<String>>(){}.getType());
        for (String k : m.keySet())
            System.out.println(String.format("%s -> %s", k, StringUtils.join(m.get(k), ",")));
    }

    @Given("Case 5 - Table to Map<String, Map<String, String>>")
    public void table_to_map_map(DataTable dataTable) {
        Map<String, Map<String, String>> m = dataTable.asMap(String.class, new TypeReference<Map<String, String>>(){}.getType());
        for (String k : m.keySet())
            System.out.println(String.format("%s -> lat=%s lon=%s", k, m.get(k).get("lat"), m.get(k).get("lon")));
    }
}
