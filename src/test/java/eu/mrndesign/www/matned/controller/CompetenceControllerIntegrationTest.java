package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.service.CompetenceService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class CompetenceControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompetenceService competenceService;


}
