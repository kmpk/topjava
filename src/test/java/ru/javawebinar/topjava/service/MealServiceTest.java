package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(FIRST_MEAL_ID, USER_ID);
        assertMatch(actual, MEAL1);

        actual = service.get(FIRST_ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotOwner() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, ADMIN_ID));

        Assert.assertThrows(NotFoundException.class, () -> service.get(FIRST_ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(FIRST_MEAL_ID, USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, USER_ID));

        service.delete(FIRST_ADMIN_MEAL_ID, ADMIN_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(FIRST_ADMIN_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotOwner() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(FIRST_MEAL_ID, ADMIN_ID));
        assertMatch(service.get(FIRST_MEAL_ID, USER_ID), MEAL1);

        Assert.assertThrows(NotFoundException.class, () -> service.delete(FIRST_ADMIN_MEAL_ID, USER_ID));
        assertMatch(service.get(FIRST_ADMIN_MEAL_ID, ADMIN_ID), ADMIN_MEAL1);
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> result = service.getBetweenInclusive(MEAL7.getDate(), MEAL7.getDate().plusMonths(1), USER_ID);
        assertMatch(result, MEAL7, MEAL6, MEAL5, MEAL4);

        result = service.getBetweenInclusive(ADMIN_MEAL1.getDate(), ADMIN_MEAL1.getDate().plusMonths(1), ADMIN_ID);
        assertMatch(result, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> result = service.getAll(USER_ID);
        assertMatch(result, MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

        result = service.getAll(ADMIN_ID);
        assertMatch(result, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    public void update() {
        Meal toUpdate = getUpdated();
        service.update(toUpdate, USER_ID);
        assertMatch(service.get(toUpdate.getId(), USER_ID), getUpdated());

        toUpdate = getUpdatedAdmin();
        service.update(toUpdate, ADMIN_ID);
        assertMatch(service.get(toUpdate.getId(), ADMIN_ID), getUpdatedAdmin());
    }

    @Test
    public void updateNotOwner() {
        Assert.assertThrows(NotFoundException.class, () -> service.update(getUpdatedAdmin(), USER_ID));
        assertMatch(service.get(getUpdatedAdmin().getId(), ADMIN_ID), ADMIN_MEAL1);

        Assert.assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
        assertMatch(service.get(getUpdated().getId(), USER_ID), MEAL1);
    }

    @Test
    public void updateNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            Meal updated = getUpdated();
            updated.setId(NOT_FOUND);
            service.update(updated, USER_ID);
        });
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(service.get(created.getId(), USER_ID), newMeal);

        newMeal = getNewAdmin();
        created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(service.get(created.getId(), ADMIN_ID), newMeal);
    }
}