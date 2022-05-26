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
public class ManyToManyCaseJoinEntity {
    @Data
    @Entity
    public static class Membership {
      @Id
      private long id; // The primary key for Membership table

      @ManyToOne
      @JoinColumn(name = "personId")
      private Person person;

      @ManyToOne
      @JoinColumn(name = "clubId")
      private Club club;

      private BigDecimal balance;
    }

    @Data
    @Entity
    @Table(name = "Person")
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
