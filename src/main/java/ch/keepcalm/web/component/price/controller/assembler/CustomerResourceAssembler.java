package ch.keepcalm.web.component.price.controller.assembler;

import ch.keepcalm.web.component.price.controller.CustomerController;
import ch.keepcalm.web.component.price.controller.ProductController;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.CustomerResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toResource(Customer customer) {
        CustomerResource customerResource = createResourceWithId(customer.getId(), customer);

        if (customer.getProducts() != null  && customer.getProducts().size() > 0) {
            for (Product product : customer.getProducts()) {
                Link productLink = new Link(linkTo(ProductController.class).slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "product");
                customerResource.add(productLink);
            };



        }
        customerResource.setCustomer(customer);

        return customerResource;
    }



}
