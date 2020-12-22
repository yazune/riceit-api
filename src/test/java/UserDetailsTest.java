import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserDetailsTest extends AuthTest{

    @Test
    public void getUserDetailsShouldReturnCorrectObject() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        mockMvc.perform(get("/user/getDetails")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is(25)));
    }

    @Test
    public void updateUserDetailsShouldChangeValuesInThem() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        ResultActions result1 =
                mockMvc.perform(get("/user/getDetails")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String resultString1 = result1.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject1 = new JSONObject(resultString1);
        double weightBeforeUpdate = jsonObject1.getDouble("weight");

        String jsonString = "{\"weight\"      :\"50\"," +
                                "\"height\"    :\"170\"," +
                                "\"k\"         :\"1.0\"," +
                                "\"gender\"    :\"MALE\"," +
                                "\"age\"      :\"80\"}";

        mockMvc.perform(post("/user/updateDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        ResultActions result2 =
                mockMvc.perform(get("/user/getDetails")
                        .header("Authorization", "Bearer " + accessToken))
                        .andExpect(status().isOk());

        String resultString2 = result2.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject2 = new JSONObject(resultString2);
        double weightAfterUpdate = jsonObject2.getDouble("weight");


        assertNotEquals(weightBeforeUpdate, weightAfterUpdate);
    }

}
