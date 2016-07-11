package ch.helsana.web.priceservice.service;

import ch.helsana.web.priceservice.api.person.PersonResource;
import ch.helsana.web.priceservice.api.person.PersonResourceAssembler;
import ch.helsana.web.priceservice.model.Person;
import ch.helsana.web.priceservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelwidmer on 22/03/16.
 */
// tag::PersonResourceService[]
@Service
public class PersonResourceService {


    private PersonRepository repository;
    private PersonResourceAssembler resourceAssembler;

    @Autowired
    public void setProductRepository(PersonRepository repository, PersonResourceAssembler resourceAssembler) {
        this.repository = repository;
        this.resourceAssembler = resourceAssembler;
    }


    /**
     * Convert model to Resource
     *
     * @param id
     * @return  one ProductResource
     */
    @Transactional
    public PersonResource findOne(Integer id) {
        Person model = repository.findOne(id);
        PersonResource resource = resourceAssembler.toResource(model);
        return resource;
    }

    /**
     * Convert model to resource
     *
     * @return a list of ProductResources
     */
    @Transactional
    public List<PersonResource> listAll() {
        List<PersonResource> persons  = new ArrayList<PersonResource>();
        for (Person model : repository.findAll()) {
            persons.add(resourceAssembler.toResource(model));
        }
        return persons;
    }

}
// end::PersonResourceService[]
