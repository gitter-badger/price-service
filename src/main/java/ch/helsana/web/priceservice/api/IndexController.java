package ch.helsana.web.priceservice.api;


import ch.helsana.web.priceservice.api.person.PersonController;
import ch.helsana.web.priceservice.api.product.ProductController;
import ch.helsana.web.priceservice.api.zip.ZipController;
import ch.helsana.web.priceservice.repository.ProductRepository;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by marcelwidmer on 20/03/16.
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @RequestMapping(method=RequestMethod.GET)
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(ProductController.class).withRel("productss"));



        index.add(linkTo(ProductRepository.class).slash("api/products").withRel("products")); // TODO: 11.07.2016  ????
        index.add(linkTo(PersonController.class).withRel("person"));
        index.add(linkTo(ZipController.class).withRel("zip"));
        index.add(linkTo(IndexController.class).withSelfRel());
        return index;
    }
}
