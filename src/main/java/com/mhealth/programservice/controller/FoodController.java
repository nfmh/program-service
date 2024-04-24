package com.mhealth.programservice.controller;
import com.mhealth.programservice.entity.Food;
import com.mhealth.programservice.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/program/food")
public class FoodController {

    private final ProgramService programService;

    public FoodController(ProgramService programService) {
        this.programService = programService;
    }


    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long foodId) {
        Food food = programService.getFoodById(foodId);
        return food != null ? ResponseEntity.ok(food) : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Food>> getAllFood() {
        List<Food> food = programService.getAllFood();
        return ResponseEntity.ok(food);
    }
}