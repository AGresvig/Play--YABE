package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Post extends Model {

	private static final long serialVersionUID = 4166617896966160322L;
	
	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String titleString;
	
	@Constraints.Required
	@Formats.NonEmpty
	public String title;
	
	/*@Formats.DateTime(pattern="yyyy-MM-dd")
	public DateTime datePosted;*/
	
	@Lob
	@Constraints.Required
	public String content;
	
	@Constraints.Required
	public User writtenBy;
	
	@Constraints.Required
	@ManyToOne
    public Category category;
	
	@ManyToMany
    public List<Tag> tags;
	
	public static Finder<String, Post> find = new Finder<String, Post>(
			String.class, Post.class);

    public Post() {
        super();
    }

    public Post(DateTime datePosted, String title, String content, User writtenBy, Category category) {
		super();
		//this.datePosted = datePosted;
		this.title = title;
		this.content = content;
		this.writtenBy = writtenBy;
		this.category = category;
	}	
	
	/**
	 * Converts the title to an ID-string
	 */
	public void setFormattedTitleString(String stringToFormat) {
		titleString = stringToFormat.replace(' ', '-').toLowerCase();
	}
}
