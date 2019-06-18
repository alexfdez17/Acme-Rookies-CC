package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	private String providerMake;
	private Collection<Sponsorship> sponsorships;

	@NotBlank
	public String getProviderMake() {
		return this.providerMake;
	}

	public void setProviderMake(final String providerMake) {
		this.providerMake = providerMake;
	}

	@OneToMany(mappedBy = "provider")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
