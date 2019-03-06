
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	// Attributes
	private String name;
	private Collection<Url> pictures;

	
	
	@ElementCollection
	@Valid
	public Collection<Url> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<Url> pictures) {
		this.pictures = pictures;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	
	
	// Relationships ----------------------------------------------------------------------
	private Collection<Brotherhood> brotherhoods;

	
	@OneToMany(mappedBy = "area")
	public Collection<Brotherhood> getBrotherhoods() {
		return brotherhoods;
	}

	public void setBrotherhoods(Collection<Brotherhood> brotherhoods) {
		this.brotherhoods = brotherhoods;
	}

	@Override
	public String toString() {
		return "Area [name=" + name + ", brotherhoods=" + brotherhoods + "]";
	}
}
