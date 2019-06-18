
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import domain.Notification;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Sentence coverage of 5.2%
	//1724 instructions

	@Autowired
	private MessageService		messageService;
	@Autowired
	private NotificationService	notificationService;
	@Autowired
	private MessageRepository	messageRepository;
	@Autowired
	private ActorService		actorService;


	@Test
	public void createAndSaveDriver() {

		final Object testingData[][] = {
			{	// Message send
				"rookie1", "Hi", "I'm testing the message system", "TEST", "company1", null
			}, {	// Cannot send message to yourself
				"rookie1", "Hi", "I'm testing the message system", "TEST", "rookie1", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	@Test
	public void createAndSaveNotificationDriver() {

		final Object testingData[][] = {
			{	// Message send
				"admin", "Hi", "I'm testing the message system", "TEST", null
			}, {	// Cannot send notification from a user that is not an admin
				"rookie1", "Hi", "I'm testing the message system", "TEST", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveNotificationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	private void createAndSaveTemplate(final String sender, final String subject, final String body, final String tags, final String recipient, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final Message message = this.messageService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setTags(tags);
			final int recipientId = this.getEntityId(recipient);
			final Actor rec = this.actorService.findOne(recipientId);
			message.setRecipient(rec);
			this.messageService.send(message);
			this.messageService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private void createAndSaveNotificationTemplate(final String sender, final String subject, final String body, final String tags, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final Notification message = this.notificationService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setTags(tags);
			this.notificationService.send(message);
			this.notificationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void createAndDeleteMessage() {
		this.authenticate("company1");
		Message message = this.messageService.create();
		message.setSubject("Test");
		message.setBody("This is gonna be deleted");
		message.setTags("TEST");
		final int recipientId = this.getEntityId("rookie1");
		final Actor rec = this.actorService.findOne(recipientId);
		message.setRecipient(rec);
		message = this.messageService.send(message);
		this.messageService.flush();
		this.unauthenticate();
		this.authenticate("rookie1");
		this.messageService.remove(message);
		this.messageService.flush();
		Assert.isTrue(this.messageRepository.exists(message.getId()));
		Assert.isTrue(this.messageService.findOne(message.getId()).getTags().equals("DELETED"));
		this.messageService.remove(message);
		this.messageService.flush();
		Assert.isTrue(!this.messageRepository.exists(message.getId()));
	}
}
