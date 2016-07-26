package ch.keepcalm.web.component.price.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ProductListResource extends ResourceSupport {

    @JsonUnwrapped
    private List<ProductResource> productResources;

    public List<ProductResource> getProductResources() {
        return productResources;
    }

    public void setProductResources(List<ProductResource> productResources) {
        this.productResources = productResources;
    }
}
