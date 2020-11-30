package eu.mrndesign.www.matned;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonOpsTest {

    @Test
    void correctJsonFormat(){
        assertEquals("{\"name\":\"na\",\"description\":\"de\"}", JsonOps.asJsonString(new CompetenceDTO("na", "de")));
    }

}
