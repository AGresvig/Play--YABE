package controllers;

import models.Category;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import views.html.categories;

import java.util.List;

import static play.mvc.Results.ok;

/**
 * Controller class for the {@link models.Category} object.
 * Exposes RESTful CRUD services.
 */
public class Categories {

    /**
     * Gets list of all categories in the database
     *  @return
     */
    public static Result retrieveAll() {
        Logger.debug("Getting list of Categories");

        List<Category> categories = Category.find.all();
        return ok(Json.toJson(categories)).as("application/json");
    }

    public static Result showAll() {
        Logger.debug("Showing all Categories");

        List<Category> catList = Category.find.all();
        return ok(categories.render(catList));
    }

    public static Result retrieve(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Result delete(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Result persist() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Result update() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
