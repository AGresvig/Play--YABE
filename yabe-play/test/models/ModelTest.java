package models;

import com.avaje.ebean.Ebean;
import helper.YamlJodaTimeConstructor;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.yaml.snakeyaml.Yaml;
import play.Play;
import play.libs.Yaml;
import runners.PlayJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests model classes, EJB Entity operations, such as CRUD etc. 
 * 
 * @author aksel
 *
 */
@RunWith(PlayJUnitRunner.class)
public class ModelTest {

	private static String EMAIL = "aksel@agresvig.com";
	private static String POST_ONE = "Post One";
	private static String CATEGORY_ONE = "Category One";
	private static String TAG_ONE = "Tag One";
	private Map data;
	
	public ModelTest() {
        data = (Map) Yaml.load("testdata.yaml");
	}
	
	@Test
	public void testCreateAndRetrieveUser() {
       /* Yaml yaml = new Yaml(new YamlJodaTimeConstructor());
        InputStream input = null;
        try {
            input = new FileInputStream(new File("test/resources/testdata.yaml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Object all = yaml.load(input);
        InputStream inputStream = Play.application().resourceAsStream("testdata.yaml");*/
        Ebean.save((Collection) (data.get("users")));

		List<User> userList = User.find.all();
		assertEquals("There should be one user in the DB", 1, userList.size());

		User aksel = User.find.byId(EMAIL);
		assertEquals("This should be Aksel", EMAIL, aksel.email);
		assertEquals("This should be Aksel", "Aksel", aksel.firstName);
	}

	@Test
	public void testCreateAndRetrievePost() {
		createPost().save();
		
		List<Post> postList = Post.find.all(); 
		
		assertEquals("There should be one post in the DB", 1, postList.size() );
		
		Post postOne = Post.find.byId(POST_ONE);
		assertEquals("This should be Aksel", POST_ONE, postOne.title);
	}
	
	@Test
	public void testCreateAndRetrieveCategory() {
		createCategory().save();
		
		List<Category> catList = Category.find.all();
		
		assertEquals("There should be one category", 1, catList.size());
		
		Category category = Category.find.byId(CATEGORY_ONE);
		assertEquals("This should be Category one", CATEGORY_ONE, category.name);
	}
	
	@Test
	public void testCreateAndRetrieveTag() {
		createTag().save();
		
		List<Tag> tagList = Tag.find.all();
		
		assertEquals("There should be one category", 1, tagList.size());
		
		Tag tag = Tag.find.byId(TAG_ONE);
		assertEquals("This should be tag one", TAG_ONE, tag.name);
	}
	
	private User createTestUser() {
		return new User("Aksel", "Gresvig", new DateTime(), EMAIL, "testPw", true);
	}
	
	private Post createPost() {
		return new Post(new DateTime(), "Post One", "This is a bunch of text",
				createTestUser(), createCategory());
	}
	
	private Category createCategory() {
		return new Category("Category One", new DateTime());
	}
	
	private Tag createTag() {
		return new Tag("Tag One", new DateTime());
	}
}
