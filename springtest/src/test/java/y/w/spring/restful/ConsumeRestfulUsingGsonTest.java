package y.w.spring.restful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import y.w.spring.config.JpaTransactionConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import y.w.spring.JDBC.repository.SpringDataJpaSalesRepository;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= JpaTransactionConfig.class)
@EnableJpaRepositories(basePackageClasses={ SpringDataJpaSalesRepository.class})
@ActiveProfiles("dev")
public class ConsumeRestfulUsingGsonTest
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

        //Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .create();

        GsonMutualFundDTO mutualFundDTO = gson.fromJson(response.getBody(), GsonMutualFundDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(gson.toJson(mutualFundDTO));
    }
}
