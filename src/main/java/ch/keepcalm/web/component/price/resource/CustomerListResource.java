package ch.keepcalm.web.component.price.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class CustomerListResource extends ResourceSupport {

    @JsonUnwrapped
    private List<CustomerResource> customerResources;

    public List<CustomerResource> getCustomerResources() {
        return customerResources;
    }

    public void setCustomerResources(List<CustomerResource> customerResources) {
        this.customerResources = customerResources;
    }
}
