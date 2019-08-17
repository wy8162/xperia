package y.w.json.jackson.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor // Needed for JSON and JPA
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonJsonSetter
{
    // Json data above doesn't match the setter/getter below.
    @JsonSetter("id") // "id" in JSON data mapps to personId
    private String personId;

    @JsonSetter("name")
    private String firstName;

    @JsonSetter("age")
    private int theAge;
}
