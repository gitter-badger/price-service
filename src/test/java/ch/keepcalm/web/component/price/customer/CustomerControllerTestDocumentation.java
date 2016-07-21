package ch.keepcalm.web.component.price.customer;

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
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
                         .withScheme("https")
                         .withHost("price-service.scapp.io")
                         .withPort(443))
                 .alwaysDo(this.document)
                 .build();
     }


    @Test
    public void listCustomerTest() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("list-customers");
        apiListCustomer(document);
    }




    @Test
    public void createCustomerTest() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("create-customer");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);
        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The customers' first name"),
                        fields.withPath("lastName").description("The customers' last name"),
                        fields.withPath("dateOfBirth").description("The customers' birthday"),
                        fields.withPath("gender").description("The customers' gender (w/m)"),
                        fields.withPath("address").description("The customers' address")
                )
        );

        apiCreateCustomer(document, createCustomer("John" , "Doe", "m"));
    }



    @Test
    public void createProductTest() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("create-product");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);
        this.document.snippets(
                requestFields(
                        fields.withPath("productNumber").description("The products' product number"),
                        fields.withPath("description").description("The products' description"),
                        fields.withPath("drittesKind").description("The products' drittes kind (Ja/Nein)"),
                        fields.withPath("unfall").description("The products' unfall (COD_eingeschlossen_HEL7 / COD_ausgeschlossen_HEL)"),
                        fields.withPath("franchise").description("The products' franchise"),
                        fields.withPath("price").description("The products' price"),
                        fields.withPath("doctor").description("The products' doctor object")
                )
        );

        apiCreateProduct(document, createCustomer("Jane" , "Doe", "w"), createProduct());
    }


    private void apiListCustomer(RestDocumentationResultHandler document) throws Exception {
        this.mockMvc.perform(get("/api/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }

    private void apiCreateCustomer(RestDocumentationResultHandler document, Customer newCustomer) throws Exception {
        this.mockMvc.perform(post("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document);
    }

    private void apiCreateProduct(RestDocumentationResultHandler document, Customer newCustomer, Product product) throws Exception {
        this.mockMvc.perform(post("/api/customers/" + newCustomer.getId() + "/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document);
    }


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
    private Product createProduct(){
        return Product.newBuilder()
                .productNumber("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .franchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                .build();

    }


    @Test
    public void listProductsTest() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("list-products");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);
        this.document.snippets(
                requestFields(
                        fields.withPath("productNumber").description("The products' product number"),
                        fields.withPath("description").description("The products' description"),
                        fields.withPath("drittesKind").description("The products' drittes kind (Ja/Nein)"),
                        fields.withPath("unfall").description("The products' unfall (COD_eingeschlossen_HEL7 / COD_ausgeschlossen_HEL)"),
                        fields.withPath("franchise").description("The products' franchise"),
                        fields.withPath("price").description("The products' price"),
                        fields.withPath("doctor").description("The products' doctor object")
                )
        );

        // create a product
        Product product = Product.newBuilder()
                .productNumber("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .franchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                .build();

        // first create one customer with one product
        Customer customer = createCustomer("Foo", "Bar", "w");
        customer.addProduct(product);
        Customer newCustomer = customerRepository.save(customer);



        this.mockMvc.perform(get("/api/customers/" + newCustomer.getId() + "/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }



    @Test
    public void updateProductPriceTest() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("update-product-price");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);
        this.document.snippets(
                requestFields(
                        fields.withPath("productNumber").description("The products' product number"),
                        fields.withPath("description").description("The products' description"),
                        fields.withPath("drittesKind").description("The products' drittes kind (Ja/Nein)"),
                        fields.withPath("unfall").description("The products' unfall (COD_eingeschlossen_HEL7 / COD_ausgeschlossen_HEL)"),
                        fields.withPath("franchise").description("The products' franchise"),
                        fields.withPath("price").description("The products' price"),
                        fields.withPath("doctor").description("The products' doctor object")
                )
        );

        // create a product
        Product product = Product.newBuilder()
                .productNumber("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .franchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                .build();

        // first create one customer with one product
        Customer customer = createCustomer("Foo", "Bar", "w");
        customer.addProduct(product);
        Customer newCustomer = customerRepository.save(customer);



        this.mockMvc.perform(patch("/api/customers/" + newCustomer.getId() + "/products/" + product.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
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
}
