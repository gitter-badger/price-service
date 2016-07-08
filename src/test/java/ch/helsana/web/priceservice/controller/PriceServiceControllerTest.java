package ch.helsana.web.priceservice.controller;

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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hkesq on 08.07.2016.
 */
@Ignore
@ActiveProfiles(profiles = {"junit"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceServiceController.class)
@WebAppConfiguration
public class PriceServiceControllerTest {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation(
            "target/generated-snippets"
    );

    @Autowired
    private WebApplicationContext context;

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

    @Test
    public void price() throws Exception {
       /* createRequestSamplePerson("George", "King");
        createSamplePerson("Mary", "Queen");

        this.document.snippets(
                responseFields(
                        fieldWithPath("[].id").description("The persons' ID"),
                        fieldWithPath("[].firstName").description("The persons' first name"),
                        fieldWithPath("[].lastName").description("The persons' last name")
                )
        );

        this.mockMvc.perform(
                get("/people").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());*/
    }
}