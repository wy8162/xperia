package y.w.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import y.w.json.jackson.model.PersonImmutable;
import y.w.json.jackson.model.PersonJsonCustomFieldName;
import y.w.json.jackson.model.PersonJsonCustom;
import y.w.json.jackson.model.PersonJsonSerializerDeserializer;
import y.w.json.jackson.model.PersonJsonSetter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * JacksonAnnotationTest
 *
 * @author ywang
 * @date 8/5/2019
 */
@Log4j
public class JacksonAnnotationTest
{
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Uses @JSonSetter to correlate JSON field to Java property name. For de-serializing JSON data to Java object.
     *
     * @JsonGetter can do the same thing - but for serializing Java objects.
     *
     * @throws IOException
     */
    @Test
    public void PersonJsonSetterTest() throws IOException
    {
        PersonJsonSetter p1 = objectMapper.readValue(jsonData, PersonJsonSetter.class);

        log.info(p1);
    }

    /**
     * Uses @JsonAnySetter in case of no setter and getter.
     *
     * @throws IOException
     */
    @Test
    public void PersonJsonAnySetterTest() throws IOException
    {
        PersonJsonCustomFieldName p1 = objectMapper.readValue(jsonData, PersonJsonCustomFieldName.class);

        log.info(p1);
    }

    /**
     * Uses @JsonCreator and @JsonProperty to Java class constructor to de-serialize object.
     *
     * Especially useful for creating immutable objects.
     *
     * @throws IOException
     */
    @Test
    public void PersonImmutableTest() throws IOException
    {
        PersonImmutable p1 = objectMapper.readValue(jsonData, PersonImmutable.class);

        log.info(p1);
    }

    /**
     * Uses @JsonProperty to designate JSON field names which differ from related Java property names.
     *
     * Uses @JsonSerialize and @JsonDeserialize to provide custom deserializer and serializer at field level.
     *
     * @throws IOException
     */
    @Test
    public void PersonJsonSerializerDeserializerTest() throws IOException
    {
        // Deserialize it
        PersonJsonSerializerDeserializer p1 = objectMapper.readValue(jsonData, PersonJsonSerializerDeserializer.class);

        log.info(p1);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, p1);

        String j = stringWriter.toString();

        log.info(j);
    }

    @Test
    public void PersonJsonCustomSerializerDeserializerTest() throws IOException
    {
        // Deserialize it
        PersonJsonCustom p1 = objectMapper.readValue(jsonData, PersonJsonCustom.class);

        log.info(p1);

        // Serialize it
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, p1);

        String j = stringWriter.toString();

        log.info(j);
    }

    private String jsonData = "{\"id\":8162, \"name\":\"Jack\", \"age\":16, \"datetime\":\"2015-09-01T17:00:00.000Z\"}";
}
