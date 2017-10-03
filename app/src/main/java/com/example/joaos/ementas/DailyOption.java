package com.example.joaos.ementas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DailyOption {
    static final public int DAILY_MEAL_LUNCH = 1;
    static final public int DAILY_MEAL_DINNER = 2;

    private Date date;
    private String canteenSite;
    private String dailyMeal;
    private boolean available;
    private List<Meal> mealCourseList;


    public DailyOption(Date date, String canteen, String dailyMeal, boolean available) {
        this.canteenSite = canteen;
        this.date = date;
        this.dailyMeal = dailyMeal;
        this.available = available;

        mealCourseList = new ArrayList<>();
    }

    public void addMealCourse(Meal meal) {
        mealCourseList.add(meal);
    }

    public List<Meal> getMealCourseList() {
        return mealCourseList;
    }

    public Date getDate() {
        return date;
    }

    public String getCanteenSite() {
        return canteenSite;
    }

    public String getDailyMeal() {
        return dailyMeal;
    }

    public boolean isAvailable() {
        return available;
    }

}
