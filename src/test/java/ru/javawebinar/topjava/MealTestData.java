package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int FIRST_MEAL_ID = START_SEQ + 2;
    public static final int FIRST_ADMIN_MEAL_ID = FIRST_MEAL_ID + 7;

    public static final Meal MEAL1 = new Meal(FIRST_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(FIRST_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(FIRST_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(FIRST_MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL5 = new Meal(FIRST_MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL6 = new Meal(FIRST_MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL7 = new Meal(FIRST_MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal ADMIN_MEAL1 = new Meal(FIRST_ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(FIRST_ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 1, 1), "Новый завтрак", 555);
    }

    public static Meal getNewAdmin() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 1, 1), "Новый админ завтрак", 555);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1.getId(), LocalDateTime.of(2000, Month.DECEMBER, 1, 1, 1), "Обновлённое значение", 9999);
    }

    public static Meal getUpdatedAdmin() {
        return new Meal(ADMIN_MEAL1.getId(), LocalDateTime.of(2000, Month.DECEMBER, 1, 1, 1), "Обновлённое значение", 9999);
    }

    public static List<Meal> getAllUser() {
        return Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7);
    }

    public static List<Meal> getAllAdmin() {
        return Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        Assertions.assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
