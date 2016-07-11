package ch.helsana.web.priceservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by marcelwidmer on 10/07/16.
 */
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="doctor_id")
    private Integer id;

    private String avmNetz;


    /**
     * Used for Entity
     */
    public Doctor() {}

    public Integer getId() {
        return id;
    }

    public String getAvmNetz() {
        return avmNetz;
    }

    private Doctor(Builder builder) {
        id = builder.id;
        avmNetz = builder.avmNetz;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * {@code Doctor} builder static inner class.
     */
    public static final class Builder {
        private Integer id;
        private String avmNetz;

        private Builder() {
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder id(Integer val) {
            id = val;
            return this;
        }

        /**
         * Sets the {@code avmNetz} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code avmNetz} to set
         * @return a reference to this Builder
         */
        public Builder avmNetz(String val) {
            avmNetz = val;
            return this;
        }

        /**
         * Returns a {@code Doctor} built from the parameters previously set.
         *
         * @return a {@code Doctor} built with parameters of this {@code Doctor.Builder}
         */
        public Doctor build() {
            return new Doctor(this);
        }
    }
}
