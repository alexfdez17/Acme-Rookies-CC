
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Administrator;
import domain.Message;

@Service
@Transactional
public class ActorService {

	// Managed Repository ----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting Services --------------------------------------------------
	@Autowired
	private SystemConfigService		systemConfigService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private MessageRepository		messageRepository;


	// Constructor ----------------------------------------------------

	public ActorService() {
		super();
	}

	//CRUD

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;
		result = this.actorRepository.save(actor);
		this.actorRepository.flush();
		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));
		final int id = LoginService.getPrincipal().getId();
		final int actorid = actor.getId();
		Assert.isTrue(id == actorid || LoginService.getPrincipal().getAuthorities().contains(Authority.ADMIN));

		this.actorRepository.delete(actor);
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Collection<Actor> findAllButPrincipal() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		final Actor principal = this.findByPrincipal();
		result.remove(principal);
		return result;
	}
	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.actorRepository.flush();
	}

	// Other Business Methods

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;
		result = this.actorRepository.findByUserAccountID(userAccount.getId());

		return result;
	}

	public Actor findByUsername(final String username) {
		return this.actorRepository.findByUsername(username);
	}

	public Boolean isSpam(final String string) {
		Boolean result = false;
		String lower;
		if (string != null) {
			lower = string.toLowerCase();

			for (final String s : this.systemConfigService.findSystemConfiguration().getSpamWords())
				if (lower.contains(s)) {
					result = true;
					return result;
				}
		}
		return result;
	}

	public boolean banActor(final UserAccount uc) {
		Actor principal;

		principal = this.findByPrincipal();

		Assert.isTrue(principal instanceof Administrator);

		final boolean result = this.ban(uc);

		return result;

	}
	public boolean ban(final UserAccount uc) {
		Assert.notNull(uc);

		boolean result;
		result = false;

		try {
			uc.setAccountNonLocked(false);
			this.userAccountRepository.save(uc);
			result = true;
		} catch (final Exception e) {
			result = false;
		}

		return result;
	}

	public boolean unbanActor(final UserAccount uc) {
		Assert.notNull(uc);

		boolean result;
		Actor principal;

		principal = this.findByPrincipal();
		result = false;

		Assert.isTrue(principal instanceof Administrator);
		try {
			uc.setAccountNonLocked(true);
			this.userAccountRepository.save(uc);
			result = true;
		} catch (final Exception e) {
			result = false;
		}

		return result;
	}

	public void isSpammer(final Actor actor) {

		final Collection<Message> spamMessages = this.getSpamMessages(actor.getId());
		final Collection<Message> nonSpamMessages = this.getNonSpamMessages(actor.getId());
		final double spamMessagesCount = spamMessages.size();
		final double nonSpamMessagesCount = nonSpamMessages.size();
		final double messagesCount = spamMessagesCount + nonSpamMessagesCount;
		if (nonSpamMessagesCount == 0 && spamMessagesCount == 0)
			actor.setSpammer(false);
		else if (nonSpamMessagesCount == 0 && spamMessagesCount > 0)
			actor.setSpammer(true);
		else if (nonSpamMessagesCount > 0 && spamMessagesCount == 0)
			actor.setSpammer(false);
		else {
			final double ratio = spamMessagesCount / messagesCount;
			if (ratio > 0.1)
				actor.setSpammer(true);
			else
				actor.setSpammer(false);
		}

		this.actorRepository.save(actor);

	}

	public void evaluateSpammers() {
		final Collection<Actor> actors = this.findAll();
		for (final Actor actor : actors)
			this.isSpammer(actor);
	}

	public Collection<Message> getSpamMessages(final int actorId) {
		return this.messageRepository.findSpamBySender(actorId);
	}

	public Collection<Message> getNonSpamMessages(final int actorId) {
		return this.messageRepository.findNonSpamBySender(actorId);
	}
}
