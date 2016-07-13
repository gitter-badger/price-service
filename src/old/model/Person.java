package ch.helsana.web.priceservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by hkesq on 20.06.2016.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personId;

    @NotNull
    @Size(min = 1, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Person(Builder builder) {
        personId = builder.personId;
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return personId;
    }

    public void setId(Integer personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * {@code Person} builder static inner class.
     */
    public static final class Builder {
        private Integer personId;
        private String firstName;
        private String lastName;

        private Builder() {
        }

        /**
         * Sets the {@code personId} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code personId} to set
         * @return a reference to this Builder
         */
        public Builder personId(Integer val) {
            personId = val;
            return this;
        }

        /**
         * Sets the {@code firstName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code firstName} to set
         * @return a reference to this Builder
         */
        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        /**
         * Sets the {@code lastName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code lastName} to set
         * @return a reference to this Builder
         */
        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        /**
         * Returns a {@code Person} built from the parameters previously set.
         *
         * @return a {@code Person} built with parameters of this {@code Person.Builder}
         */
        public Person build() {
            return new Person(this);
        }
    }
}
