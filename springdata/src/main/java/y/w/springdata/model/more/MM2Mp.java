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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany - owning side. Cascade from both sides
 *
 * Hibernate says:
 *
 * Practical test cases for real many-to-many associations are rare. Most of the time
 * you need additional information stored in the "link table". In this case, it is much
 * better to use two one-to-many associations to an intermediate link class. In fact,
 * most associations are one-to-many and many-to-one. For this reason, you should proceed
 * cautiously when using any other association style.
 *
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "mm2mp")
public class MM2Mp
{
    @Id
    @GeneratedValue
    @Column(name = "P_ID")
    Long id;

    private String pinfo;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "P_M2M_C",    // Joining table. Who owns Joining Table who owns relationship.
            joinColumns = {@JoinColumn(name="FK_P_ID", referencedColumnName = "P_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FK_C_ID", referencedColumnName = "C_ID")})
    @Builder.Default
    private List<MM2Mc> mm2mcs = new ArrayList<>();

    /**
     * Setup relationship as well
     *
     * @param mm2Mc
     */
    public void addMM2Mc(MM2Mc mm2Mc)
    {
        this.mm2mcs.add(mm2Mc);
        mm2Mc.getMm2mps().add(this);
    }
}
