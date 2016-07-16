package ch.keepcalm.web.component.price.customer;

import ch.keepcalm.web.component.price.PriceServiceApplication;
import ch.keepcalm.web.component.price.model.Address;
import ch.keepcalm.web.component.price.model.Customer;
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
}
