package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.PriceServiceApplication;
import ch.keepcalm.web.component.price.model.Address;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by stevenheyninck on 29/10/15.
 */
@ActiveProfiles(profiles = {"junit"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceServiceApplication.class)
@WebAppConfiguration
public class CustomerControllerTestDocumentation {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation(
            "target/generated-snippets"
    );

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Before
     public void setUp() {
         this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
         this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                 .apply(documentationConfiguration(this.restDocumentation).uris()
                         .withScheme("http")
                         .withHost("http://msswlmp01.ads.hel.kko.ch/")
                         .withPort(80))
                 .alwaysDo(this.document)
                 .build();
     }

    /**
     *
     * @param fields
     * @return
     */
    private Snippet getSnippetCustomer(ConstrainedFields fields) {
        return requestFields(
                fields.withPath("firstName").description("The customers' first name"),
                fields.withPath("lastName").description("The customers' last name"),
                fields.withPath("dateOfBirth").description("The customers' birthday"),
                fields.withPath("gender").description("The customers' gender (w/m)"),
                fields.withPath("address").description("The customers' address")
        );
    }
    /**
     *
     * @param fields
     * @return
     */
    private Snippet getSnippetProduct(ConstrainedFields fields) {
        return requestFields(
                fields.withPath("productNumber").description("The products' product number"),
                fields.withPath("description").description("The products' description"),
                fields.withPath("drittesKind").description("The products' drittes kind (Ja/Nein)"),
                fields.withPath("unfall").description("The products' unfall (COD_eingeschlossen_HEL7 / COD_ausgeschlossen_HEL)"),
                fields.withPath("franchise").description("The products' franchise"),
                fields.withPath("price").description("The products' price"),
                fields.withPath("doctor").description("The products' doctor object")
        );
    }

    /**
     * Test list all customer resources.
     * @throws Exception
     */
    @Test
    public void listCustomer() throws Exception {
        // setup db
        Customer customer1 = createCustomer("Foo", "Bar", "w"); // create a customer
        customer1.addProduct(createDummyProduct()); // add a product to a customer
        customerRepository.save(customer1); // save a customer with product.
        Customer customer2 = createCustomer("Jone", "Doe", "m");
        customerRepository.save(customer2);

        // call API
        documentApiListCustomers("list-customers");

        // reset db
        customerRepository.delete(customer1);
        customerRepository.delete(customer2);


    }


    /**
     * Test crate a customre resource.
     * @throws Exception
     */
    @Test
    public void createCustomer() throws Exception {
        documentApiCreateCustomer("create-customer", createCustomer("John" , "Doe", "m"));
    }

    /**
     * Test create a product resource.
     * @throws Exception
     */
    @Test
    public void createProduct() throws Exception {
        // setup db
        Customer customer = createCustomer("Foo", "Bar", "w"); // create a customer
        customer.addProduct(createDummyProduct()); // add a product to a customer
        customerRepository.save(customer); // save a customer with product.

        // call API
        documentApiCreateProduct("create-product", customer, createDummyProduct());

        // reset db
        customerRepository.delete(customer);
    }

    /**
     * Test update a product resource with a price.
     * @throws Exception
     */
    @Test
    public void updateProductPrice() throws Exception {
        // setup db
        Customer customer = createCustomer("Foo", "Bar", "w"); // create a customer
        customer.addProduct(createDummyProduct()); // add a product to a customer
        Customer newCustomer = customerRepository.save(customer); // save a customer with product.

        // call API
        documentApiUpdateProductPrice("update-product-price",newCustomer, newCustomer.getProducts().get(0)); // get the first product

        // reset db
        customerRepository.delete(customer);
    }

    /**
     * Pretty print request and response
     *
     * @param useCase the name of the snippet
     * @return RestDocumentationResultHandler
     */
    private RestDocumentationResultHandler documentPrettyPrintReqResp(String useCase) {
        return document(useCase,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
    }



    /**
     *
     * @param useCase
     * @throws Exception
     */
    private void documentApiListCustomers(String useCase) throws Exception {
        this.mockMvc.perform(get("/api/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andDo(document);
    }

     /**
     *
     * @param useCase
     * @param newCustomer
     * @throws Exception
     */
    private void documentApiCreateCustomer(String useCase, Customer newCustomer) throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp(useCase);
        ConstrainedFields fields = new ConstrainedFields(Customer.class);
        this.document.snippets(
                getSnippetCustomer(fields)
        );
        this.mockMvc.perform(post("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document);
    }


    /**
     *
     * @param useCase
     * @param customer
     * @param product
     * @throws Exception
     */
    private void documentApiCreateProduct(String useCase, Customer customer, Product product) throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp(useCase);
        ConstrainedFields fields = new ConstrainedFields(Product.class);
        this.document.snippets(
                getSnippetProduct(fields)
        );
        this.mockMvc.perform(post("/api/customers/" + customer.getId() + "/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document);
    }


    /**
     *
     * @param useCase
     * @param customer
     * @param product
     * @throws Exception
     */
    private void documentApiUpdateProductPrice(String useCase, Customer customer, Product product) throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp(useCase);
        ConstrainedFields fields = new ConstrainedFields(Product.class);
        this.document.snippets(
                getSnippetProduct(fields)
        );

        this.mockMvc.perform(patch("/api/customers/" + customer.getId() + "/products/" + product.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param gender
     * @return a customer object
     */
    private Customer createCustomer(String firstName, String lastName, String gender) {
        return Customer.newBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .dateOfBirth(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDateTime().toDate())
                .address(Address.newAddress()
                        .locality("Gockhausen")
                        .municipality("Dübendorf")
                        .postal_code("8044")
                        .municipality_nr("191")
                        .postal_code_addition("00")
                        .build())
                .build();
    }

    /**
     *
     * @return a dummy product
     */
    private Product createDummyProduct(){
        return Product.newBuilder()
                .productNumber("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .franchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                .build();

    }

    /**
     * ConstrainedFields class
     */
    class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        public FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}

