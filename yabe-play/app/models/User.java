package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.DateTime;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class User extends Model {

	private static final long serialVersionUID = 893814552367259173L;
	
	@Id
	public Long id;
	
	@Constraints.Required
	public String firstName;
	
	@Constraints.Required
	public String lastName;
	
	@Formats.DateTime(pattern="yyyy-MM-dd")
	public DateTime dateOfBirth;
	
	@Constraints.Email
	public String email;
	
	@Constraints.Required
	public String password;
	
	public boolean isAdmin;	

	public static Finder<Long, User> find = new Finder<Long, User>(
			Long.class, User.class);
	
	public User(String firstName, String lastName, DateTime dateofBirth,
			String email, String password, boolean isAdmin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateofBirth;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}
			
}
