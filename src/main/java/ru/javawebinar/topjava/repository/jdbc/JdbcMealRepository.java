package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("userId", userId)
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            meal.setId(insertMeal.executeAndReturnKey(map).intValue());
        } else if (update(map) == 0) {
            return null;
        }
        return meal;
    }

    private int update(MapSqlParameterSource map) {
        return namedParameterJdbcTemplate.update("" +
                "UPDATE meals " +
                "SET date_time=:dateTime, description=:description, calories=:calories " +
                "WHERE id=:id AND user_id=:userId", map);
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("" +
                "DELETE " +
                "FROM meals " +
                "WHERE id=? AND user_id=?", id, userId) == 1;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> result = jdbcTemplate.query("" +
                "SELECT * " +
                "FROM meals " +
                "WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return result.size() == 1 ? result.get(0) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("" +
                "SELECT * " +
                "FROM meals " +
                "WHERE user_id=? " +
                "ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("" +
                "SELECT * " +
                "FROM meals " +
                "WHERE user_id=? AND date_time BETWEEN ? AND ? " +
                "ORDER BY date_time DESC", ROW_MAPPER, userId, startDate, endDate);
    }
}
