package ch.keepcalm.web.component.price.controller;


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
        index.add(linkTo(ProductController.class).withRel("products"));
        index.add(linkTo(CustomerController.class).withRel("customers"));
        index.add(linkTo(IndexController.class).withSelfRel());
        return index;
    }
}
