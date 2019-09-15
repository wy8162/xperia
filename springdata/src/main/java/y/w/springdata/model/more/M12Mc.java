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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OneToMany - Owning side. Cascade from both side
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "m12mc")
public class M12Mc
{
    @Id
    @GeneratedValue
    @Column(name = "C_ID")
    Long id;

    private String cinfo;

    // m12mp in M12Mp who owns the relationship
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TO_P") // Column in this side as Foreign Key
    private M12Mp m121Mp;         // mappedBy from M12Mp
}
