
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	private String	name;
	private String	middleName;
	private String	surname;
	private String	statement;
	private String	githubProfile;
	private String	linkedinProfile;
	private String	phone;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}
	@URL
	@NotNull
	public String getGithubProfile() {
		return this.githubProfile;
	}

	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}
	@URL
	@NotNull
	public String getLinkedinProfile() {
		return this.linkedinProfile;
	}

	public void setLinkedinProfile(final String linkedinProfile) {
		this.linkedinProfile = linkedinProfile;
	}
	@NotBlank
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
}
