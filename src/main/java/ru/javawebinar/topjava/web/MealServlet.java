package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealRepository repository = new InMemoryMealRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        switch (command == null ? "" : command) {
            case "upd":
                forwardToMealsAddEdit(repository.getById(Long.parseLong(req.getParameter("id"))).get(), req, resp);
                break;
            case "add":
                forwardToMealsAddEdit(new Meal(LocalDateTime.now().withNano(0), "", 100), req, resp);
                break;
            case "del":
                repository.deleteById(Long.parseLong(req.getParameter("id")));
                redirectToMeals(resp);
                break;
            default:
                forwardToMeals(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        repository.save(extractMeal(req));
        redirectToMeals(resp);
    }

    private void redirectToMeals(HttpServletResponse resp) throws IOException {
        log.debug("redirecting to meals");
        resp.sendRedirect("./meals");
    }

    private void forwardToMealsAddEdit(Meal meal, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forwarding to mealsEditAdd.jsp");
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("./mealsEditAdd.jsp").forward(req, resp);
    }

    private void forwardToMeals(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forwarding to meals.jsp");
        req.setAttribute("meals", MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("./meals.jsp").forward(req, resp);
    }

    private Meal extractMeal(HttpServletRequest req) {
        String description = req.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");
        return id.isEmpty() ? new Meal(dateTime, description, calories)
                : new Meal(Long.parseLong(id), dateTime, description, calories);
    }
}
