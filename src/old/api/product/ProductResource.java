package ch.helsana.web.priceservice.api.product;

import ch.helsana.web.priceservice.model.Doctor;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

/**
 * Created by marcelwidmer on 10/07/16.
 */
public class ProductResource extends ResourceSupport {
    private String productId;
    private String description;
    private BigDecimal price;
    private String unfall;
    private String franchise;
    private String drittesKin;
    private Doctor doctor;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUnfall(String unfall) {
        this.unfall = unfall;
    }

    public String getUnfall() {
        return unfall;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setDrittesKin(String drittesKin) {
        this.drittesKin = drittesKin;
    }

    public String getDrittesKin() {
        return drittesKin;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }


}