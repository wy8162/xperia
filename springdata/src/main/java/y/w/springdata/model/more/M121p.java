package y.w.springdata.model.more;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * OneToOne - the parent, owns the relationship. Who owns foreign key who owns the relationship.
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "m121p")
public class M121p
{
    @Id
    @GeneratedValue
    @Column(name = "P_ID")
    Long id;

    private String pinfo;

    @OneToOne(cascade = CascadeType.ALL) // m121p in M121c
    @JoinColumn(name="FK_TO_C") // the column in m121p table, as foreign key
    private M121c m121c;

    /**
     * Setup relationship as well
     *
     * @param m121c
     */
    public void setM121c(M121c m121c)
    {
        this.m121c = m121c;
        this.m121c.setM121p(this);
    }
}
