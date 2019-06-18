
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date	creationMoment;
	private String	answerExplanation;
	private String	answerCode;
	private Date	submittedMoment;
	private String	status;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:MM")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public String getAnswerExplanation() {
		return this.answerExplanation;
	}

	public void setAnswerExplanation(final String answerExplanation) {
		this.answerExplanation = answerExplanation;
	}
	@URL
	public String getAnswerCode() {
		return this.answerCode;
	}

	public void setAnswerCode(final String answerCode) {
		this.answerCode = answerCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:MM")
	public Date getSubmittedMoment() {
		return this.submittedMoment;
	}

	public void setSubmittedMoment(final Date submittedMoment) {
		this.submittedMoment = submittedMoment;
	}
	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}


	//Relationships

	private PositionProblem	positionProblem;
	private Rookie			rookie;


	@ManyToOne(optional = false)
	public PositionProblem getPositionProblem() {
		return this.positionProblem;
	}

	public void setPositionProblem(final PositionProblem positionProblem) {
		this.positionProblem = positionProblem;
	}

	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}
}
