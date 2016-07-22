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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
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
 * Created by marcelwidmer on 21/07/16.
 */
@ActiveProfiles(profiles = {"junit"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceServiceApplication.class)
@WebAppConfiguration
public class CustomerAggregateControllerDocumentation {
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
                .apply(documentationConfiguration(this.restDocumentation).uris())
                .alwaysDo(this.document)
                .build();

    }


    @Test
    public void entryPoint()  {
        try {
            this.mockMvc.perform(
                    get("/api").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("entry-point"));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void createCustomer() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("create-customer");
        ConstrainedFields fields = new ConstrainedFields(Customer.class);
        this.document.snippets(
                requestFields(
                        fields.withPath("dateOfBirth").description("The customers' birthday"),
                        fields.withPath("gender").description("The customers' gender"),
                        fields.withPath("address").description("The customers' address")

                )
        );

        Address address = Address.newAddress()
                .locality("Gockhausen")
                .municipality("Dübendorf")
                .municipality_nr("191")
                .postal_code("8044")
                .postal_code_addition("00")
                .build();

        Customer newCustomer = Customer.newBuilder()
                .gender("m")
                .dateOfBirth(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDateTime().toDate())
                .address(address)
                .build();

        // documentation call
        this.mockMvc.perform(
                post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newCustomer))
        ).andExpect(status().isCreated())
                .andDo(document);
    }

    @Test
    public void listCustomers() throws Exception {
        Address address = Address.newAddress()
                .locality("Gockhausen")
                .municipality("Dübendorf")
                .municipality_nr("191")
                .postal_code("8044")
                .postal_code_addition("00")
                .build();

        Customer newCustomer = Customer.newBuilder()
                .id(1)
                .gender("m")
                .dateOfBirth(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDateTime().toDate())
                .address(address)
                .build();

        customerRepository.save(newCustomer);


        // documentation call
        this.mockMvc.perform(
                get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(document);
    }


    @Test
    public void createProduct() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("create-product");
        ConstrainedFields fields = new ConstrainedFields(Product.class);
        this.document.snippets(
                requestFields(
                        fields.withPath("productNumber").description("The producs' product ID"),
                        fields.withPath("description").description("The producs' description"),
                        fields.withPath("unfall").description("The producs' unfall attribute"),
                        fields.withPath("franchise").description("The producs' franchise attribute"),
                        fields.withPath("drittesKind").description("The producs' drittesKind attribute"),
                        fields.withPath("price").description("The producs' price attribute")
                )
        );


        Product product = Product.newBuilder()
                .productNumber("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .franchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                .build();
        List<Product> products = new ArrayList<Product>();
        products.add(product);

        Address address = Address.newAddress()
                .locality("Gockhausen")
                .municipality("Dübendorf")
                .municipality_nr("191")
                .postal_code("8044")
                .postal_code_addition("00")
                .build();

        Customer newCustomer = Customer.newBuilder()
                .id(1)
                .gender("m")
                .dateOfBirth(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDateTime().toDate())
                .address(address)
                .build();

        customerRepository.save(newCustomer);


        // documentation call
        this.mockMvc.perform(
                post("/api/customers/1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(product))
        ).andExpect(status().isCreated())
                .andDo(document);
    }


    @Test
    public void updateProductPrice() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("update-product-price");

        Product product = Product.newBuilder()
                .productNumber("PRO_P0BEPH_HEL_IG")
                .description("Product one")
                .drittesKind("Nein")
                .unfall("COD_ausgeschlossen_HEL")
                .franchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                .build();
        List<Product> products = new ArrayList<Product>();
        products.add(product);

        Address address = Address.newAddress()
                .locality("Gockhausen")
                .municipality("Dübendorf")
                .municipality_nr("191")
                .postal_code("8044")
                .postal_code_addition("00")
                .build();

        Customer newCustomer = Customer.newBuilder()
                .id(1)
                .gender("m")
                .dateOfBirth(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDateTime().toDate())
                .address(address)
                .build();


        customerRepository.save(newCustomer);


        // create a product
       this.mockMvc.perform(
                post("/api/customers/1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(product)
                        )
        ).andExpect(status().isCreated());


        // documentation call
        this.mockMvc.perform(
                patch("/api/customers/1/products/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }


    /**
     * @param useCase
     * @return
     */
    private RestDocumentationResultHandler documentPrettyPrintReqResp(String useCase) {
        return document(useCase,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
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
