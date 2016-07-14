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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="address_id")
    private int id;

    private String street;
    private String municipality;  // ": "Dübendorf", /* Gemeindename */
    private String municipality_nr;  //": 191, /* Gemeinde-Nr */
    private String postal_code; // ": 8044, /* Postleitzahl */
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
        this.locality = builder.locality;
    }

    public static Builder newAddress() {
        return new Builder();
    }

    /**
     * Getter for property 'street'.
     *
     * @return Value for property 'street'.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setter for property 'street'.
     *
     * @param street Value to set for property 'street'.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Getter for property 'municipality'.
     *
     * @return Value for property 'municipality'.
     */
    public String getMunicipality() {
        return municipality;
    }

    /**
     * Setter for property 'municipality'.
     *
     * @param municipality Value to set for property 'municipality'.
     */
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    /**
     * Getter for property 'municipality_nr'.
     *
     * @return Value for property 'municipality_nr'.
     */
    public String getMunicipality_nr() {
        return municipality_nr;
    }

    /**
     * Setter for property 'municipality_nr'.
     *
     * @param municipality_nr Value to set for property 'municipality_nr'.
     */
    public void setMunicipality_nr(String municipality_nr) {
        this.municipality_nr = municipality_nr;
    }

    /**
     * Getter for property 'postal_code'.
     *
     * @return Value for property 'postal_code'.
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Setter for property 'postal_code'.
     *
     * @param postal_code Value to set for property 'postal_code'.
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Getter for property 'locality'.
     *
     * @return Value for property 'locality'.
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Setter for property 'locality'.
     *
     * @param locality Value to set for property 'locality'.
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    public static final class Builder {
        private String street;
        private String municipality;
        private String municipality_nr;
        private String postal_code;
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

        public Builder locality(String locality) {
            this.locality = locality;
            return this;
        }
    }
}
