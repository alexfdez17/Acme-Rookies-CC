package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	private String comercialName;
	private Double auditScore;

	@NotBlank
	public String getComercialName() {
		return this.comercialName;
	}

	public void setComercialName(final String comercialName) {
		this.comercialName = comercialName;
	}

	public Double getAuditScore() {
		return auditScore;
	}

	public void setAuditScore(Double auditScore) {
		this.auditScore = auditScore;
	}
	
	
}
