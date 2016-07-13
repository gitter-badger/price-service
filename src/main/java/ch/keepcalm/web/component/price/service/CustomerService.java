package ch.keepcalm.web.component.price.service;

import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;
    @Autowired
    public void setCustomerRepository(CustomerRepository repository) {
        this.repository = repository;
    }


    public Customer getCustomer(int id) {
        return repository.findOne(id);
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        for (Customer model : repository.findAll()) { // TODO: 12/07/16  no better solution
            customers.add(model);
        }
        return customers;
    }
}
