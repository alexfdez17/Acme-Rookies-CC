
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Curricula;
import domain.Rookie;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	//Managed Repository
	@Autowired
	private PersonalDataRepository	personalDataRepository;
	@Autowired
	private RookieService			rookieService;
	@Autowired
	private CurriculaService		curriculaService;


	// Constructor ----------------------------------------------------

	public PersonalDataService() {
		super();
	}

	//CRUD

	public PersonalData create() {
		PersonalData result;

		result = new PersonalData();

		return result;

	}
	public PersonalData copy(final PersonalData pData) {
		final PersonalData result = this.create();
		result.setGithubProfile(pData.getGithubProfile());
		result.setLinkedinProfile(pData.getLinkedinProfile());
		result.setMiddleName(pData.getMiddleName());
		result.setName(pData.getName());
		result.setPhone(pData.getPhone());
		result.setStatement(pData.getStatement());
		result.setSurname(pData.getStatement());
		this.personalDataRepository.save(result);
		return result;
	}

	public PersonalData save(final PersonalData personalData) {
		Assert.notNull(personalData);
		PersonalData result;
		if (personalData.getId() == 0) {
			final Rookie rookie = this.rookieService.findByPrincipal();
			final Curricula curricula = this.curriculaService.create();
			curricula.setPersonalData(personalData);
			curricula.setRookie(rookie);
			this.curriculaService.save(curricula);

		} else {
			Assert.isTrue(this.curriculaService.findByPersonalData(personalData).getRookie().equals(this.rookieService.findByPrincipal()));
			Assert.isTrue(!this.curriculaService.findByPersonalData(personalData).getIsCopy());
		}
		result = this.personalDataRepository.save(personalData);
		this.personalDataRepository.flush();
		return result;
	}

	public void delete(final PersonalData personalData) {
		Assert.notNull(personalData);
		Assert.isTrue(personalData.getId() != 0);
		Assert.isTrue(this.personalDataRepository.exists(personalData.getId()));

		this.personalDataRepository.delete(personalData);
	}

	public Collection<PersonalData> findAll() {
		Collection<PersonalData> result;

		result = this.personalDataRepository.findAll();

		return result;
	}

	public PersonalData findOne(final int personalDataId) {
		PersonalData result;

		result = this.personalDataRepository.findOne(personalDataId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.personalDataRepository.flush();
	}

	// Export

	public String export(final PersonalData pd) {

		final String result = "Personal Data\n\nName: " + pd.getName() + "\nMiddle name: " + pd.getMiddleName() + "\nSurname: " + pd.getSurname() + "\nStatement: " + pd.getStatement() + "\nGithub Profile: " + pd.getGithubProfile() + "\nLinkedIn Profile: "
			+ pd.getLinkedinProfile() + "\nPhone: " + pd.getPhone() + "\n\n";

		return result;
	}
}
