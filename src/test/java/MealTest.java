import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MealTest extends AuthTest {

    @Test
    public void showAllMealsShouldReturn2Items() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");
        String jsonString = "{\"date\" : \"1996-01-01\"}";

        mockMvc.perform(post("/meals/showAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(post("/meals/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        mockMvc.perform(post("/meals/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        mockMvc.perform(post("/meals/showAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void showAllMealsWithWrongDateShouldReturn0Items() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString = "{\"date\" : \"3000-01-01\"}";

        mockMvc.perform(post("/meals/showAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createMealShouldIncreaseAmountReturnedByShowAll() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");
        String jsonString = "{\"date\" : \"2000-01-01\"}";

        mockMvc.perform(post("/meals/showAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(post("/meals/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        mockMvc.perform(post("/meals/showAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getFoodWithCorrectIdShouldReturnCorrectObject() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString = "{\"foodId\" : \"1\"}";

        mockMvc.perform(post("/meals/getFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("food1")));
    }

    @Test
    public void getFoodWhichIsNotYoursShouldReturnException() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString = "{\"foodId\" : \"3\"}";

        mockMvc.perform(post("/meals/getFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addFoodShouldBePlacedInsideTheMeal() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString1 = "{\"name\"      :\"xyz\"," +
                             "\"mealId\"    :\"1\"," +
                             "\"kcal\"      :\"1800\"," +
                             "\"protein\"   :\"50\"," +
                             "\"fat\"       :\"28\"," +
                             "\"carbohydrate\"      :\"94\"}";

        mockMvc.perform(post("/meals/addFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String jsonString2 = "{\"mealId\" : \"1\"}";

        ResultActions result =
                mockMvc.perform(post("/meals/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JSONObject jsonObject = new JSONObject(resultString);
        assertTrue(jsonObject.toString().contains("\"xyz\""));
    }

    @Test
    public void addFoodWithWrongMealIdShouldReturnException() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString = "{\"name\"      :\"burger\"," +
                                "\"mealId\"    :\"12\"," +
                                "\"kcal\"      :\"1800\"," +
                                "\"protein\"   :\"50\"," +
                                "\"fat\"       :\"28\"," +
                                "\"carbohydrate\"      :\"94\"}";

        mockMvc.perform(post("/meals/addFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addFoodWithMealWhichIsNotYoursShouldReturnException() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString = "{\"name\"      :\"burger\"," +
                                "\"mealId\"    :\"2\"," +
                                "\"kcal\"      :\"1800\"," +
                                "\"protein\"   :\"50\"," +
                                "\"fat\"       :\"28\"," +
                                "\"carbohydrate\"      :\"94\"}";

        mockMvc.perform(post("/meals/addFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addFoodShouldIncreaseMacroInsideTheMeal() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString1 = "{\"mealId\"     :\"1\"}";

        ResultActions result =
                mockMvc.perform(post("/meals/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String resultString1 = result.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString1);
        double kcalBeforeAdding = jsonObject.getDouble("kcal");

        String jsonString2 = "{\"name\"            :\"juice\"," +
                                "\"mealId\"        :\"1\"," +
                                "\"kcal\"          :\"50\"," +
                                "\"protein\"       :\"1\"," +
                                "\"fat\"           :\"1\"," +
                                "\"carbohydrate\"  :\"1\"}";

        mockMvc.perform(post("/meals/addFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        ResultActions result2 = mockMvc.perform(post("/meals/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String resultString2 = result2.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject2 = new JSONObject(resultString2);
        double kcalAfterAdding = jsonObject2.getDouble("kcal");

        assertEquals(kcalBeforeAdding + 50.0, kcalAfterAdding);
    }

    @Test
    public void removeFoodShouldMakeGetFoodReturnIsNotFound() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString1 = "{\"foodId\" :\"1000\"}";

        mockMvc.perform(post("/meals/removeFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String jsonString2 = "{\"foodId\" : \"1000\"}";

        mockMvc.perform(post("/meals/getFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeFoodWhichIsNotYoursShouldReturnUnauthorized() throws Exception{
        String accessToken = obtainAccessToken("testuser2", "testuser2");

        String jsonString = "{\"foodId\" :\"1\"}";

        mockMvc.perform(post("/meals/removeFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void removeFoodShouldDecreaseMacroInMeal() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString1 = "{\"mealId\"     :\"4\"}";

        ResultActions result =
                mockMvc.perform(post("/meals/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString1)
                        .header("Authorization", "Bearer " + accessToken))
                        .andExpect(status().isOk());

        String resultString1 = result.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString1);
        double kcalBeforeRemoving = jsonObject.getDouble("kcal");

        String jsonString2 = "{\"foodId\" :\"1001\"}";

        mockMvc.perform(post("/meals/removeFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        ResultActions result2 = mockMvc.perform(post("/meals/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String resultString2 = result2.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject2 = new JSONObject(resultString2);
        double kcalAfterRemoving = jsonObject2.getDouble("kcal");

        assertEquals(kcalBeforeRemoving - 100.0, kcalAfterRemoving);
    }

    @Test
    public void updateFoodShouldBeNoticedThenByGetFood() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString1 = "{\"name\"      :\"apple\"," +
                "\"foodId\"    :\"2\"," +
                "\"kcal\"      :\"50\"," +
                "\"protein\"   :\"0\"," +
                "\"fat\"       :\"0\"," +
                "\"carbohydrate\"      :\"12.5\"}";

        mockMvc.perform(post("/meals/updateFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String jsonString2 = "{\"foodId\" : \"2\"}";

        mockMvc.perform(post("/meals/getFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kcal", is(50.0)));
    }

    @Test
    public void updateFoodWhichIsNotYoursShouldReturnUnauthorized() throws Exception{
        String accessToken = obtainAccessToken("testuser", "testuser");

        String jsonString = "{\"name\"      :\"apple\"," +
                "\"foodId\"    :\"3\"," +
                "\"kcal\"      :\"50\"," +
                "\"protein\"   :\"0\"," +
                "\"fat\"       :\"0\"," +
                "\"carbohydrate\"      :\"12.5\"}";

        mockMvc.perform(post("/meals/updateFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateFoodShouldUpdateMacroInsideTheMeal() throws Exception{
        String accessToken = obtainAccessToken("testuser2", "testuser2");

        String jsonString1 = "{\"mealId\"     :\"2\"}";

        ResultActions result =
                mockMvc.perform(post("/meals/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString1)
                        .header("Authorization", "Bearer " + accessToken))
                        .andExpect(status().isOk());

        String resultString1 = result.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString1);
        double kcalBeforeUpdating = jsonObject.getDouble("kcal");

        String jsonString2 = "{\"name\"            :\"food3_corrected\"," +
                                "\"foodId\"        :\"3\"," +
                                "\"kcal\"          :\"200\"," +
                                "\"protein\"       :\"10\"," +
                                "\"fat\"           :\"10\"," +
                                "\"carbohydrate\"  :\"10\"}";

        mockMvc.perform(post("/meals/updateFood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString2)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        ResultActions result2 = mockMvc.perform(post("/meals/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString1)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        String resultString2 = result2.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject2 = new JSONObject(resultString2);
        double kcalAfterUpdating = jsonObject2.getDouble("kcal");

        assertNotEquals(kcalAfterUpdating,kcalBeforeUpdating);
    }




}
