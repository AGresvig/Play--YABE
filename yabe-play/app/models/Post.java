package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Post extends Model {

	private static final long serialVersionUID = 4166617896966160322L;
	
	@Id
	public Long id;
	
	@Formats.DateTime(pattern="yyyy-MM-dd")
	public DateTime datePosted;
	
	@Constraints.Required
	public String title;
	
	@Lob
	@Constraints.Required
	public String content;
	
	@Constraints.Required
	public User writtenBy;
	
	@Constraints.Required
	@ManyToOne
	private Category category;
	
	public static Finder<Long, Post> find = new Finder<Long, Post>(
			Long.class, Post.class);
	
	public Post(DateTime datePosted, String title, String content, User writtenBy, Category category) {
		super();
		this.datePosted = datePosted;
		this.title = title;
		this.content = content;
		this.writtenBy = writtenBy;
		this.category = category;
	}	
}
