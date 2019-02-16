package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> exceeds = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        exceeds.forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDateTime, Integer> callories = new HashMap<>();
        for(UserMeal temp : mealList){
            callories.put(temp.getDateTime(), callories.getOrDefault(temp.getDateTime(),0) + temp.getCalories());
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for(UserMeal temp : mealList){
            if(temp.getDateTime().toLocalTime().isAfter(startTime) && temp.getDateTime().toLocalTime().isBefore(endTime)){
                result.add(new UserMealWithExceed(temp.getDateTime(), temp.getDescription(), temp.getCalories(),
                        callories.get(temp.getDateTime()) > caloriesPerDay));
            }
        }
        // TODO return filtered list with correctly exceeded field
        return result;
    }
}
