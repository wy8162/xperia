package y.w.spring.model;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@ToString
@Entity
@Table(name="SALES")
public class Sales
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull @Column(name = "ORDER_NO")
    private Long Id;

    @NotNull @Column(name = "PART_NO")
    private String partNo;

    @NotNull @Column(name = "DEPT_NO")
    private String deptNo;

    @NotNull @Column(name = "COUNTRY")
    private String country;

    @NotNull @Column(name = "AMOUNT")
    private Double amount;

    public Sales()
    {
    }

    public Sales(@NotNull Long orderNo, @NotNull String deptNo, @NotNull String country, @NotNull String partNo, @NotNull Double amount)
    {
        this.Id = orderNo;
        this.partNo = partNo;
        this.deptNo = deptNo;
        this.country = country;
        this.amount = amount;
    }

    public Long getId()
    {
        return Id;
    }

    public void setId(Long id)
    {
        Id = id;
    }

    public String getPartNo()
    {
        return partNo;
    }

    public void setPartNo(String partNo)
    {
        this.partNo = partNo;
    }

    public String getDeptNo()
    {
        return deptNo;
    }

    public void setDeptNo(String deptNo)
    {
        this.deptNo = deptNo;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }
}
