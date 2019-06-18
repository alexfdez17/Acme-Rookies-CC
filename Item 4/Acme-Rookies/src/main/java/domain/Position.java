package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "ticker, title, description, profileRequired, skillsRequired, technologiesRequired"),
		@Index(columnList = "company"), @Index(columnList = "status"),
		@Index(columnList = "deadLine"), @Index(columnList = "salaryOffered") })
public class Position extends DomainEntity {

	private String title;
	private String description;
	private Date deadLine;
	private int salaryOffered;
	private String profileRequired;
	private String skillsRequired;
	private String technologiesRequired;
	private String ticker;
	private String status;

	public Position(final Position p) {
		// TODO Auto-generated constructor stub
		super();
		this.company = p.getCompany();
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.deadLine = p.getDeadLine();
		this.salaryOffered = p.getSalaryOffered();
		this.profileRequired = p.getProfileRequired();
		this.skillsRequired = p.getSkillsRequired();
		this.technologiesRequired = p.getTechnologiesRequired();
		this.ticker = p.getTicker();
		this.status = p.getStatus();
	}

	public Position() {
		// TODO Auto-generated constructor stub
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDeadLine() {
		return this.deadLine;
	}

	public void setDeadLine(final Date deadLine) {
		this.deadLine = deadLine;
	}

	public int getSalaryOffered() {
		return this.salaryOffered;
	}

	public void setSalaryOffered(final int salaryOffered) {
		this.salaryOffered = salaryOffered;
	}

	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotBlank
	public String getProfileRequired() {
		return this.profileRequired;
	}

	public void setProfileRequired(final String profileRequired) {
		this.profileRequired = profileRequired;
	}

	@NotBlank
	public String getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@NotBlank
	public String getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final String technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{4}-[0-9]{4}")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	// Relationship
	private Company company;
	private Collection<Sponsorship> sponsorships;

	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	@OneToMany(mappedBy = "position")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
