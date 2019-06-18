
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Curricula;
import domain.PositionData;
import forms.PositionDataForm;

@Service
@Transactional
public class PositionDataService {

	//Managed Repository
	@Autowired
	private PositionDataRepository	positionDataRepository;
	@Autowired
	private RookieService			rookieService;
	@Autowired
	private CurriculaService		curriculaService;


	// Constructor ----------------------------------------------------

	public PositionDataService() {
		super();
	}

	//CRUD

	public PositionData create() {
		PositionData result;

		result = new PositionData();

		return result;

	}

	public PositionData copy(final PositionData pData) {
		final PositionData result = this.create();
		result.setDescription(pData.getDescription());
		if (pData.getEndDate() != null)
			result.setEndDate(pData.getEndDate());
		result.setStartDate(pData.getStartDate());
		result.setTitle(pData.getTitle());
		this.positionDataRepository.save(result);
		return result;
	}

	public PositionData save(final PositionData positionData, final Curricula curricula) {
		Assert.notNull(positionData);
		Assert.isTrue(curricula.getRookie().equals(this.rookieService.findByPrincipal()));
		Assert.isTrue(!curricula.getIsCopy());
		PositionData result;
		if (positionData.getId() == 0) {
			curricula.getPositionsData().add(positionData);
			this.curriculaService.save(curricula);
			this.curriculaService.flush();
		}
		if (positionData.getEndDate() != null)
			Assert.isTrue(positionData.getStartDate().before(positionData.getEndDate()));
		result = this.positionDataRepository.save(positionData);
		this.positionDataRepository.flush();
		return result;
	}

	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		Assert.isTrue(positionData.getId() != 0);
		Assert.isTrue(this.positionDataRepository.exists(positionData.getId()));
		final Curricula curricula = this.curriculaService.findByPositionData(positionData);
		Assert.isTrue(curricula.getRookie().equals(this.rookieService.findByPrincipal()));
		curricula.getPositionsData().remove(positionData);
		this.positionDataRepository.delete(positionData);
	}

	public Collection<PositionData> findAll() {
		Collection<PositionData> result;

		result = this.positionDataRepository.findAll();

		return result;
	}

	public PositionData findOne(final int positionDataId) {
		PositionData result;

		result = this.positionDataRepository.findOne(positionDataId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.positionDataRepository.flush();
	}

	public PositionData reconstruct(final PositionDataForm positionDataForm) {
		PositionData positionData;
		if (positionDataForm.getId() == 0)
			positionData = this.create();
		else
			positionData = this.findOne(positionDataForm.getId());
		positionData.setId(positionDataForm.getId());
		positionData.setVersion(positionDataForm.getVersion());
		positionData.setStartDate(positionDataForm.getStartDate());
		positionData.setTitle(positionDataForm.getTitle());
		positionData.setDescription(positionDataForm.getDescription());
		positionData.setEndDate(positionDataForm.getEndDate());
		return positionData;
	}

	public PositionDataForm toPositionDataForm(final PositionData positionData) {
		PositionDataForm result;

		result = new PositionDataForm();
		result.setDescription(positionData.getDescription());
		result.setVersion(positionData.getVersion());
		result.setTitle(positionData.getTitle());
		result.setStartDate(positionData.getStartDate());
		result.setEndDate(positionData.getEndDate());
		result.setId(positionData.getId());

		return result;

	}

	// Export

	public String export(final PositionData pd) {

		final String result = "Position Data\n\nTitle: " + pd.getTitle() + "\nDescription: " + pd.getDescription() + "\nStart Date: " + pd.getStartDate() + "\nEnd Date: " + pd.getEndDate() + "\n\n";

		return result;
	}
}
