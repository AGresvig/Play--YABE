package controllers;

import models.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;

/**
 * Controller class for the {@link models.User} object.
 * Exposes RESTful CRUD services.
 */
public class Users extends Controller {

    /**
     * Retrieves {@link User} from db by email
     * @param email email (unique ID)
     * @return {@link User} object as JSON
     */
    public static Result retrieve(String email) {
        Logger.debug("Getting User with email: " + email);
        User user = User.find.byId(email);
        return ok(Json.toJson(user)).as("application/json");
    }

    /**
     * Creates new {@link User} from request body JSON data
     * and persists to database
     * @return The resulting {@link User} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result persist() {
        JsonNode request = request().body().asJson();
        Logger.info("Saving User from JSON: " + request.asText());

        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        //Attempt to parse JSON
        try {
            user = mapper.readValue(request, User.class);
            user.save();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return badRequest(e.getCause().getMessage());
        }
        return created(Json.toJson(user));
    }
}
