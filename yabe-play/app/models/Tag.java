package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.DateTime;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Tag extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String name;
	
	/*@Formats.DateTime(pattern="yyyy-MM-dd")
	public DateTime dateCreated;*/
	
	public static Finder<String, Tag> find = new Finder<String, Tag>(
			String.class, Tag.class);

    public Tag() {
        super();
    }

    public Tag(String name, DateTime dateCreated) {
		super();
		this.name = name;
		//this.dateCreated = dateCreated;
	}
}
