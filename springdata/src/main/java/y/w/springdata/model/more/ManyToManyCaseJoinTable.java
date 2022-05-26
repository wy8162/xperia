package y.w.springdata.model.more;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * Many to Many: One Person can join multiple Clubs; one Club can have multiple Person
 * Person*----membership----*Club
 *
 * Model the relationship with third table with both foreign keys.
 */
public class ManyToManyCaseJoinTable {

    @Data
    @Entity
    @Table(name = "Person", indexes = {
      @Index(name = "idx_person_personid", columnList = "personId")
    })
    public static class Person {
      @Id
      private long personId;
      private String name;

      @ManyToMany // Owner side
      @JoinTable(
        name = "person_club_relationship",                // Joining table name
        joinColumns = @JoinColumn(name = "personId"),     // Joining column
        inverseJoinColumns = @JoinColumn(name = "clubId") // Joining column from the target side
      )
      private Set<Club> clubs;
    }

    @Data
    @Entity
    public static class Club {
      @Id
      private long clubId;
      private String name;

      @ManyToMany(mappedBy = "clubs") // The field of owner side, which maps the joining table.
      private Set<Person> members;
    }
}
