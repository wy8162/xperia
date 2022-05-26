package y.w.springdata.model.more;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * Many to Many: One Person can join multiple Clubs; one Club can have multiple Person
 * Person*----Membership----*Club
 *
 * Here, Membership has more information than two foreign keys. For example, Membership has charge balance.
 * The relationship it has attributes.
 *
 * Model the relationship with composite key.
 */
public class ManyToManyCaseCompositeKey {
    @Data
    @Embeddable
    public static class MembershipKey implements Serializable { // Must implement Serializable
      @Column(name = "personId")
      private long personId;

      @Column(name = "clubId")
      private long clubId;
    }

    @Data
    @Entity
    public static class Membership {
      @EmbeddedId
      private MembershipKey id; // The composite primary key

      @ManyToOne
      @MapsId("personId")       // Claims foreign key
      @JoinColumn(name = "personId")
      private Person person;

      @ManyToOne
      @MapsId("clubId")         // Claims foreign key
      @JoinColumn(name = "clubId")
      private Club club;

      private BigDecimal balance;
    }

    @Data
    @Entity
    @Table(name = "Person", indexes = {
      @Index(name = "idx_person_personid", columnList = "personId")
    })
    public static class Person {
      @Id
      private long personId;
      private String name;

      @OneToMany(mappedBy = "person") // Inverse reference
      private Set<Membership> memberships;
    }

    @Data
    @Entity
    public static class Club {
      @Id
      private long clubId;
      private String name;

      @OneToMany(mappedBy = "club") // Inverse reference.
      private Set<Membership> members;
    }
}
