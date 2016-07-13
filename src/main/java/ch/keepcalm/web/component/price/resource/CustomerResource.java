package ch.keepcalm.web.component.price.resource;

import ch.keepcalm.web.component.price.model.Customer;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

public class CustomerResource extends ResourceSupport {

    @JsonUnwrapped
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
