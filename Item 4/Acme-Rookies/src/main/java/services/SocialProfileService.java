
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	//Managed Repository
	@Autowired
	private SocialProfileRepository	socialprofileRepository;

	//Supporting Services
	@Autowired
	private ActorService			actorService;


	//Constructor
	public SocialProfileService() {
		super();
	}

	public SocialProfile create() {
		SocialProfile socialProfile;
		Actor actor;

		socialProfile = new SocialProfile();
		actor = this.actorService.findByPrincipal();

		socialProfile.setActor(actor);

		return socialProfile;
	}

	public Collection<SocialProfile> findByActor(final Actor actor) {
		return this.socialprofileRepository.getByActorId(actor.getId());
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);

		SocialProfile result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		if (socialProfile.getId() != 0)
			Assert.isTrue(this.findByActor(actor).contains(socialProfile));

		result = this.socialprofileRepository.save(socialProfile);
		return result;
	}

	public void delete(final SocialProfile socialProfile) {
		Actor actor;

		actor = this.actorService.findByPrincipal();
		final Collection<SocialProfile> owned = this.findByActor(actor);

		Assert.isTrue(owned.contains(socialProfile));

		this.socialprofileRepository.delete(socialProfile);
	}

	public Collection<SocialProfile> findAll() {
		Collection<SocialProfile> result;

		result = this.socialprofileRepository.findAll();

		return result;
	}

	public Collection<SocialProfile> findByPincipal() {
		Collection<SocialProfile> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		result = this.findByActor(actor);

		return result;
	}

	public SocialProfile findOne(final int socialIdentityId) {
		SocialProfile socialIdentity;

		socialIdentity = this.socialprofileRepository.findOne(socialIdentityId);
		return socialIdentity;
	}

	public void flush() {
		this.socialprofileRepository.flush();
	}


	// Reconstruct

	@Autowired
	private Validator	validator;


	public SocialProfile reconstruct(final SocialProfile socialProfile, final BindingResult binding) {
		SocialProfile result;

		if (socialProfile.getId() == 0)
			result = socialProfile;
		else {
			final SocialProfile retrieved = this.socialprofileRepository.findOne(socialProfile.getId());
			result = socialProfile;

			result.setActor(retrieved.getActor());

		}

		this.validator.validate(result, binding);
		this.flush();
		return result;
	}

	// Clear

	public void clear(final SocialProfile socialProfile) {

		this.socialprofileRepository.delete(socialProfile);
	}

	// Export

	public String export(final SocialProfile socialProfile) {

		final String result = "Social Profile\n\nNick: " + socialProfile.getNick() + "\nSocial Network: " + socialProfile.getSocialNetwork() + "\nProfile Link: " + socialProfile.getProfileLink() + "\n\n";

		return result;
	}
}
