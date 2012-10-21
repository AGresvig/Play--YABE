package models;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

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
		new Post(new Date(), "Post One", "This is a bunch of text", createTestUser()).save();
		
		List<Post> postList = Post.find.all(); 
		
		assertEquals("There should be one post in the DB", 1, postList.size() );
		
		Post postOne = Post.find.byId(1L);
		assertEquals("This post one", 1L, postOne.id.longValue());
		assertEquals("This should be Aksel", "Post One", postOne.title);
	}

	private User createTestUser() {
		return new User("Aksel", "Gresvig", new Date(), EMAIL, "testPw", true);
	}
}
