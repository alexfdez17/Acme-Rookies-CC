
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isCopy")
})
public class Curricula extends DomainEntity {

	//Relationships

	private PersonalData				personalData;
	private Collection<PositionData>	positionsData;
	private Collection<EducationData>	educationData;
	private MiscellaneousData			miscellaneousData;
	private Rookie						rookie;
	private Boolean						isCopy;


	@OneToOne(optional = false, cascade = {
		CascadeType.ALL
	})
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@OneToMany(cascade = {
		CascadeType.ALL
	})
	public Collection<PositionData> getPositionsData() {
		return this.positionsData;
	}

	public void setPositionsData(final Collection<PositionData> positionsData) {
		this.positionsData = positionsData;
	}

	@OneToMany(cascade = {
		CascadeType.ALL
	})
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}
	@OneToOne(optional = true, cascade = {
		CascadeType.ALL
	})
	public MiscellaneousData getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(final MiscellaneousData miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@NotNull
	public Boolean getIsCopy() {
		return this.isCopy;
	}

	public void setIsCopy(final Boolean isCopy) {
		this.isCopy = isCopy;
	}

}
