package ch.keepcalm.web.component.price.model;

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
    private int id;

    private String avmNetz;


    /**
     * Used for Entity
     */
    public Doctor() {}

    private Doctor(Builder builder) {
        setAvmNetz(builder.avmNetz);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAvmNetz() {
        return avmNetz;
    }

    public void setAvmNetz(String avmNetz) {
        this.avmNetz = avmNetz;
    }


    /**
     * {@code Doctor} builder static inner class.
     */
    public static final class Builder {
        private String avmNetz;

        private Builder() {
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
