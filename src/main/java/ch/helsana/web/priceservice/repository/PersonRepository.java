package ch.helsana.web.priceservice.repository;

import ch.helsana.web.priceservice.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by hkesq on 20.06.2016.
 */
public interface PersonRepository extends CrudRepository<Person,  Integer> {
    Person findByPersonId(String id);

}
