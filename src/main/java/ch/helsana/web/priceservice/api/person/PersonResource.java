package ch.helsana.web.priceservice.api.person;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by marcelwidmer on 10/07/16.
 */
public class PersonResource extends ResourceSupport {

    private String personId;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}