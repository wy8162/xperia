package y.w.springdata.model.more;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * OneToOne - the child
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "m121c")
public class M121c
{
    @Id
    @GeneratedValue
    @Column(name = "C_ID")
    Long id;

    private String cinfo;

    @OneToOne(mappedBy = "m121c") // m121c in M121p who owns the relationship
    private M121p m121p;
}
