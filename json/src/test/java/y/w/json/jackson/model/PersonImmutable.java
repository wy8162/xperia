package y.w.json.jackson.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * Immutable class without any setter.
 */
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonImmutable
{
    private final String personId;
    private final String firstName;
    private final int theAge;

    @JsonCreator
    public PersonImmutable(@JsonProperty("id") String personId,
                           @JsonProperty("name") String firstName,
                           @JsonProperty("age") int theAge)
    {
        this.personId = personId;
        this.firstName = firstName;
        this.theAge = theAge;
    }
}
