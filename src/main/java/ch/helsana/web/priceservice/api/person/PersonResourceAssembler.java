package ch.helsana.web.priceservice.api.person;

import ch.helsana.web.priceservice.model.Person;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by marcelwidmer on 10/07/16.
 */
@Component
public class PersonResourceAssembler extends ResourceAssemblerSupport<Person, PersonResource> {
    /**
     * Constructor
     */
    public PersonResourceAssembler() {
        super(PersonController.class, PersonResource.class);
    }

    /**
     * Convert domain product to resource product
     *
     * @param person
     * @return
     */
    @Override
    public PersonResource toResource(Person person) {
        PersonResource resource = createResourceWithId(person.getId(), person); // adds a "self" link
        resource.setPersonId(person.getId());
        resource.setFirstName(person.getFirstName());
        resource.setLastName(person.getLastName());
        return resource;
    }
}

