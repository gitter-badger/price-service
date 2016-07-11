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
        // TODO: 11.07.2016  exception handling here ??!!!
     /*   try {
            Link products = linkTo(methodOn(ProductController.class).price(null)).withRel("price");
            productResource.add(products);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: copy properties from product to productResource
        productResource.setProductId(product.getProductId());
        productResource.setDescription(product.getDescription());
        productResource.setUnfall(product.getUnfall());
        productResource.setFranchise(product.getFranchise());
        productResource.setDrittesKin(product.getDrittesKind());
        productResource.setDoctor(product.getDoctor());
        productResource.setPrice(product.getPrice());*/

        return resource;
    }
}

