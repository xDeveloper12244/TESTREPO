package com.example.joaos.ementas;

/**
 * Created by joaos on 03/10/2017.
 */

public class Meal {

    private int courseOrder;
    private String foodOptionDescription;

    public int getMealCourseOrder() {
        return courseOrder;
    }

    public String getFoodOptionDescription() {
        return foodOptionDescription;
    }

    public Meal(int mealCourseOrder, String foodOption) {
        this.courseOrder = mealCourseOrder;
        this.foodOptionDescription = foodOption;
    }
}
