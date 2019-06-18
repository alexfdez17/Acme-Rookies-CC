
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import domain.Curricula;
import domain.EducationData;
import forms.EducationDataForm;

@Service
@Transactional
public class EducationDataService {

	//Managed Repository
	@Autowired
	private EducationDataRepository	educationDataRepository;
	@Autowired
	private RookieService			rookieService;
	@Autowired
	private CurriculaService		curriculaService;


	// Constructor ----------------------------------------------------

	public EducationDataService() {
		super();
	}

	//CRUD

	public EducationData create() {
		EducationData result;

		result = new EducationData();

		return result;

	}

	public EducationData copy(final EducationData eData) {
		final EducationData result = this.create();
		result.setDegree(eData.getDegree());
		result.setEndDate(eData.getEndDate());
		result.setInstitution(eData.getInstitution());
		result.setMark(eData.getMark());
		result.setStartDate(eData.getStartDate());
		this.educationDataRepository.save(result);
		return result;
	}

	public EducationData save(final EducationData educationData, final Curricula curricula) {
		Assert.notNull(educationData);
		Assert.isTrue(curricula.getRookie().equals(this.rookieService.findByPrincipal()));
		Assert.isTrue(!curricula.getIsCopy());
		EducationData result;
		if (educationData.getId() == 0) {
			curricula.getEducationData().add(educationData);
			this.curriculaService.save(curricula);
			this.curriculaService.flush();
		}
		if (educationData.getEndDate() != null)
			Assert.isTrue(educationData.getStartDate().before(educationData.getEndDate()));
		result = this.educationDataRepository.save(educationData);
		this.educationDataRepository.flush();
		return result;
	}

	public void delete(final EducationData educationData) {
		Assert.notNull(educationData);
		Assert.isTrue(educationData.getId() != 0);
		Assert.isTrue(this.educationDataRepository.exists(educationData.getId()));
		final Curricula curricula = this.curriculaService.findByEducationData(educationData);
		Assert.isTrue(curricula.getRookie().equals(this.rookieService.findByPrincipal()));
		curricula.getEducationData().remove(educationData);
		this.educationDataRepository.delete(educationData);
	}

	public Collection<EducationData> findAll() {
		Collection<EducationData> result;

		result = this.educationDataRepository.findAll();

		return result;
	}

	public EducationData findOne(final int educationDataId) {
		EducationData result;

		result = this.educationDataRepository.findOne(educationDataId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.educationDataRepository.flush();
	}

	public EducationData reconstruct(final EducationDataForm educationDataForm) {
		EducationData educationData;
		if (educationDataForm.getId() == 0)
			educationData = this.create();
		else
			educationData = this.findOne(educationDataForm.getId());
		educationData.setId(educationDataForm.getId());
		educationData.setVersion(educationDataForm.getVersion());
		educationData.setStartDate(educationDataForm.getStartDate());
		educationData.setMark(educationDataForm.getMark());
		educationData.setInstitution(educationDataForm.getInstitution());
		educationData.setDegree(educationDataForm.getDegree());
		educationData.setEndDate(educationDataForm.getEndDate());
		return educationData;
	}

	public EducationDataForm toEducationDataForm(final EducationData educationData) {
		EducationDataForm result;

		result = new EducationDataForm();
		result.setDegree(educationData.getDegree());
		result.setVersion(educationData.getVersion());
		result.setMark(educationData.getMark());
		result.setInstitution(educationData.getInstitution());
		result.setStartDate(educationData.getStartDate());
		result.setEndDate(educationData.getEndDate());
		result.setId(educationData.getId());

		return result;

	}

	// Export

	public String export(final EducationData ed) {

		final String result = "Education Data\n\nDegree: " + ed.getDegree() + "\nInstitution: " + ed.getInstitution() + "\nMark: " + ed.getMark() + "\nStart Date: " + ed.getStartDate() + "\nEnd Date: " + ed.getEndDate() + "\n\n";

		return result;
	}

}
