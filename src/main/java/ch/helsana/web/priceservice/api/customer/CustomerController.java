package ch.helsana.web.priceservice.api.customer;

import ch.helsana.web.priceservice.api.product.ProductResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hkesq on 11.07.2016.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    /**
     * TODO: 10/07/16 HATEOAS
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<ProductResource> create(@RequestBody String customer ) { // TODO: 11.07.2016 create object
        // TODO: 07.07.2016 implement me !!
        return new ResponseEntity("{ Request : " +
                "\"" + customer +"\"}", HttpStatus.CREATED);
    }
}
