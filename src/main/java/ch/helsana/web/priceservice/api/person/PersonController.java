package ch.helsana.web.priceservice.api.person;

import ch.helsana.web.priceservice.service.PersonResourceService;
import ch.helsana.web.priceservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hkesq on 11.07.2016.
 */
@RestController
@RequestMapping("/api/person")
public class PersonController {

    private PersonResourceService resourceService;
    private PersonService service;


    @Autowired
    public void setProductService(PersonResourceService resourceService, PersonService service) {
        this.resourceService = resourceService;
        this.service = service;
    }

    /**
     * TODO: 10/07/16 HATEOAS
     *
     * @param personId
     * @return
     */
    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    public HttpEntity<PersonResource> findOne(@PathVariable("personId") Integer personId) {
        PersonResource resource = resourceService.findOne(personId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * TODO: 10/07/16 HATEOAS
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<PersonResource>> showAll() {
        List<PersonResource> resources = resourceService.listAll();
        return new ResponseEntity<List<PersonResource>>(resources, HttpStatus.OK);
    }






}
