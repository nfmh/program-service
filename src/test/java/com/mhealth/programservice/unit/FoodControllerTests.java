package com.mhealth.programservice.unit;
import com.mhealth.programservice.controller.FoodController;
import com.mhealth.programservice.entity.Food;
import com.mhealth.programservice.service.ProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class FoodControllerTests {

    @Mock
    private ProgramService programService;

    @InjectMocks
    private FoodController foodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFoodById() {
        Long foodId = 1L;
        Food food = new Food();
        food.setId(foodId);

        when(programService.getFoodById(foodId)).thenReturn(food);

        ResponseEntity<Food> response = foodController.getFoodById(foodId);

        assertEquals(food, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllFood() {
        List<Food> foods = Collections.singletonList(new Food());

        when(programService.getAllFood()).thenReturn(foods);

        ResponseEntity<List<Food>> response = foodController.getAllFood();

        assertEquals(foods, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
     @Test
      void testGetFoodById_NotFound() {
         long nonExistentFoodId = 999L;

         when(programService.getFoodById(nonExistentFoodId)).thenReturn(null);

         ResponseEntity<Food> responseEntity = foodController.getFoodById(nonExistentFoodId);

         assertEquals(404, responseEntity.getStatusCodeValue());
     }
}
