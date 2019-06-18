
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	keyword;
	private Date	maximumDeadLine;
	private int		minimumSalary;
	private Date	finderDate;
	private Collection<Sponsorship> sponsorships;


	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaximumDeadLine() {
		return this.maximumDeadLine;
	}

	public void setMaximumDeadLine(final Date maximumDeadLine) {
		this.maximumDeadLine = maximumDeadLine;
	}

	public int getMinimumSalary() {
		return this.minimumSalary;
	}

	public void setMinimumSalary(final int minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getFinderDate() {
		return this.finderDate;
	}

	public void setFinderDate(final Date finderDate) {
		this.finderDate = finderDate;
	}
	
	@OneToMany(cascade = {CascadeType.ALL})
		public Collection<Sponsorship> getSponsorships() {
			return this.sponsorships;
		}

		public void setSponsorships(final Collection<Sponsorship> sponsorships) {
			this.sponsorships = sponsorships;
		}


	//Relationships

	private Rookie					rookie;
	private Collection<Position>	positions;


	@OneToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
