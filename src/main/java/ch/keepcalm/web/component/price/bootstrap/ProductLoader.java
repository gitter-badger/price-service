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

import java.util.ArrayList;
import java.util.List;


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
                .productNumber("ProductNumer_1")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .build();
        productRepository.save(productOne);
        log.info("Product-One - id: " + productOne.getId());


        Product productTwo = Product.newBuilder()
                .productNumber("ProductNumer_2")
                .description("Product one")
                .drittesKind("Ja")
                .unfall("COD_ausgeschlossen_HEL")
                .build();
        productRepository.save(productTwo);
        log.info("Product-Two - id: " + productTwo.getId());

        List<Product> products = new ArrayList();
        products.add(productOne);
        products.add(productTwo);

        Customer customerOne = Customer.newBuilder().firstName("Foo").lastName("Bar").products(products).build();
        customerRepository.save(customerOne);
        log.info("Person-One - id: " + customerOne.getId());

        Customer customerTwo = Customer.newBuilder().firstName("Mr").lastName("X").build();
        Product productThree = Product.newBuilder()
                .productNumber("ProductNumer_3")
                .description("Product one")
                .drittesKind("Ja")
                .unfall("COD_ausgeschlossen_HEL")
                .build();
        productRepository.save(productThree);
        customerTwo.addProduct(productThree);
        customerRepository.save(customerTwo);
        log.info("Person-Two - id: " + customerTwo.getId());


        Customer customerThree = Customer.newBuilder().firstName("Miss").lastName("Y").build();
        customerThree.addProduct(productOne);
        customerThree.addProduct(productThree);
        customerRepository.save(customerThree);

        log.info("Person-Three - id: " + customerThree.getId());





    }
}
