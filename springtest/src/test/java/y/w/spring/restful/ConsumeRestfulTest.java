package y.w.spring.restful;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumeRestfulTest
{
    @Test
    public void loadContextTest()
    {
        // Fails if Spring fails to load context.
    }

    @Test
    public void consumeJSONService() throws IOException
    {
        final String uri = "https://api.worldtradingdata.com/api/v1/mutualfund?symbol=AAAAX,AAADX,AAAGX&api_token=demo";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(json.get("message"));
        System.out.println(json.get("data"));
    }

    @Test
    public void consumeJSONObjectService() throws IOException
    {
        final String uri = "https://api.worldtradingdata.com/api/v1/mutualfund?symbol=AAAAX,AAADX,AAAGX&api_token=demo";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MutualFundDTO> response = restTemplate.getForEntity(uri, MutualFundDTO.class);

        MutualFundDTO dto = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * ForexUsd does not match the data structure from the RESTful service. We
     * need our own deserializer.
     *
     * @throws IOException
     */
    @Test
    public void consumeJSONDeserializerService() throws IOException
    {
        final String uri = "https://api.worldtradingdata.com/api/v1/forex?base=USD&api_token=demo";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ForexUsd> response = restTemplate.getForEntity(uri, ForexUsd.class);

        ForexUsd usdEur = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
