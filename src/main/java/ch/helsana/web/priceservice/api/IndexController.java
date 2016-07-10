package ch.helsana.web.priceservice.api;


import ch.helsana.web.priceservice.api.product.ProductController;
import ch.helsana.web.priceservice.controller.PriceServiceController;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

        Link products = linkTo(methodOn(ProductController.class).showAll()).withRel("products");
        //Link products = linkTo(ProductController.class).withRel("products");

        Link productBestPrice = linkTo(methodOn(PriceServiceController.class).berechneBesterPreis(null)).withRel("best-price");
        Link productPrice = linkTo(methodOn(PriceServiceController.class).berechnePraemie(null)).withRel("price");



       /* Link person = linkTo(methodOn(PersonController.class).show(1L)).withRel("person");
        Link products = linkTo(ProductController.class).withRel("products");
        Link personProducts = linkTo(PersonProductController.class, 1L).withRel("personProducts");
        Link personResources = linkTo(methodOn(PersonController.class).showAllAsResources()).withRel("peopleAsResources");
        Link pagedResources = linkTo(methodOn(PersonController.class).showAllPaged()).withRel("peoplePaged");
        Link search = linkTo(methodOn(PersonController.class).searchPersonForm()).withRel("search");
*/
        links.add(self);
        links.add(products);
        links.add(productBestPrice);
        links.add(productPrice);
        //links.add(search);
        /*links.add(person);
        links.add(products);
        links.add(personProducts);
        links.add(personResources);
        links.add(pagedResources);*/
        return new HttpEntity<List<Link>>(links);
    }

}
