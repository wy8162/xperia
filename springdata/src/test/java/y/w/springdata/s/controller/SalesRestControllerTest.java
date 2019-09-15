package y.w.springdata.s.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import y.w.springdata.controller.SalesRestController;
import y.w.springdata.model.Sales;
import y.w.springdata.service.SalesService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * SalesControllerTest
 */
@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(SalesRestController.class)
public class SalesRestControllerTest
{
    @MockBean
    private SalesService salesService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<Sales>       jsonSale; // to be initialized by setup below.
    private JacksonTester<List<Sales>> jsonSales; // to be initialized by setup below.

    @Before
    public void setup()
    {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void findOneTest() throws Exception
    {
        Optional<Sales> sales = Optional.of(new Sales( "Marketing", "China", "iPAD", new Double(100.0)));

        // Given
        given(salesService.findOne(anyLong()))
                .willReturn(sales);

        // When
        MockHttpServletResponse response = mvc.perform(get("/api/v1/order").accept(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo(jsonSale.write(sales.get()).getJson());
    }

    @Test
    public void findAllTest() throws Exception
    {
        List<Sales> sales = Lists.newArrayList(
                new Sales("Marketing", "China", "iPAD", new Double(100.0)),
                new Sales("Sales", "America", "Apple Pencil", new Double(120.0)));

        // Given
        given(salesService.findAll())
                .willReturn(sales);

        // When
        MockHttpServletResponse response = mvc.perform(get("/api/v1/sales").accept(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo(jsonSales.write(sales).getJson());
    }

    @Test
    public void createSalesTest() throws IOException, Exception
    {
        // Given
        Sales sales = new Sales( "AnyDept", "AnyCountry", "AnyProd", new Double(100.0));
        given(salesService.createSales(sales)).willReturn(sales);

        // When
        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/sales").contentType(MediaType.APPLICATION_JSON)
                .content(jsonSale.write(sales).getJson()))
                .andReturn()
                .getResponse();

        // Then
        Sales sales2 = new Sales( "AnyDept", "AnyCountry", "AnyProd", new Double(100.0));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonSale.write(sales2).getJson());

        log.info(response.getContentAsString());
    }
}
