package ch.helsana.web.priceservice.api.product;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.web.priceservice.exception.BusinessException;
import ch.helsana.web.priceservice.exception.SystemException;
import ch.helsana.web.priceservice.service.PriceService;
import ch.helsana.web.priceservice.service.ProductResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by marcelwidmer on 11/04/16.
 */
@RestController
@RequestMapping("/api/productss")
public class ProductController {

    private ProductResourceService resourceService;
    private PriceService service;


    @Autowired
    public void setProductService(ProductResourceService resourceService, PriceService priceService) {
        this.resourceService = resourceService;
        this.service = priceService;
    }

    /**
     * TODO: 10/07/16 HATEOAS
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public HttpEntity<ProductResource> findOne(@PathVariable("productId") Integer productId) {
        ProductResource resource = resourceService.findOne(productId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * TODO: 10/07/16 HATEOAS
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ProductResource>> showAll() {
        List<ProductResource> resources = resourceService.listAll();
        return new ResponseEntity<List<ProductResource>>(resources, HttpStatus.OK);
    }


    /**
     * https://tools.ietf.org/html/rfc7231#section-8.1.3
     * <p>
     * POST because a new price is created.
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = "/price",
            method = {RequestMethod.POST},
            produces = "application/json; charset=utf-8"
    )
    public ResponseEntity price(@RequestBody BerechnePraemieRequest request) throws Exception {
        try {
            return new ResponseEntity(service.berechnePraemie(request).getPreis(), HttpStatus.ACCEPTED);
        } catch (BerechnePraemieSystemFaultMessage systemFaultMessage) {
            throw new SystemException("System business exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BerechnePraemieBusinessFaultMessage businessFaultMessage) {
            throw new BusinessException("Price business exception : ", HttpStatus.BAD_REQUEST);
        }

    }

   /* @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product updatedProduct, @PathVariable Integer id) {

        Product product = Product.newBuilder()
                .productId(updatedProduct.getProductId())
                .description(updatedProduct.getDescription())
                .imageUrl(updatedProduct.getImageUrl())
                .price(updatedProduct.getPrice())
                .build();

        return productResourceService.saveProduct(product);
    }*/

   /* @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productResourceService.saveProduct(product);
    }*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class})
    public void handleNotFound() {
    }
}
