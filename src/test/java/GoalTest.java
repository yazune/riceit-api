import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GoalTest extends AuthTest {

    @Test
    public void getGoalShouldReturnCorrectObject() throws Exception{

        String accessToken = obtainAccessToken("testuser", "testuser");

        mockMvc.perform(get("/user/getGoal")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autoKcal", is(2763.0)));

    }

}
