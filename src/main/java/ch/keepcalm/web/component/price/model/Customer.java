package ch.keepcalm.web.component.price.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int id;

    private String firstName;
    private String lastName;

    @ManyToMany(targetEntity = Product.class)
    private List <Product> products;


    /**
     * Used for Entity
     */
    public Customer() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    private Customer(Builder builder) {
        setId(builder.id);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setProducts(builder.products);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if(products == null)
            products = new ArrayList<>();
        this.products.add(product);
    }


    /**
     * {@code Customer} builder static inner class.
     */
    public static final class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private List<Product> products;

        private Builder() {
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder id(int val) {
            id = val;
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
         * Sets the {@code products} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code products} to set
         * @return a reference to this Builder
         */
        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        /**
         * Returns a {@code Customer} built from the parameters previously set.
         *
         * @return a {@code Customer} built with parameters of this {@code Customer.Builder}
         */
        public Customer build() {
            return new Customer(this);
        }
    }
}
