package ch.helsana.web.priceservice.api;


import ch.helsana.web.priceservice.api.person.PersonController;
import ch.helsana.web.priceservice.api.product.ProductController;
import ch.helsana.web.priceservice.api.zip.ZipController;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by marcelwidmer on 20/03/16.
 */
@RestController
@RequestMapping("/api")
public class IndexController {


    @RequestMapping(value="",
            method = RequestMethod.GET
    )
    public HttpEntity<List<Link>> showLinks() throws Exception {

        List<Link> links = new ArrayList<Link>();
        Link self = linkTo(IndexController.class).withSelfRel();
        Link products = linkTo(ProductController.class).withRel("products");
        Link zip = linkTo(ZipController.class).withRel("zip"); // TODO: 11.07.2016
        Link person = linkTo(PersonController.class).withRel("person"); // TODO: 11.07.2016

        links.add(self);
        links.add(zip);
        links.add(person);
        links.add(products);

        return new HttpEntity<List<Link>>(links);
    }

}
