package ch.helsana.web.priceservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by marcelwidmer on 21/03/16.
 * <p>
 * "produkt": [
 * {
 * "arzt": {
 * "avmNetz": "AVN_N_1AH_HEL"
 * },
 * "drittesKind": "Nein",
 * "franchise": "COD_Franchise_KVG-O_Erwachsener_1500_HEL",
 * "produktId": "PRO_P0BEPH_HEL_IG",
 * "unfall": "COD_ausgeschlossen_HEL"
 * }
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;


    @Size(min = 1, max = 36)
    private String productId;
    @Size(min = 2, max = 30)
    private String description;

    private String unfall;
    private String franchise;
    private String drittesKind;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="doctor_id")
    private Doctor doctor;

    private BigDecimal price;

    /**
     * Used for Entity
     */
    public Product() {}

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public String getUnfall() {
        return unfall;
    }

    public String getFranchise() {
        return franchise;
    }

    public String getDrittesKind() {
        return drittesKind;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private Product(Builder builder) {
        id = builder.id;
        productId = builder.productId;
        description = builder.description;
        unfall = builder.unfall;
        franchise = builder.franchise;
        drittesKind = builder.drittesKind;
        doctor = builder.doctor;
        price = builder.price;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * {@code Product} builder static inner class.
     */
    public static final class Builder {
        private Integer id;
        private String productId;
        private String description;
        private String unfall;
        private String franchise;
        private String drittesKind;
        private Doctor doctor;
        private BigDecimal price;

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
         * Sets the {@code productId} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code productId} to set
         * @return a reference to this Builder
         */
        public Builder productId(String val) {
            productId = val;
            return this;
        }

        /**
         * Sets the {@code description} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code description} to set
         * @return a reference to this Builder
         */
        public Builder description(String val) {
            description = val;
            return this;
        }

        /**
         * Sets the {@code unfall} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code unfall} to set
         * @return a reference to this Builder
         */
        public Builder unfall(String val) {
            unfall = val;
            return this;
        }

        /**
         * Sets the {@code franchise} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code franchise} to set
         * @return a reference to this Builder
         */
        public Builder franchise(String val) {
            franchise = val;
            return this;
        }

        /**
         * Sets the {@code drittesKind} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code drittesKind} to set
         * @return a reference to this Builder
         */
        public Builder drittesKind(String val) {
            drittesKind = val;
            return this;
        }

        /**
         * Sets the {@code doctor} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code doctor} to set
         * @return a reference to this Builder
         */
        public Builder doctor(Doctor val) {
            doctor = val;
            return this;
        }

        /**
         * Sets the {@code price} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code price} to set
         * @return a reference to this Builder
         */
        public Builder price(BigDecimal val) {
            price = val;
            return this;
        }

        /**
         * Returns a {@code Product} built from the parameters previously set.
         *
         * @return a {@code Product} built with parameters of this {@code Product.Builder}
         */
        public Product build() {
            return new Product(this);
        }
    }
}