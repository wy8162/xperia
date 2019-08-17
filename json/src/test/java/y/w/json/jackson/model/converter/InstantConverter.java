package y.w.json.jackson.model.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import y.w.json.jackson.model.PersonJsonCustom;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * InstantDeserializer
 *
 * @author ywang
 * @date 8/5/2019
 */
public class InstantConverter
{
    // Format: 2015-09-01T16:00:00.000Z
    public final static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendInstant(3)
            .toFormatter();

    public static class InstantDeserializer extends JsonDeserializer<ZonedDateTime>
    {
        @Override public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException
        {
            return Instant.parse(p.getText()).atZone(ZoneId.of("UTC"));
        }
    }

    public static class InstantSerializer extends JsonSerializer<ZonedDateTime>
    {
        @Override public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException
        {
            gen.writeString(InstantConverter.dateTimeFormatter.format(value));
        }
    }

    // "{\"id\":8162, \"name\":\"Jack\", \"age\":16, \"datetime\":\"2015-09-01T17:00:00.000Z\"}";
    public static class PersonDeserializer extends JsonDeserializer<PersonJsonCustom>
    {
        @Override public PersonJsonCustom deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException
        {
            ObjectCodec oc = p.getCodec();
            JsonNode node = oc.readTree(p);
            return PersonJsonCustom.builder()
                    .firstName(node.get("name").asText())
                    .personId(node.get("id").asText())
                    .theAge(node.get("age").asInt())
                    .birthDay(Instant.parse(node.get("datetime").asText()).atZone(ZoneId.of("UTC")))
                    .build();
        }
    }

    // "{\"id\":8162, \"name\":\"Jack\", \"age\":16, \"datetime\":\"2015-09-01T17:00:00.000Z\"}";
    public static class PersonSerializer extends JsonSerializer<PersonJsonCustom>
    {
        @Override public void serialize(PersonJsonCustom v, JsonGenerator gen, SerializerProvider serializers)
                throws IOException
        {
//            gen.writeString(
//                "{" +
//                     "\"id\":\"" + v.getPersonId() + "\"" +
//                     "\"name\":\"" + v.getFirstName() + "\"" +
//                     "\"age\":\"" + v.getTheAge() + "\"" +
//                     "\"datetime\":\"" + InstantConverter.dateTimeFormatter.format(v.getBirthDay()) + "\"" +
//                     "}"
//            );
            gen.writeStartObject();
            gen.writeStringField("id", v.getPersonId());
            gen.writeStringField("name", v.getFirstName());
            gen.writeNumberField("age", v.getTheAge());
            gen.writeStringField("datetime", InstantConverter.dateTimeFormatter.format(v.getBirthDay()));
            gen.writeEndObject();

        }
    }
}
