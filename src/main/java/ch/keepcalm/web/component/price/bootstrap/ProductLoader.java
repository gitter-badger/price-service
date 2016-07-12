package ch.keepcalm.web.component.price.bootstrap;


import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.repository.CustomerRepository;
import ch.keepcalm.web.component.price.repository.ProductRepository;
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
    private CustomerRepository customerRepository;

    private Logger log = Logger.getLogger(ProductLoader.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository, CustomerRepository personRepository) {
        this.productRepository = productRepository;
        this.customerRepository = personRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Product productOne = Product.newBuilder()
                .productId("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .build();
        productRepository.save(productOne);
        log.info("Product-One - id: " + productOne.getId());


        Product productTwo = Product.newBuilder()
                .productId("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Ja")
                .unfall("COD_ausgeschlossen_HEL")
                .build();
        productRepository.save(productTwo);
        log.info("Product-Two - id: " + productTwo.getId());

        Customer personOne = Customer.newBuilder().firstName("Foo").lastName("Bar").build();
        customerRepository.save(personOne);
        log.info("Person-One - id: " + personOne.getId());

        Customer personTwo = Customer.newBuilder().firstName("Mr").lastName("X").build();
        customerRepository.save(personTwo);
        log.info("Person-Two - id: " + personTwo.getId());



    }
}
