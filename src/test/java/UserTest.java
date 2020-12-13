import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends AuthTest {

    @Test
    public void createNewUser() throws Exception{
        // user = cracow123
        // password = warsaw123
        String jsonString = "{\"username\"  :\"cracow123\"," +
                "\"password\"  :\"warsaw123\"," +
                "\"email\"     :\"wroclaw@gmail.com\"}";

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("wroclaw@gmail.com")));
    }

    @Test
    public void createNewUserWithUsernameAlreadyTaken() throws Exception{

        String jsonString = "{\"username\"  :\"testuser\"," +
                "\"password\"  :\"differentpassword\"," +
                "\"email\"     :\"differentemail@gmail.com\"}";

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createNewUserWithEmailAlreadyTaken() throws Exception{
        String jsonString =     "{\"username\"  :\"differentpassword\"," +
                "\"password\"  :\"differentpassword\"," +
                "\"email\"     :\"testuser@gmail.com\"}";

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createNewUserWithUsernameOrPasswordTooShort() throws Exception{
        String jsonString1 = "{\"username\"  :\"a\"," +
                "\"password\"  :\"longpassword\"," +
                "\"email\"     :\"jack1@gmail.com\"}";

        String jsonString2 = "{\"username\"  :\"longusername\"," +
                "\"password\"  :\"pass\"," +
                "\"email\"     :\"jack2@gmail.com\"}";

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2))
                .andExpect(status().isBadRequest());
    }
}
