
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;
import domain.Notification;
import domain.SystemConfig;

@Service
@Transactional
public class NotificationService {

	//Managed Repository
	@Autowired
	private NotificationRepository	notificationRepository;

	@Autowired
	private AdministratorService	administratorService;


	// Constructor ----------------------------------------------------

	public NotificationService() {
		super();
	}


	@Autowired
	private SystemConfigService	systemConfigService;


	//CRUD

	public Notification create() {
		final Notification result;

		result = new Notification();
		final Administrator administrator = this.administratorService.findByPrincipal();
		result.setAdministrator(administrator);
		final Date date = new Date();
		result.setMoment(date);
		result.setTags("SYSTEM");

		return result;

	}

	public Notification save(final Notification notification) {
		Assert.notNull(notification);
		final Authority adm = new Authority();
		adm.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adm));
		Notification result;
		result = this.notificationRepository.save(notification);
		return result;
	}

	public Notification send(final Notification notification) {
		final SystemConfig sysConf = this.systemConfigService.findSystemConfiguration();
		if (notification.getSubject().contains("Name change") || notification.getSubject().contains("Rebranding") || notification.getSubject().contains("Name changed") || notification.getSubject().contains("Cambio de nombre")
			|| notification.getSubject().contains("Nombre cambiado")) {
			Assert.isTrue(sysConf.getNameChanged() == false);
			sysConf.setNameChanged(true);
			this.systemConfigService.save(sysConf);
		}
		final Notification result;
		final Administrator administrator = this.administratorService.findByPrincipal();
		notification.setAdministrator(administrator);
		result = this.save(notification);
		return result;
	}

	public void delete(final Notification notification) {
		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);
		Assert.isTrue(this.notificationRepository.exists(notification.getId()));

		this.notificationRepository.delete(notification);
	}

	public Collection<Notification> findAll() {
		Collection<Notification> result;

		result = this.notificationRepository.findAll();

		return result;
	}

	public Notification findOne(final int notificationId) {
		Notification result;

		result = this.notificationRepository.findOne(notificationId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.notificationRepository.flush();
	}

	public Collection<Notification> findByAdministrator(final Administrator administrator) {
		Assert.notNull(administrator);
		Collection<Notification> result;

		result = this.notificationRepository.findByAdministrator(administrator.getId());

		return result;
	}

	// Clear

	public void clear(final Notification notification) {

		this.notificationRepository.delete(notification);
	}

	// Export

	public String export(final Notification notification) {

		final String result = "Notification\n\nMoment: " + notification.getMoment() + "\nSubject: " + notification.getSubject() + "\nBody: " + notification.getBody() + "\nTags: " + notification.getTags() + "\n\n";

		return result;
	}
}
