package ch.keepcalm.web.component.price.customer;

import ch.keepcalm.web.component.price.PriceServiceApplication;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PersonControllerTestDocumentation {
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
                        .withHost("rest-docs.scapp.io")
                        .withPort(443))
                .alwaysDo(this.document)
                .build();
    }

    @Ignore
    @Test
    public void listPerson() throws Exception {
        createSampleCustomer("George", "King");
        createSampleCustomer("Mary", "Queen");

        this.document.snippets(
                responseFields(
                        fieldWithPath("[].personId").description("The persons' ID"),
                        fieldWithPath("[].links").description("The persons self resource"),
                        fieldWithPath("[].firstName").description("The persons' first name"),
                        fieldWithPath("[].lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                get("/api/customers").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void getPerson() throws Exception {
        Customer samplePerson = createSampleCustomer("Henry", "King");

        this.document.snippets(
                responseFields(
                        fieldWithPath("id").description("The person's ID"),
                        fieldWithPath("firstName").description("The persons' first name"),
                        fieldWithPath("lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                get("/api/person" + samplePerson.getId()).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void createPerson() throws Exception {
        Map<String, String> newPerson = new HashMap();
        newPerson.put("firstName", "Anne");
        newPerson.put("lastName", "Queen");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);

        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The persons' first name"),
                        fields.withPath("lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                post("api/person").contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(newPerson)
                )
        ).andExpect(status().isCreated());
    }

    @Ignore
    @Test
    public void updatePerson() throws Exception {
        Customer originalPerson = createSampleCustomer("Victoria", "Queen");
        Map<String, String> updatedPerson = new HashMap();
        updatedPerson.put("firstName", "Edward");
        updatedPerson.put("lastName", "King");

        ConstrainedFields fields = new ConstrainedFields(Customer.class);

        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The persons' first name"),
                        fields.withPath("lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                put("api/person" + originalPerson.getId()).contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(updatedPerson)
                )
        ).andExpect(status().isNoContent());
    }

    private Customer createSampleCustomer(String firstName, String lastName) {
        return customerRepository.save(new Customer().newBuilder().firstName(firstName).lastName(lastName).build());
    }

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
