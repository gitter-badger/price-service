package ch.keepcalm.web.component.price.controller.assembler;

import ch.keepcalm.web.component.price.controller.CustomerAggregateController;
import ch.keepcalm.web.component.price.controller.CustomerController;
import ch.keepcalm.web.component.price.model.Customer;
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
        // api/customers/1; rel="self
        CustomerResource customerResource = createResourceWithId(customer.getId(), customer);
        // GET products http://localhost:8080/api/customers/1/products; rel="list_products"
        Link listProducts = new Link(linkTo(CustomerAggregateController.class)
                .slash(customer.getId())
                .slash("products").toUriComponentsBuilder().build().toUriString(), "list_products");
        customerResource.add(listProducts);

        // POST products http://localhost:8080/api/customers/1/products; rel="create_product"
        Link createProducts = new Link(linkTo(CustomerAggregateController.class)
                .slash(customer.getId())
                .slash("products").toUriComponentsBuilder().build().toUriString(), "create_product");
        customerResource.add(createProducts);


        // POST products http://localhost:8080/api/customers/1/deeplink; rel="create_deeplink"
        Link deeplink = new Link(linkTo(CustomerAggregateController.class)
                .slash(customer.getId())
                .slash("deeplink").toUriComponentsBuilder().build().toUriString(), "create_deeplink");
        customerResource.add(deeplink);

        customerResource.setCustomer(customer);
        return customerResource;
    }


}
