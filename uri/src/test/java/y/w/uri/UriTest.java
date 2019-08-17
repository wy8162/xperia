package y.w.uri;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * UriTest
 *
 * @author ywang
 * @date 8/12/2019
 */
public class UriTest
{
    @Test
    public void construct_uri_spring () {

        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("www.leveluplunch.com")
                        .path("/java/examples/")
                        .build();

        assertEquals("http://www.leveluplunch.com/java/examples/", uriComponents.toUriString());
    }

    @Test
    public void construct_uri_encoded_spring () {

        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("www.leveluplunch.com")
                        .path("/java/examples/?sample=uri encode")
                        .build()
                        .encode();

        assertEquals("http://www.leveluplunch.com/java/examples/%3Fsample=uri%20encode",
                uriComponents.toUriString());
    }

    /**
     * Construct UIR with template
     */
    @Test
    public void construct_uri_template_spring () {

        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("www.leveluplunch.com")
                        .path("/{lanuage}/{type}/")
                        .build()
                        .expand("java", "examples")
                        .encode();

        assertEquals("http://www.leveluplunch.com/java/examples/",
                uriComponents.toUriString());
    }

    /**
     * Construct URI with query parameter
     */
    @Test
    public void construct_uri_queryparmeter_spring () {

        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("www.leveluplunch.com")
                        .path("/{lanuage}/{type}/")
                        .queryParam("test", "a", "b")
                        .build()
                        .expand("java", "examples")
                        .encode();

        assertEquals("http://www.leveluplunch.com/java/examples/?test=a&test=b",
                uriComponents.toUriString());
    }

    @Test
    public void constructUriWithQueryParameterList() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.google.com")
                .path("/stats")
                .queryParam("metric", Arrays.asList("a", "b"))
                .queryParam("stat", Arrays.asList("MIN", "MAX"))
                .queryParam("from", "2019-08-21T15:15:00.000Z")
                .queryParam("to", "2019-08-21T15:15:00.000Z")
                .buildAndExpand("a", "b", "c", "d", "e", "f")
                .encode();

        assertEquals("http://www.google.com/stats?metric=%5Ba,%20b%5D&stat=%5BMIN,%20MAX%5D&from=2019-08-21T15:15:00.000Z&to=2019-08-21T15:15:00.000Z", uriComponents.toUriString());
    }

    @Test
    public void constructUriWithQueryParameter() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.google.com")
                .path("/stats")
                .query("metric={value1}")
                .query("metric={value2}")
                .query("stats={value4}")
                .query("stats={value5}")
                .query("from={value6}")
                .query("to={value7}")
                .buildAndExpand("a", "b", "c", "d", "e", "f");

        assertEquals("http://www.google.com/stats?metric=a&metric=b&stats=c&stats=d&from=e&to=f", uriComponents.toUriString());
    }

    @Test
    public void constructUriWithQueryParameterMap() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", "key");
        params.add("storeId", "storeId");
        params.add("orderId", "orderId");
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://spsenthil.com/order")
                .queryParams(params)
                .build();

        assertEquals("http://spsenthil.com/order?key=key&storeId=storeId&orderId=orderId", uriComponents.toUriString());
    }
}
