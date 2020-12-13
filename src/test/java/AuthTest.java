import com.agh.riceitapi.RiceitApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = RiceitApiApplication.class)
public class AuthTest {

    @Autowired
    protected MockMvc mockMvc;

    protected String obtainAccessToken(String usernameOrEmail, String password) throws Exception {

        String jsonString = "{\"usernameOrEmail\" : \"" + usernameOrEmail + "\",\"password\" : \"" + password + "\"}";

        ResultActions result
                = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }

    @Test
    public void givenNoTokenShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/test/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenTokenShouldReturnOk() throws Exception {
        String accessToken = obtainAccessToken("testuser2", "testuser2");

        mockMvc.perform(get("/test/hello")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }
}
