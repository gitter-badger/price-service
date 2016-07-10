package ch.helsana.web.priceservice.bootstrap;


import ch.helsana.web.priceservice.model.Product;
import ch.helsana.web.priceservice.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


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

    private Logger log = Logger.getLogger(ProductLoader.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Product shirt = Product.newBuilder()
                .productId("235268845711068308")
                .description("Spring Framework Shirt")
                .price(new BigDecimal("18.95"))
                .imageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg")
                .build();
        productRepository.save(shirt);
        log.info("Saved Shirt - id: " + shirt.getId());

        Product mug = Product.newBuilder()
                .productId("168639393495335947")
                .description("Spring Framework Shirt Mug")
                .price(new BigDecimal("11.95"))
                .imageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg")
                .build();
        productRepository.save(mug);
        log.info("Saved Mug - id:" + mug.getId());
    }
}
