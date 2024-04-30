package com.mhealth.programservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhealth.programservice.controller.FoodController;
import com.mhealth.programservice.dto.FoodDTO;
import com.mhealth.programservice.entity.Food;
import com.mhealth.programservice.service.ProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
@AutoConfigureMockMvc
 class FoodIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProgramService programService;

    @Test
     void getFoodById_ReturnsFood() throws Exception {
        Food food = new Food();
        food.setId(1L);
        food.setName("Apple");
        food.setDescription("Fresh apple");
        food.setCalories(52);

        when(programService.getFoodById(1L)).thenReturn(food);

        mockMvc.perform(get("/program/food/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.description").value("Fresh apple"))
                .andExpect(jsonPath("$.calories").value(52));
    }


    @Test
     void getAllFoods_ReturnsAllFoods() throws Exception {
        Food food1 = new Food();
        food1.setId(1L);
        food1.setName("Apple");
        food1.setDescription("Fresh apple");
        food1.setCalories(52);

        Food food2 = new Food();
        food2.setId(2L);
        food2.setName("Banana");
        food2.setDescription("Fresh banana");
        food2.setCalories(89);

        List<Food> foodList = Arrays.asList(food1, food2);

        when(programService.getAllFood()).thenReturn(foodList);

        mockMvc.perform(get("/program/food")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].description").value("Fresh apple"))
                .andExpect(jsonPath("$[0].calories").value(52))
                .andExpect(jsonPath("$[1].name").value("Banana"))
                .andExpect(jsonPath("$[1].description").value("Fresh banana"))
                .andExpect(jsonPath("$[1].calories").value(89));
    }

    @Test
     void getNonExistentFood_ReturnsNotFound() throws Exception {
        when(programService.getFoodById(100L)).thenReturn(null);

        mockMvc.perform(get("/program/food/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
