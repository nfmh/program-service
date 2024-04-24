package com.mhealth.programservice.entity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "exercise")
@Data
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "calories_burned", nullable = false)
    private int caloriesBurned;
}


