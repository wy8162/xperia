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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany - the child. Cascade from both sides
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "mm2mc")
public class MM2Mc
{
    @Id
    @GeneratedValue
    @Column(name = "C_ID")
    Long id;

    private String cinfo;

    @ManyToMany(mappedBy = "mm2mcs", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<MM2Mp> mm2mps = new ArrayList<>();

    public void addMM2Mp(MM2Mp mm2Mp)
    {
        mm2mps.add(mm2Mp);
        mm2Mp.getMm2mcs().add(this);
    }
}
