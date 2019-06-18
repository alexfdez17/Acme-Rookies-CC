
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	//Sentence coverage of 3.5%
	//1155 instructions

	//SUT
	@Autowired
	private SocialProfileService	socialProfileService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	// Social profile correct creation
				"rookie1", "sampleNick", "sampleSocialNetwork", "https://sampleProfileLink.com", null
			}, {	// A user that is not authenticated cannot create a social profile
				null, "sampleNick", "sampleSocialNetwork", "https://sampleProfileLink.com", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void deleteDriver() {
		final Object testingData[][] = {
			{	// Social profile correct delete
				"rookie1", "socialProfile1", null
			}, { //An actor cannot delete a social profile from another actor
				"rookie2", "socialProfile1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String userName, final String nick, final String socialNetwork, final String profileLink, final Class<?> expected) {
		Class<?> caught;
		SocialProfile socialProfile;

		caught = null;
		try {
			this.authenticate(userName);
			socialProfile = this.socialProfileService.create();
			socialProfile.setNick(nick);
			socialProfile.setSocialNetwork(socialNetwork);
			socialProfile.setProfileLink(profileLink);

			this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void deleteTemplate(final String userName, final String socialProfileBeanName, final Class<?> expected) {
		Class<?> caught;
		int socialProfileId;
		final SocialProfile socialProfile;

		caught = null;
		try {
			this.authenticate(userName);
			socialProfileId = super.getEntityId(socialProfileBeanName);
			socialProfile = this.socialProfileService.findOne(socialProfileId);
			this.socialProfileService.delete(socialProfile);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
