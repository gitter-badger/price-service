package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.controller.assembler.CustomerResourceAssembler;
import ch.keepcalm.web.component.price.model.Customer;
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

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResource addCustomer(@RequestBody Customer customer) {
        // TODO: 14.07.2016  Implement me !!
        return customerToResource(customer);
    }


    @RequestMapping(method = RequestMethod.GET)
    public CustomerListResource getCustomers() {
        return customerToResource(customerService.getCustomers());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CustomerResource getCustomer(@PathVariable int id) {
        return customerToResource(customerService.getCustomer(id));
    }

    private CustomerListResource customerToResource(List<Customer> customers) {
        CustomerListResource customerListResource = new CustomerListResource();
        customerListResource.add(linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());
        List<CustomerResource> customerResources = customerResourceAssembler.toResources(customers);
        customerListResource.setCustomerResourceList(customerResources);
        return customerListResource;
    }

    private CustomerResource customerToResource(Customer customer) {
        return customerResourceAssembler.toResource(customer);
    }
}
