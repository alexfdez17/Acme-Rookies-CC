
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Application;
import domain.Finder;
import domain.Message;
import domain.Position;

@Service
@Transactional
public class MessageService {

	//Managed Repository
	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructor ----------------------------------------------------

	public MessageService() {
		super();
	}

	//CRUD

	public Message create() {
		Message result;

		result = new Message();
		final Actor sender = this.actorService.findByPrincipal();
		result.setSender(sender);
		final Date date = new Date();
		result.setMoment(date);
		return result;

	}

	public Message save(final Message message) {
		Assert.notNull(message);
		Message result;
		result = message;
		result.setSpam(this.isSpam(message));
		result = this.messageRepository.save(message);
		this.messageRepository.flush();
		return result;
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(this.messageRepository.exists(message.getId()));

		this.messageRepository.delete(message);
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Message> findByRecipient(final Actor recipient) {
		return this.messageRepository.findbyRecipient(recipient.getId());
	}

	public Collection<Message> findBySender(final Actor sender) {
		return this.messageRepository.findbySender(sender.getId());
	}

	public Collection<Message> findPrincipalMessages() {
		final Actor principal = this.actorService.findByPrincipal();
		return this.findByRecipient(principal);
	}

	public Collection<Message> findPrincipalMessagesFromTag(final String tag) {
		final Actor principal = this.actorService.findByPrincipal();
		Collection<Message> result;
		if (tag.equals(""))
			result = this.findPrincipalMessages();
		else
			result = this.messageRepository.findbyRecipientAndTag(principal.getId(), tag);
		return result;
	}

	public Message send(final Message message) {
		Message result;
		final Actor sender = this.actorService.findByPrincipal();
		Assert.isTrue(!message.getRecipient().equals(sender));
		message.setSender(sender);
		final Date date = new Date();
		message.setMoment(date);
		result = this.save(message);
		return result;
	}

	public void remove(final Message message) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(message.getRecipient().equals(principal));
		if (message.getTags().equals("DELETED"))
			this.delete(message);
		else {
			message.setTags("DELETED");
			this.save(message);
		}
	}

	public Message applicationNotification(final Application application) {
		Message result;

		result = this.create();
		result.setSubject("ESTADO DE APLICACION CAMBIADO / APPLICATION STATUS CHANGED");
		final String positionProblemTitle = application.getPositionProblem().getTitle();
		final String applicationStatus = application.getStatus();
		result.setBody("YOUR APPLICATION TO POSITION PROBLEM " + positionProblemTitle + " HAS CHANGED IT'S STATUS TO " + applicationStatus + "\n" + "SU APLICACION AL PROBLEMA " + positionProblemTitle + " HA CAMBIADO SU ESTADO A" + applicationStatus);
		result.setTags("SYSTEM");
		result.setSender(application.getPositionProblem().getPosition().getCompany());
		result.setRecipient(application.getRookie());
		result = this.save(result);
		return result;
	}

	public Message finderNotification(final Finder finder, final Position position) {
		Message result;

		result = this.create();
		Boolean matchesFinder = false;
		final String keyword = finder.getKeyword();
		if (!(keyword.isEmpty() || keyword == null))
			if (position.getTitle().matches(".*" + keyword + ".*") || position.getDescription().matches(".*" + keyword + ".*") || position.getTicker().matches(".*" + keyword + ".*") || position.getSkillsRequired().matches(".*" + keyword + ".*")
				|| position.getTechnologiesRequired().matches(".*" + keyword + ".*"))
				matchesFinder = true;
			else {
				matchesFinder = false;
				return null;
			}
		else
			matchesFinder = true;

		if (position.getSalaryOffered() > finder.getMinimumSalary())
			matchesFinder = true;
		else {
			matchesFinder = false;
			return null;
		}

		if (finder.getMaximumDeadLine() != null) {
			if (position.getDeadLine().before(finder.getMaximumDeadLine()))
				matchesFinder = true;
			else {
				matchesFinder = false;
				return null;
			}

		} else
			matchesFinder = true;

		if (matchesFinder) {
			result.setSubject("A NEW POSITION THAT MATCHES YOUR FINDER PARAMETERS HAS BEEN CREATED / UNA NUEVA POSICION QUE CUMPLE CON SUS PARAMETROS DE BUSQUEDA HA SIDO CREADA");
			result.setBody("NEW POSITION TITLE IS " + position.getTitle() + "\n" + "EL TITULO DE LA NUEVA POSICION ES" + position.getTitle());
			result.setTags("SYSTEM");
			result.setSender(position.getCompany());
			result.setRecipient(finder.getRookie());
			result = this.save(result);
		}

		return result;

	}

	public void newPositionNotification(final Position position) {
		final Collection<Finder> finders = this.finderService.findAll();

		for (final Finder finder : finders)
			this.finderNotification(finder, position);
	}

	public boolean isSpam(final Message message) {
		boolean result = false;

		final Collection<String> spamWords = this.systemConfigService.findSystemConfiguration().getSpamWords();

		for (final String spamWord : spamWords)
			if (message.getBody().matches(".*" + spamWord + ".*") || message.getSubject().matches(".*" + spamWord + ".*") || message.getTags().matches(".*" + spamWord + ".*")) {
				result = true;
				break;
			}

		return result;
	}
	public void flush() {
		this.messageRepository.flush();
	}

	// Clear

	public void clear(final Message message) {

		this.messageRepository.delete(message);
	}

	// Export

	public String export(final Message message) {

		final String result = "Message\n\nSender: " + message.getSender().getName() + "\nRecipient: " + message.getRecipient().getName() + "\nMoment: " + message.getMoment() + "\nSubject: " + message.getSubject() + "\nBody: " + message.getBody()
			+ "\nTags: " + message.getTags() + "\nSpam: " + message.isSpam() + "\n\n";

		return result;
	}

}
