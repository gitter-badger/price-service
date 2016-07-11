package ch.helsana.web.priceservice.api.zip;

import ch.helsana.web.priceservice.api.product.ProductResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hkesq on 11.07.2016.
 */
@RestController
@RequestMapping("/api/zip")
public class ZipController {

    /**
     * TODO: 10/07/16 HATEOAS
     *
     * @param zipCode
     * @return
     */
    @RequestMapping(value = "/search/{zipCode}", method = RequestMethod.GET)
    public HttpEntity<ProductResource> search(@PathVariable("zipCode") String zipCode) {
        // TODO: 07.07.2016 implement me !!
        return new ResponseEntity("{ YourValue : " +
                "\"" + zipCode +"\"}", HttpStatus.ACCEPTED);
    }
}
