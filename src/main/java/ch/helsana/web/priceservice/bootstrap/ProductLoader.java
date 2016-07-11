package ch.helsana.web.priceservice.bootstrap;


import ch.helsana.web.priceservice.model.Doctor;
import ch.helsana.web.priceservice.model.Person;
import ch.helsana.web.priceservice.model.Product;
import ch.helsana.web.priceservice.repository.PersonRepository;
import ch.helsana.web.priceservice.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * Created by marcelwidmer on 21/03/16.
 *
 * This class implements the ApplicationListner interface, so it gets called upon the ContextRefresedEvent on startup.
 * We’re using Spring to inject the Spring Data JPA repository into the class for our use.
 * In this example, I’m creating two entities and saving them to the database.
 */
@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;
    private PersonRepository personRepository;

    private Logger log = Logger.getLogger(ProductLoader.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository, PersonRepository personRepository) {
        this.productRepository = productRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Product productOne = Product.newBuilder()
                .productId("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .doctor(Doctor.newBuilder().avmNetz("AVN_N_1AH_HEL").build())
                .build();
        productRepository.save(productOne);
        log.info("Product-One - id: " + productOne.getId());


        Product productTwo = Product.newBuilder()
                .productId("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Ja")
                .unfall("COD_ausgeschlossen_HEL")
                .doctor(Doctor.newBuilder().avmNetz("AVN_N_1AH_HEL").build())
                .build();
        productRepository.save(productTwo);
        log.info("Product-Two - id: " + productTwo.getId());

        Person personOne = Person.newBuilder().firstName("Foo").lastName("Bar").build();
        personRepository.save(personOne);
        log.info("Person-One - id: " + personOne.getId());



    }
}
