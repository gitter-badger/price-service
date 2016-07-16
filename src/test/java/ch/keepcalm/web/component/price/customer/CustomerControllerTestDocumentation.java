package ch.keepcalm.web.component.price.customer;

import ch.keepcalm.web.component.price.PriceServiceApplication;
import ch.keepcalm.web.component.price.model.Address;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    /* @Before
     public void setUp() {
         this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
         this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                 .apply(documentationConfiguration(this.restDocumentation).uris()
                         .withScheme("https")
                         .withHost("rest-docs.scapp.io")
                         .withPort(443))
                 .alwaysDo(this.document)
                 .build();
     }*/
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }


    @Test
    public void listCustomer() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("list-customer");
        this.mockMvc.perform(get("/api/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }

    @Test
    public void createCustomer() throws Exception {
        RestDocumentationResultHandler document = documentPrettyPrintReqResp("create-customer");
        Customer newCustomer = Customer.newBuilder()
                .firstName("Foo")
                .lastName("Bar")
                .dateOfBirth(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDateTime().toDate())
                .address(Address.newAddress()
                        .locality("Gockhausen")
                        .municipality("Dübendorf")
                        .postal_code("8044")
                        .municipality_nr("191")
                        .build())
                .build();

        this.mockMvc.perform(post("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
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


/*


    {
        "customerResourceList" : [ {
        "firstName" : "Many",
                "lastName" : "Mayer",
                "dateOfBirth" : "1975-09-26T23:00:00.000+0000",
                "gender" : null,
                "address" : {
            "street" : null,
                    "municipality" : "Dübendorf",
                    "municipality_nr" : "191",
                    "postal_code" : "8044",
                    "locality" : "Gockhausen"
        },
        "_links" : {
            "self" : {
                "href" : "http://localhost:8080/api/customers/1"
            }
        }
    } ],
        "_links" : {
        "self" : {
            "href" : "http://localhost:8080/api/customers"
        }
    }
    }

*/


    @Ignore
    @Test
    public void listCustomers() throws Exception {
        createSampleCustomer("George", "King");
        createSampleCustomer("Mary", "Queen");

        this.document.snippets(
                responseFields(
                        fieldWithPath("[].id").description("The customers' ID"),
                        fieldWithPath("[].links").description("The customer self resource"),
                        fieldWithPath("[].firstName").description("The customers' first name"),
                        fieldWithPath("[].lastName").description("The customers' last name")
                )
        );

        this.mockMvc.perform(
                get("/api/customers")
                        .accept(
                                MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


    @Ignore
    @Test
    public void getCustomers() throws Exception {
        Customer sampleCustomer = createSampleCustomer("Henry", "King");

        this.document.snippets(
                responseFields(
                        fieldWithPath("id").description("The person's ID"),
                        fieldWithPath("firstName").description("The persons' first name"),
                        fieldWithPath("lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                get("/api/customers" + sampleCustomer.getId()).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void createCustomers() throws Exception {
        Map<String, String> newCustomer = new HashMap();
        newCustomer.put("firstName", "Anne");
        newCustomer.put("lastName", "Queen");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);

        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The persons' first name"),
                        fields.withPath("lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                post("api/customers").contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(newCustomer)
                )
        ).andExpect(status().isCreated());
    }

    @Ignore
    @Test
    public void updateCustomers() throws Exception {
        Customer originalCustomers = createSampleCustomer("Victoria", "Queen");
        Map<String, String> updatedCustomer = new HashMap();
        updatedCustomer.put("firstName", "Edward");
        updatedCustomer.put("lastName", "King");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);

        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The persons' first name"),
                        fields.withPath("lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                put("api/customers" + originalCustomers.getId()).contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(updatedCustomer)
                )
        ).andExpect(status().isNoContent());
    }


    /**
     * @param firstName
     * @param lastName
     * @return
     */
    private Customer createSampleCustomer(String firstName, String lastName) {
        return customerRepository.save(new Customer().newBuilder().firstName(firstName).lastName(lastName).build());
    }

    /**
     *
     */
    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
