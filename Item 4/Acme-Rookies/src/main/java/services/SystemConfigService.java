
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SystemConfigRepository;
import domain.SystemConfig;
import forms.SystemConfigForm;

@Service
@Transactional
public class SystemConfigService {

	// Managed Repository
	@Autowired
	private SystemConfigRepository	systemConfigRepository;


	// Constructor
	public SystemConfigService() {
		super();
	}

	// CRUD

	public SystemConfig findSystemConfiguration() {
		SystemConfig result = null;
		result = this.systemConfigRepository.findAll().get(0);
		return result;
	}

	public SystemConfig save(final SystemConfig systemConfig) {
		Assert.notNull(systemConfig);
		Assert.isTrue(systemConfig.getId() != 0);
		SystemConfig result;
		result = this.systemConfigRepository.save(systemConfig);

		return result;
	}

	public void delete(final SystemConfig systemConfig) {
		Assert.notNull(systemConfig);
		Assert.isTrue(systemConfig.getId() != 0);
		Assert.isTrue(this.systemConfigRepository.exists(systemConfig.getId()));

		this.systemConfigRepository.delete(systemConfig);
	}

	public Collection<SystemConfig> findAll() {
		Collection<SystemConfig> result;

		result = this.systemConfigRepository.findAll();

		return result;
	}

	public SystemConfigForm toSystemConfigForm() {
		final SystemConfig systemConfig = this.findSystemConfiguration();
		final SystemConfigForm result = new SystemConfigForm();
		result.setBanner(systemConfig.getBanner());
		result.setFinderCacheHours(systemConfig.getFinderCacheHours());
		result.setFinderMaxResults(systemConfig.getFinderMaxResults());
		result.setSystemName(systemConfig.getSystemName());
		result.setPhonePrefix(systemConfig.getPhonePrefix());
		result.setWelcomeMessage(systemConfig.getWelcomeMessage());
		result.setSpanishWelcomeMessage(systemConfig.getSpanishWelcomeMessage());
		result.setCharge(systemConfig.getCharge());
		result.setVat(systemConfig.getVat());

		String spamWords = "";

		for (final String s : systemConfig.getSpamWords())
			if (spamWords == "")
				spamWords = s;
			else
				spamWords = spamWords + ", " + s;

		result.setSpamWords(spamWords);

		return result;
	}

	public SystemConfig reconstruct(final SystemConfigForm systemConfigForm) {
		final SystemConfig result = this.findSystemConfiguration();

		result.setBanner(systemConfigForm.getBanner());
		result.setFinderCacheHours(systemConfigForm.getFinderCacheHours());
		result.setFinderMaxResults(systemConfigForm.getFinderMaxResults());
		result.setSystemName(systemConfigForm.getSystemName());
		result.setPhonePrefix(systemConfigForm.getPhonePrefix());
		result.setWelcomeMessage(systemConfigForm.getWelcomeMessage());
		result.setSpanishWelcomeMessage(systemConfigForm.getSpanishWelcomeMessage());
		result.setCharge(systemConfigForm.getCharge());
		result.setVat(systemConfigForm.getVat());

		final Set<String> spamWords = new HashSet<String>();

		final String[] spwords = systemConfigForm.getSpamWords().split(",");
		for (final String string : spwords)
			spamWords.add(string.trim());

		result.setSpamWords(spamWords);
		return result;
	}

	public void flush() {
		this.systemConfigRepository.flush();
	}

}
