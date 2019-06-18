package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Service
@Transactional
public class CurriculaService {

	// Managed Repository
	@Autowired
	private CurriculaRepository curriculaRepository;
	@Autowired
	private PersonalDataService personalDataService;
	@Autowired
	private EducationDataService educationDataService;
	@Autowired
	private PositionDataService positionDataService;
	@Autowired
	private MiscellaneousDataService miscellaneousDataService;
	@Autowired
	private RookieService rookieService;

	// Constructor ----------------------------------------------------

	public CurriculaService() {
		super();
	}

	// CRUD

	public Curricula create() {
		Curricula result;

		result = new Curricula();
		final Rookie rookie = this.rookieService.findByPrincipal();
		final PersonalData personalData = this.personalDataService.create();
		final Collection<EducationData> educationData = Collections.emptySet();
		final Collection<PositionData> positionData = Collections.emptySet();
		result.setEducationData(educationData);
		result.setPersonalData(personalData);
		result.setPositionsData(positionData);
		result.setIsCopy(false);
		result.setRookie(rookie);

		return result;

	}

	public Curricula save(final Curricula curricula) {
		Assert.notNull(curricula);
		Assert.isTrue(curricula.getIsCopy() == false);
		Assert.isTrue(curricula.getRookie().equals(
				this.rookieService.findByPrincipal()));
		Curricula result;
		result = this.curriculaRepository.save(curricula);
		this.curriculaRepository.flush();
		return result;
	}

	public void delete(final Curricula curricula) {
		Assert.notNull(curricula);
		Assert.isTrue(curricula.getId() != 0);
		Assert.isTrue(curricula.getRookie().equals(
				this.rookieService.findByPrincipal()));
		Assert.isTrue(this.curriculaRepository.exists(curricula.getId()));

		this.curriculaRepository.delete(curricula);
	}

	public Collection<Curricula> findAll() {
		Collection<Curricula> result;

		result = this.curriculaRepository.findAll();

		return result;
	}

	public Curricula findOne(final int curriculaId) {
		Curricula result;

		result = this.curriculaRepository.findOne(curriculaId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.curriculaRepository.flush();
	}

	public Collection<Curricula> getCurriculaByRookie(final Rookie rookie) {
		final Integer rookieId = rookie.getId();
		final Collection<Curricula> res = this.curriculaRepository
				.getCurriculaByRookieId(rookieId);
		return res;
	}

	public Curricula findByEducationData(final EducationData educationData) {
		final Curricula res = this.curriculaRepository
				.getCurriculaByEducationDataId(educationData.getId());
		return res;
	}

	public Curricula findByPositionData(final PositionData positionData) {
		final Curricula res = this.curriculaRepository
				.getCurriculaByPositionDataId(positionData.getId());
		return res;
	}

	public Curricula findByPersonalData(final PersonalData personalData) {
		final Curricula res = this.curriculaRepository
				.getCurriculaByPersonalDataId(personalData.getId());
		return res;
	}

	public Curricula findByMiscellaneousData(
			final MiscellaneousData miscellaneousData) {
		final Curricula res = this.curriculaRepository
				.getCurriculaByMiscellaneousDataId(miscellaneousData.getId());
		return res;
	}

	public Collection<Curricula> getOriginalCurricula() {
		Collection<Curricula> curriculas;
		curriculas = this.curriculaRepository.getOriginalCurricula();
		return curriculas;
	}

	public Curricula copyCurricula(final Curricula curricula) {
		final Curricula res = this.create();
		res.setIsCopy(true);
		final PersonalData pers = curricula.getPersonalData();
		final PersonalData perRes = this.personalDataService.copy(pers);
		res.setPersonalData(perRes);
		final Collection<EducationData> eds = curricula.getEducationData();
		for (final EducationData edd : eds) {
			final EducationData edRes = this.educationDataService.copy(edd);
			res.getEducationData().add(edRes);
		}
		final Collection<PositionData> pos = curricula.getPositionsData();
		for (final PositionData poss : pos) {
			final PositionData posRes = this.positionDataService.copy(poss);
			res.getPositionsData().add(posRes);
		}
		if (curricula.getMiscellaneousData() != null) {
			final MiscellaneousData mData = curricula.getMiscellaneousData();
			final MiscellaneousData mRes = this.miscellaneousDataService
					.copy(mData);
			res.setMiscellaneousData(mRes);
		}
		this.curriculaRepository.save(res);
		this.curriculaRepository.flush();
		return res;
	}

	public Double[] curriculaPerRookieStats() {

		final Double[] res = new Double[4];
		final Collection<Long> stats = this.curriculaRepository
				.getCurriculaPerRookie();
		if (!stats.isEmpty()) {
			Double sum = 0.0;
			for (final Long n : stats)
				sum += n;
			res[0] = sum / stats.size(); // Average

			res[1] = Collections.min(stats).doubleValue();

			res[2] = Collections.max(stats).doubleValue();

			Double num = 0.0;
			Double numi = 0.0;
			for (final Long n : stats) {
				numi = Math.pow(n - res[0], 2);
				num += numi;
			}
			if (num != 0) {
				res[3] = Math.sqrt(num / (stats.size() - 1)); // Standard
																// deviation
			} else {
				res[3] = 0.;
			}
		} else {
			res[0] = 0.;
			res[1] = 0.;
			res[2] = 0.;
			res[3] = 0.;
		}

		return res;

	}

	// Clear

	public void clear(final Curricula curricula) {

		this.curriculaRepository.delete(curricula);
	}

	// Export

	public String export(final Curricula curricula) {

		String result = "Curricula\n\nCopy: " + curricula.getIsCopy() + "\n\n";

		final PersonalData personalData = curricula.getPersonalData();
		if (personalData != null)
			result = result + this.personalDataService.export(personalData);

		final Collection<PositionData> pds = curricula.getPositionsData();
		for (final PositionData pd : pds)
			result = result + this.positionDataService.export(pd);

		final Collection<EducationData> eds = curricula.getEducationData();
		for (final EducationData ed : eds)
			result = result + this.educationDataService.export(ed);

		final MiscellaneousData miscellaneousData = curricula
				.getMiscellaneousData();
		if (miscellaneousData != null)
			result = result
					+ this.miscellaneousDataService.export(miscellaneousData);

		return result;
	}
}
