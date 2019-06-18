
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class MiscellaneousDataForm {

	private int		curriculaId;

	private String	freeText;
	private String	attachments;
	private int		id;
	private int		version;


	@NotBlank
	public String getFreeText() {
		return this.freeText;
	}

	public void setFreeText(final String freeText) {
		this.freeText = freeText;
	}
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public int getCurriculaId() {
		return this.curriculaId;
	}

	public void setCurriculaId(final int curriculaId) {
		this.curriculaId = curriculaId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
}
