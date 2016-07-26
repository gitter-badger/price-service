package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.controller.assembler.CustomerResourceAssembler;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.resource.CustomerListResource;
import ch.keepcalm.web.component.price.resource.CustomerResource;
import ch.keepcalm.web.component.price.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        CustomerResource customerResource = customerToResource(customerService.getCustomer(customer.getId()));
        return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.CREATED);
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
    public ResponseEntity getCustomers() {
        CustomerListResource customerListResource = customerToResource(customerService.getCustomers());
        return new ResponseEntity<CustomerListResource>(customerListResource, HttpStatus.OK);
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
    public ResponseEntity getCustomer(@PathVariable int id) {
        CustomerResource customerResource = customerToResource(customerService.getCustomer(id));
        return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.OK);
    }


    /**
     * Create a list of customer resource
     *
     * @param customers
     * @return
     */
    private CustomerListResource customerToResource(List<Customer> customers) {
        // api/customers>;rel="self
        CustomerListResource customerListResource = new CustomerListResource();
        customerListResource.add(linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());
        List<CustomerResource> customerResources = customerResourceAssembler.toResources(customers);
        customerListResource.setCustomerResources(customerResources);
        // api/customers>;rel="create_customer"
        Link createCustomerLink = new Link(linkTo(CustomerController.class).toUriComponentsBuilder().build().toUriString(), "create_customer");
        customerListResource.add(createCustomerLink);
        // api/customers>;rel="list_customers
        Link listCustomerLink = new Link(linkTo(CustomerController.class).toUriComponentsBuilder().build().toUriString(), "list_customers");
        customerListResource.add(listCustomerLink);

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
