
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	//Relationships

	private String	banner;
	private String	targetPage;
	private double	chargedAmount;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	public String getTargetPage() {
		return this.targetPage;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	public double getChargedAmount() {
		return this.chargedAmount;
	}

	public void setChargedAmount(final double chargedAmount) {
		this.chargedAmount = chargedAmount;
	}


	//Relationship
	private CreditCard	creditcard;
	private Provider	provider;
	private Position	position;


	@OneToOne(optional = false, cascade = {
		CascadeType.ALL
	})
	public CreditCard getcreditcard() {
		return this.creditcard;
	}

	public void setcreditcard(final CreditCard creditcard) {
		this.creditcard = creditcard;
	}

	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
