
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NuevaEntidadXXXRepository;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.NuevaEntidadXXX;

@Service
@Transactional
public class NuevaEntidadXXXService {

	// Managed Repository
	@Autowired
	private NuevaEntidadXXXRepository	nuevaEntidadXXXRepository;

	//Supported services

	@Autowired
	private CompanyService				companyService;


	// CRUD

	public NuevaEntidadXXX create() {
		final NuevaEntidadXXX result;
		String ticker, month, year, day, letters;
		Date today;

		today = new Date();

		year = String.valueOf(today.getYear() - 100);
		if (today.getMonth() < 10)
			month = "0" + String.valueOf(today.getMonth() + 1);
		else
			month = String.valueOf(today.getMonth() + 1);
		if (today.getDate() < 10)
			day = "0" + String.valueOf(today.getDate());
		else
			day = String.valueOf(today.getDate());

		//Generate letters
		letters = RandomStringUtils.randomAlphabetic(5).toUpperCase();

		ticker = year + month + day + "-" + letters;

		result = new NuevaEntidadXXX();
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setTicker(ticker);

		return result;
	}

	public NuevaEntidadXXX save(final NuevaEntidadXXX nuevaEntidadXXX) {
		Assert.notNull(nuevaEntidadXXX);
		NuevaEntidadXXX result;

		if (nuevaEntidadXXX.getId() != 0) {
			final NuevaEntidadXXX retrieved = this.findOne(nuevaEntidadXXX.getId());
			Assert.isTrue(retrieved.isDraftMode());
		}

		final Company principal = this.companyService.findByPrincipal();
		final Company owner = nuevaEntidadXXX.getAudit().getPosition().getCompany();
		Assert.isTrue(principal.equals(owner));

		result = this.nuevaEntidadXXXRepository.save(nuevaEntidadXXX);
		return result;
	}

	public void delete(final NuevaEntidadXXX nuevaEntidadXXX) {
		Assert.notNull(nuevaEntidadXXX);
		Assert.isTrue(nuevaEntidadXXX.getId() != 0);

		final NuevaEntidadXXX retrieved = this.findOne(nuevaEntidadXXX.getId());
		Assert.isTrue(retrieved.isDraftMode());

		final Company principal = this.companyService.findByPrincipal();
		final Company owner = nuevaEntidadXXX.getAudit().getPosition().getCompany();
		Assert.isTrue(principal.equals(owner));

		this.nuevaEntidadXXXRepository.delete(nuevaEntidadXXX);
	}

	public Collection<NuevaEntidadXXX> findAll() {
		Collection<NuevaEntidadXXX> result;

		result = this.nuevaEntidadXXXRepository.findAll();

		return result;
	}

	public NuevaEntidadXXX findOne(final int nuevaEntidadXXXId) {
		NuevaEntidadXXX result;

		result = this.nuevaEntidadXXXRepository.findOne(nuevaEntidadXXXId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.nuevaEntidadXXXRepository.flush();
	}

	public Collection<NuevaEntidadXXX> findByAuditor(final Auditor auditor) {
		Collection<NuevaEntidadXXX> nuevaEntidadXXXs;
		final Collection<NuevaEntidadXXX> result = new ArrayList<NuevaEntidadXXX>();

		nuevaEntidadXXXs = this.nuevaEntidadXXXRepository.findByAuditorId(auditor.getId());

		for (final NuevaEntidadXXX n : nuevaEntidadXXXs)
			if (!n.isDraftMode())
				result.add(n);
		return result;
	}

	public Collection<NuevaEntidadXXX> findByCompany(final Company company) {
		return this.nuevaEntidadXXXRepository.findByCompanyId(company.getId());
	}

	public Collection<NuevaEntidadXXX> findByAudit(final Audit audit) {
		return this.nuevaEntidadXXXRepository.findByAuditId(audit.getId());
	}


	// Reconstruct

	@Autowired
	private Validator	validator;


	public NuevaEntidadXXX reconstruct(final NuevaEntidadXXX nuevaEntidadXXX, final BindingResult binding) {
		NuevaEntidadXXX result;

		if (nuevaEntidadXXX.getId() == 0)
			result = nuevaEntidadXXX;
		else {
			final NuevaEntidadXXX retrieved = this.nuevaEntidadXXXRepository.findOne(nuevaEntidadXXX.getId());
			result = nuevaEntidadXXX;

			result.setAudit(retrieved.getAudit());
			result.setMoment(retrieved.getMoment());
			result.setTicker(retrieved.getTicker());

		}

		this.validator.validate(result, binding);
		this.flush();
		return result;
	}

}
