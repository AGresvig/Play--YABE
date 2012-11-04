package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import helper.YamlJodaTimeConstructor;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Yaml;
import runners.PlayJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests model classes, EJB Entity operations, such as CRUD etc. 
 * 
 * @author aksel@agresvig.com
 *
 */
@RunWith(PlayJUnitRunner.class)
public class ModelTest {

    //IDs
	private static String EMAIL = "aksel@agresvig.com";
	private static String POST_ONE = "Post One";
	private static String CATEGORY_ONE = "Category One";
	private static String TAG_ONE = "Tag One";

    public static DdlGenerator ddl;
    public static EbeanServer server;

    /**
     * Get some info we need to reset the DB between tests.
     *
     */
    @BeforeClass
    public static void setup() {
        server = Ebean.getServer("default");

        ServerConfig config = new ServerConfig();
        config.setDebugSql(true);

        ddl = new DdlGenerator((SpiEbeanServer) server, new H2Platform(), config);
    }

    /**
     * Runs before every test and resets the database
     * @throws IOException
     */
    @Before
    public void resetDb() throws IOException {

        // drop
        String dropScript = ddl.generateDropDdl();
        ddl.runScript(false, dropScript);

        // create
        String createScript = ddl.generateCreateDdl();
        ddl.runScript(false, createScript);

        // insert data
        loadAndSaveTestdata();
    }

    private void loadAndSaveTestdata() {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("testdata.yaml");
        //Using Yaml instance with custom constructor here in order to support JodaTime dates with Yaml testing..
        Yaml yaml = new Yaml(new YamlJodaTimeConstructor());
        Map data = (Map) yaml.load(input);
        Ebean.save((Collection) (data.get("users")));
        Ebean.save((Collection) (data.get("categories")));
        Ebean.save((Collection) (data.get("tags")));
    }

    @Test
    public void testRetrieveUser() {
        List<User> userList = User.find.all();
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(1);

        User aksel = User.find.byId(EMAIL);

        assertThat(aksel).isNotNull();
        assertThat(EMAIL).isEqualTo(aksel.email);
        assertThat(aksel.firstName).isEqualTo("Aksel");
        assertThat(aksel.lastName).isEqualTo("Gresvig");
        assertThat(aksel.password).isEqualTo("secret");
        assertThat(aksel.isAdmin).isTrue();

        DateTime createDate = new DateTime(1950, 06, 22, 0, 0);
        compareDate(aksel.dateOfBirth, createDate);
    }

    @Test
    public void testCreateUser() {
        User bob = new User();
        bob.firstName = "Bob";
        bob.lastName = "Bobson";
        bob.password = "topsecret";
        bob.email = "bob@bobson.com";
        bob.dateOfBirth = new DateTime(1970, 05, 05, 0, 0);
        bob.isAdmin = false;

        bob.save();

        User bobAgain = User.find.byId("bob@bobson.com");
        assertThat(bobAgain.dateOfBirth).isNotNull();
        assertThat(bobAgain.email).isEqualToIgnoringCase("bob@bobson.com");
        assertThat(bobAgain.firstName).isNotEmpty();
        assertThat(bobAgain.lastName).isNotEmpty();
        assertThat(bobAgain.isAdmin).isFalse();
    }

    @Test
    public void testRetrieveCategory() {
        List<Category> catList = Category.find.all();

        assertThat(catList).isNotEmpty();

        Category category = Category.find.byId(CATEGORY_ONE);

        assertThat(category.name).isEqualTo(CATEGORY_ONE);
        DateTime createDate = new DateTime(2012, 10, 20, 0, 0);
        compareDate(createDate, category.dateCreated);
    }

    @Test
    public void testCreateCategory() {
        Category categoryTwo = new Category("Category two", new DateTime());
        categoryTwo.save();

        Category retrievedCategory = Category.find.byId("Category two");
        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.name).isNotEmpty();
        assertThat(retrievedCategory.dateCreated).isNotNull();

    }

    @Test
    public void testRetrieveTag() {
        List<Tag> tagList = Tag.find.all();

        assertThat(tagList).isNotEmpty();

        Tag tag = Tag.find.byId(TAG_ONE);

        assertThat(tag.name).isEqualTo(TAG_ONE);
        DateTime createDate = new DateTime(2012, 10, 10, 0, 0);
        compareDate(createDate, tag.dateCreated);
    }

    @Test
    public void testCreateTag() {
        Tag tagTwo = new Tag("Tag two", new DateTime());
        tagTwo.save();

        Tag retrievedTag = Tag.find.byId("Tag two");
        assertThat(retrievedTag).isNotNull();
        assertThat(retrievedTag.name).isNotEmpty();
        assertThat(retrievedTag.dateCreated).isNotNull();

    }

    @Test
    public void testCreateAndRetrieveNewPost() {
        User author = User.find.byId(EMAIL);
        Category category = Category.find.byId(CATEGORY_ONE);
        Tag tag = Tag.find.byId(TAG_ONE);
        List<Tag> tagList = new ArrayList<Tag>();
        tagList.add(tag);

        Post newPost = new Post(new DateTime(), POST_ONE, "This is the first post!",
                author, category, tagList);
        newPost.save();

        List<Post> postList = Post.find.all();
        assertThat(postList).isNotEmpty();

        Post postOne = Post.find.byId(POST_ONE);

        assertThat(postOne).isNotNull();
        assertThat(postOne.datePosted).isNotNull();
        assertThat(postOne.content).contains("This is the first post!");
        assertThat(postOne.title).isNotEmpty();
        assertThat(postOne.category).isEqualTo(category);
        assertThat(postOne.tags).contains(tag);
        assertThat(postOne.writtenBy).equals(author);
    }

    private User createUser() {
        return new User("Aksel", "Gresvig", new DateTime(), EMAIL, "secret", true);
    }

    private Post createPost(User author, Category category, List<Tag> tags) {
        return new Post(new DateTime(), "Post One", "This is the blog post content",
                author, category, tags);
    }

    private Category createCategory() {
        return new Category("Category One", new DateTime());
    }

    private Tag createTag() {
        return new Tag("Tag One", new DateTime());
    }

    private void compareDate(DateTime date1, DateTime date2) {
        assertThat(date1.getYear()).isEqualTo(date2.getYear());
        assertThat(date1.getDayOfMonth()).isEqualTo(date2.getDayOfMonth());
        assertThat(date1.getMonthOfYear()).isEqualTo(date2.getMonthOfYear());
    }

    private void compareDateAndTime(DateTime date1, DateTime date2) {
        compareDate(date1, date2);
        assertThat(date1.getHourOfDay()).isEqualTo(date2.getHourOfDay());
        assertThat(date1.getMinuteOfHour()).isEqualTo(date2.getMinuteOfHour());
        assertThat(date1.getSecondOfMinute()).isEqualTo(date2.getSecondOfMinute());
    }
}
