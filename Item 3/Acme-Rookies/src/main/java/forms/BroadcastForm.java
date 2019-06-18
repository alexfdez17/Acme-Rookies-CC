
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class BroadcastForm {

	private String	subject;
	private String	body;

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}
}
