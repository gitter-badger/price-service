package ch.helsana.web.priceservice.service;

/**
 * Created by hkesq on 11.07.2016.
 */

import ch.helsana.web.priceservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// tag::PersonService[]
@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    public void setPersonRepository(PersonRepository repository) {
        this.repository = repository;
    }

    


}
// end::PersonService[]
