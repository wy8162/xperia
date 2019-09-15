package y.w.springdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Immutable Sales object
 */
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name="SALES")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Sales
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    final private String deptNo;

    final private String country;

    final private String partNo;

    final private Double amount;

    // Empty constructor for JSON / JPA
    public Sales() {
        this(null, null, null, null);
    }

    public Sales(String deptNo, String country, String partNo, Double amount)
    {
        this.deptNo = deptNo;
        this.country = country;
        this.partNo = partNo;
        this.amount = amount;
    }
}
