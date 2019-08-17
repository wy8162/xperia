package y.w.json.jackson.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor // Needed for JSON and JPA
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonJsonCustomFieldName
{
    // There is no name or whatever. All data stored in a map.
    @JsonAnySetter
    Map<String, String> data = new HashMap<String, String>();
}
