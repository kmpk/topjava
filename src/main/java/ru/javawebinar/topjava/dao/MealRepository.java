package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealRepository {
    Optional<Meal> getById(long id);

    List<Meal> getAll();

    void save(Meal meal);

    boolean deleteById(long id);
}
