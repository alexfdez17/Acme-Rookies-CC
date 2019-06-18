
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import domain.Curricula;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Service
@Transactional
public class MiscellaneousDataService {

	//Managed Repository
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;
	@Autowired
	private RookieService				rookieService;
	@Autowired
	private CurriculaService			curriculaService;


	// Constructor ----------------------------------------------------

	public MiscellaneousDataService() {
		super();
	}

	//CRUD

	public MiscellaneousData create() {
		MiscellaneousData result;

		result = new MiscellaneousData();

		return result;

	}

	public MiscellaneousData copy(final MiscellaneousData mData) {
		final MiscellaneousData result = this.create();
		result.setAttachments(mData.getAttachments());
		result.setFreeText(mData.getFreeText());
		this.miscellaneousDataRepository.save(result);
		return result;
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneousData, final Curricula curricula) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(!curricula.getIsCopy());
		Assert.notNull(curricula);
		Assert.isTrue(curricula.getRookie().equals(this.rookieService.findByPrincipal()));
		MiscellaneousData result;
		curricula.setMiscellaneousData(miscellaneousData);
		this.curriculaService.save(curricula);
		this.curriculaService.flush();
		result = this.miscellaneousDataRepository.save(miscellaneousData);
		this.miscellaneousDataRepository.flush();
		return result;
	}

	public void delete(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(miscellaneousData.getId() != 0);
		Assert.isTrue(this.miscellaneousDataRepository.exists(miscellaneousData.getId()));
		final Curricula curricula = this.curriculaService.findByMiscellaneousData(miscellaneousData);
		Assert.isTrue(curricula.getRookie().equals(this.rookieService.findByPrincipal()));
		this.miscellaneousDataRepository.delete(miscellaneousData);
	}

	public Collection<MiscellaneousData> findAll() {
		Collection<MiscellaneousData> result;

		result = this.miscellaneousDataRepository.findAll();

		return result;
	}

	public MiscellaneousData findOne(final int miscellaneousDataId) {
		MiscellaneousData result;

		result = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}

	public MiscellaneousData reconstruct(final MiscellaneousDataForm miscellaneousDataForm) {
		MiscellaneousData miscellaneousData;
		if (miscellaneousDataForm.getId() == 0)
			miscellaneousData = this.create();
		else
			miscellaneousData = this.findOne(miscellaneousDataForm.getId());
		miscellaneousData.setId(miscellaneousDataForm.getId());
		miscellaneousData.setVersion(miscellaneousDataForm.getVersion());
		miscellaneousData.setFreeText(miscellaneousDataForm.getFreeText());
		final String attach = miscellaneousDataForm.getAttachments();
		final List<String> attachments = new ArrayList<>();
		final String[] attachmArray = attach.split(",");
		for (final String s : attachmArray)
			if (!s.isEmpty())
				attachments.add(s);
		miscellaneousData.setAttachments(attachments);
		return miscellaneousData;
	}

	public MiscellaneousDataForm toMiscellaneousDataForm(final MiscellaneousData miscellaneousData) {
		MiscellaneousDataForm result;

		result = new MiscellaneousDataForm();
		result.setFreeText(miscellaneousData.getFreeText());
		result.setVersion(miscellaneousData.getVersion());
		String attachments = "";
		if (miscellaneousData.getAttachments() != null) {
			final Collection<String> att = miscellaneousData.getAttachments();
			for (final String a : att)
				attachments = attachments.concat(a + ",");
			attachments.trim();
		}
		result.setAttachments(attachments);
		result.setId(miscellaneousData.getId());

		return result;

	}

	// Export

	public String export(final MiscellaneousData md) {

		final String result = "Miscellaneous Data\n\nFree text: " + md.getFreeText() + "\nAttachments: " + md.getAttachments() + "\n\n";

		return result;
	}
}
