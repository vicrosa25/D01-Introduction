package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Attributes -------------------------------------------------------------
	private String email;

	@NotBlank
	@Pattern(regexp = "^[\\w\\s]+(\\s*)\\<\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\\>|\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}|[\\w\\s]+(\\s*)\\<\\w+@\\>|\\w+@$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
}
