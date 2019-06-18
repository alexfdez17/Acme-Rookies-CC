
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Position;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//Sentence coverage of 4.4%
	//1473 instructions

	//SUT
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private ProviderService		providerService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	// Sponsorship correct creation
				"provider3", "position1", "https://link.com", "https://link.com", "provider3CreditCard", null
			}, {	// Sponsorship creation with target page not being a link
				"provider3", "position1", "https://link.com", "notalink", "provider3CreditCard", ConstraintViolationException.class
			}, {	// Sponsorship creation with banner not being a link
				"provider3", "position1", "notalink", "https://link.com", "provider3CreditCard", ConstraintViolationException.class
			}, {	// Sponsorship creation by a non-provider user
				"rookie1", "position1", "notalink", "https://link.com", "provider3CreditCard", NullPointerException.class
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
	public void deleteDriver() {
		final Object testingData[][] = {
			{	// Sponsorship correct delete
				"provider1", "sponsorship1", null
			}, {	// Provider deleting a sponsorship that doesn't own
				"provider3", "sponsorship1", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String provider, final String positionstr, final String banner, final String targetPage, final String creditcardstr, final Class<?> expected) {
		Class<?> caught;
		Sponsorship sponsorship;
		Position position;
		CreditCard creditCard;
		caught = null;
		try {
			this.authenticate(provider);
			final int positionId = this.getEntityId(positionstr);
			position = this.positionService.findOne(positionId);
			sponsorship = this.sponsorshipService.create(position);
			final int creditCardId = this.getEntityId(creditcardstr);
			creditCard = this.creditCardService.findOne(creditCardId);
			sponsorship.setBanner(banner);
			sponsorship.setcreditcard(creditCard);
			sponsorship.setTargetPage(targetPage);
			sponsorship.setProvider(this.providerService.findByPrincipal());
			this.sponsorshipService.update(sponsorship);
			this.sponsorshipService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void deleteTemplate(final String provider, final String sponsorshipstr, final Class<?> expected) {
		Class<?> caught;
		int sponsorshipId;
		final Sponsorship sponsorship;

		caught = null;
		try {
			this.authenticate(provider);
			sponsorshipId = super.getEntityId(sponsorshipstr);
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			this.sponsorshipService.delete(sponsorship);
			this.sponsorshipService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
