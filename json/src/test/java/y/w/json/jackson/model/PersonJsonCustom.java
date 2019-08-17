package y.w.json.jackson.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import y.w.json.jackson.model.converter.InstantConverter;

import java.time.ZonedDateTime;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor // Needed for JSON and JPA
@JsonDeserialize(using = InstantConverter.PersonDeserializer.class)
@JsonSerialize(using = InstantConverter.PersonSerializer.class)
public class PersonJsonCustom
{
    private String personId;
    private String firstName;
    private int theAge;
    private ZonedDateTime birthDay;
}
