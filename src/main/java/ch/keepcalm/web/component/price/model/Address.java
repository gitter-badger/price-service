package ch.keepcalm.web.component.price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by marcelwidmer on 10/07/16.
 */
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="address_id")
    private int id;

    @JsonIgnore // TODO: 21/07/16 jsonIgnore
    private String street;
    private String municipality;  // ": "Dübendorf", /* Gemeindename */
    private String municipality_nr;  //": 191, /* Gemeinde-Nr */
    private String postal_code; // ": 8044, /* Postleitzahl */
    private String postal_code_addition; // ": 00, /* postleitzahlZusatz */
    private String locality; //": "Gockhausen" /* Ort */



    /**
     * Used for Entity
     */
    public Address() {}

    private Address(Builder builder) {
        this.street = builder.street;
        this.municipality = builder.municipality;
        this.municipality_nr = builder.municipality_nr;
        this.postal_code = builder.postal_code;
        this.postal_code_addition = builder.postal_code_addition;
        this.locality = builder.locality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getMunicipality_nr() {
        return municipality_nr;
    }

    public void setMunicipality_nr(String municipality_nr) {
        this.municipality_nr = municipality_nr;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPostal_code_addition() {
        return postal_code_addition;
    }

    public void setPostal_code_addition(String postal_code_addition) {
        this.postal_code_addition = postal_code_addition;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public static Builder newAddress() {
        return new Builder();
    }


    public static final class Builder {
        private String street;
        private String municipality;
        private String municipality_nr;
        private String postal_code;
        private String postal_code_addition;
        private String locality;

        private Builder() {
        }

        public Address build() {
            return new Address(this);
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder municipality(String municipality) {
            this.municipality = municipality;
            return this;
        }

        public Builder municipality_nr(String municipality_nr) {
            this.municipality_nr = municipality_nr;
            return this;
        }

        public Builder postal_code(String postal_code) {
            this.postal_code = postal_code;
            return this;
        }

        public Builder postal_code_addition(String postal_code_addition) {
            this.postal_code_addition = postal_code_addition;
            return this;
        }

        public Builder locality(String locality) {
            this.locality = locality;
            return this;
        }
    }
}
