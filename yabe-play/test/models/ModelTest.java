package models;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class ModelTest extends BaseModelTest {

	private static String EMAIL = "aksel@agresvig.com";

	@Test
	public void testCreateAndRetrieveUser() {
		User newUser = createTestUser();
		newUser.save();

		List<User> userList = User.find.all();
		assertEquals("There should be one user in the DB", 1, userList.size());

		User aksel = User.find.byId(1L);
		assertEquals("This should be Aksel", 1L, aksel.id.longValue());
		assertEquals("This should be Aksel", "Aksel", aksel.firstName);
	}

	@Test
	public void testCreateAndRetrievePost() {
		createPost().save();
		
		List<Post> postList = Post.find.all(); 
		
		assertEquals("There should be one post in the DB", 1, postList.size() );
		
		Post postOne = Post.find.byId(1L);
		assertEquals("This post one", 1L, postOne.id.longValue());
		assertEquals("This should be Aksel", "Post One", postOne.title);
	}
	
	@Test
	public void testCreateAndRetrieveCategory() {
		createCategory().save();
		
		List<Category> catList = Category.find.all();
		
		assertEquals("There should be one category", 1, catList.size());
		
		Category category = Category.find.byId(1L);
		assertEquals("This should be category one", 1L, category.id.longValue());
		assertEquals("This should be Category one", "Category One", category.name);
	}
	
	@Test
	public void testCreateAndRetrieveTag() {
		createTag().save();
		
		List<Tag> tagList = Tag.find.all();
		
		assertEquals("There should be one category", 1, tagList.size());
		
		Tag tag = Tag.find.byId(1L);
		assertEquals("This should be tag one", 1L, tag.id.longValue());
		assertEquals("This should be tag one", "Tag One", tag.name);
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
