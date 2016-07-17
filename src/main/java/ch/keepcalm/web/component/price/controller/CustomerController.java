package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.controller.assembler.CustomerResourceAssembler;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.CustomerListResource;
import ch.keepcalm.web.component.price.resource.CustomerResource;
import ch.keepcalm.web.component.price.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/customers", produces = "application/hal+json")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public void setCustomerService(CustomerService customerService, CustomerResourceAssembler customerResourceAssembler) {
        this.customerService = customerService;
        this.customerResourceAssembler = customerResourceAssembler;
    }

    /**
     * Create a customer resource
     *
     * @param customer
     * @return
     */
    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResource addCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return customerToResource(customerService.getCustomer(customer.getId()));
    }


    /**
     * Get all customer resources
     *
     * @return
     */
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public CustomerListResource getCustomers() {
        return customerToResource(customerService.getCustomers());
    }

    /**
     * Get one customer resource
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}",
            method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public CustomerResource getCustomer(@PathVariable int id) {
        return customerToResource(customerService.getCustomer(id));
    }


    /**
     * Add a product to a customer
     *
     * @param product
     * @param id
     * @return
     */
    @RequestMapping(
            value = "{id}/products",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResource addProductToCustomer(@RequestBody Product product, @PathVariable int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            customer.getProducts().add(product);
            customerService.updateCustmer(customer);
            return customerToResource(customerService.getCustomer(customer.getId())); // TODO: 17/07/16 calculate price also
        }
        return customerToResource(new Customer()); // TODO: 15.07.2016 not nice solution
    }


    /**
     * Create a list of customer resource
     *
     * @param customers
     * @return
     */
    private CustomerListResource customerToResource(List<Customer> customers) {
        CustomerListResource customerListResource = new CustomerListResource();
        customerListResource.add(linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());
        List<CustomerResource> customerResources = customerResourceAssembler.toResources(customers);
        customerListResource.setCustomerResourceList(customerResources);
        return customerListResource;
    }

    /**
     * Create a customer resource
     *
     * @param customer
     * @return
     */
    private CustomerResource customerToResource(Customer customer) {
        return customerResourceAssembler.toResource(customer);
    }
}
