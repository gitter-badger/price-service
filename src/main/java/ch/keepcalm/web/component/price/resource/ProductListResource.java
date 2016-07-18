package ch.keepcalm.web.component.price.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ProductListResource extends ResourceSupport {

    @JsonUnwrapped
    private List<ProductResource> productResourceList;

    public List<ProductResource> getProductResourceList() {
        return productResourceList;
    }

    public void setProductResourceList(List<ProductResource> productResourceList) {
        this.productResourceList = productResourceList;
    }
}
