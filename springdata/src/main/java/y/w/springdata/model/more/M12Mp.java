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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * OneToMany - owned side. Cascade from both side
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "m12mp")
public class M12Mp
{
    @Id
    @GeneratedValue
    @Column(name = "P_ID")
    Long id;

    private String pinfo;

    // mappedBy points to the M12Mp object in ONE side.
    // The field that owns the relationship. Required unless the relationship is unidirectional.
    @OneToMany(mappedBy = "m121Mp", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<M12Mc> m12Mcs = new ArrayList<>();

    /**
     * Setup relationship as well
     *
     * @param m12Mc
     */
    public void addM12Mc(M12Mc m12Mc)
    {
        this.m12Mcs.add(m12Mc);
        m12Mc.setM121Mp(this);
    }
}
