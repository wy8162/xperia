package y.w.json.jackson.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import y.w.json.jackson.model.converter.InstantConverter;

import java.time.ZonedDateTime;

/**
 * @JsonSetter and @JsonGetter can define the JSON field names which are different from Java property
 * names.
 *
 * But @JsonSetter and @JsonGetter can't be used a the same time.
 *
 * A new way is to use @JsonProperty
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor // Needed for JSON and JPA
public class PersonJsonSerializerDeserializer
{
    // @JsonSetter("id") // the same logic for @JsonGetter
    @JsonProperty("id")
    private String personId;

    // @JsonSetter("name")
    @JsonProperty("name")
    private String firstName;

    // @JsonSetter("age")
    @JsonProperty("age")
    private int theAge;

    /*
     * Jackson can't deserialize the time format like "2015-09-01T17:00:00.000Z".
     * We need a deserializer
     */
    @JsonProperty("datetime")
    @JsonDeserialize(using = InstantConverter.InstantDeserializer.class)
    @JsonSerialize(using = InstantConverter.InstantSerializer.class)
    private ZonedDateTime birthDay;
}
