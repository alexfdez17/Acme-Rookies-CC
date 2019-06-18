
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfig extends DomainEntity {

	private String				systemName;
	private String				banner;
	private String				welcomeMessage;
	private String				spanishWelcomeMessage;
	private String				phonePrefix;
	private Double				finderCacheHours;
	private int					finderMaxResults;
	private Collection<String>	spamWords;
	private Boolean				nameChanged;
	private double				charge;
	private int					vat;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}
	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@NotBlank
	public String getSpanishWelcomeMessage() {
		return this.spanishWelcomeMessage;
	}

	public void setSpanishWelcomeMessage(final String spanishWelcomeMessage) {
		this.spanishWelcomeMessage = spanishWelcomeMessage;
	}

	@NotBlank
	public String getPhonePrefix() {
		return this.phonePrefix;
	}

	public void setPhonePrefix(final String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}
	@NotNull
	@Range(min = 1, max = 24)
	public Double getFinderCacheHours() {
		return this.finderCacheHours;
	}

	public void setFinderCacheHours(final Double finderCacheHours) {
		this.finderCacheHours = finderCacheHours;
	}
	@NotNull
	@Range(min = 10, max = 100)
	public int getFinderMaxResults() {
		return this.finderMaxResults;
	}

	public void setFinderMaxResults(final int finderMaxResults) {
		this.finderMaxResults = finderMaxResults;
	}
	@ElementCollection(targetClass = String.class)
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	public Boolean getNameChanged() {
		return this.nameChanged;
	}

	public void setNameChanged(final Boolean nameChanged) {
		this.nameChanged = nameChanged;
	}

	@NotNull
	public double getCharge() {
		return this.charge;
	}

	public void setCharge(final double charge) {
		this.charge = charge;
	}

	public int getVat() {
		return this.vat;
	}

	@Range(min = 0, max = 100)
	public void setVat(final int vat) {
		this.vat = vat;
	}

}
