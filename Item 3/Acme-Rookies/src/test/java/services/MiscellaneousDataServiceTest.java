
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;
import domain.MiscellaneousData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {

	//Sentence coverage of 4.1%
	//1369 instructions

	//SUT
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private CurriculaService			curriculaService;


	@Test
	public void createAndSaveMiscellaneousDataDriver() {
		final Object testingData[][] = {
			{	//Create miscellaneous DATA
				"rookie1", "curricula1", null
			}, {//Anon cant create  miscellaneous data
				null, "curricula1", IllegalArgumentException.class
			}, {//Rookie cant create miscellaneous data for another curricula
				"rookie2", "curricula1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveMiscellaneousDataTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void editDriver() {
		final Object testingData[][] = {
			{	//rookie updates miscellaneous data
				"rookie1", "miscellaneousData1", "curricula1", null
			}, { //Rookie cannot update another one's miscellaneous data
				"rookie2", "miscellaneousData2", "curricula1", IllegalArgumentException.class
			}, { //Only rookie can update miscellaneous data
				"company1", "miscellaneousData1", "curricula1", IllegalArgumentException.class
			}, { //Anon cannot update miscellaneous data
				null, "miscellaneousData1", "curricula1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Ancillary method

	protected void createAndSaveMiscellaneousDataTemplate(final String userName, final String curricula, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(userName);
			final MiscellaneousData miscellaneousData = this.miscellaneousDataService.create();
			miscellaneousData.setFreeText("text");
			final List<String> att = new ArrayList<String>();
			att.add("aaa");
			att.add("wee");
			miscellaneousData.setAttachments(att);
			final Integer curriculaId = super.getEntityId(curricula);
			final Curricula curricula1 = this.curriculaService.findOne(curriculaId);
			this.miscellaneousDataService.save(miscellaneousData, curricula1);
			this.miscellaneousDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String userName, final String miscellaneousDataBeanName, final String curricula, final Class<?> expected) {
		Class<?> caught;
		int miscellaneousDataId;
		MiscellaneousData miscellaneousData;

		caught = null;
		try {
			this.authenticate(userName);
			miscellaneousDataId = super.getEntityId(miscellaneousDataBeanName);
			miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			miscellaneousData.setFreeText("mod");
			final Integer curriculaId = super.getEntityId(curricula);
			final Curricula curricula1 = this.curriculaService.findOne(curriculaId);
			this.miscellaneousDataService.save(miscellaneousData, curricula1);
			this.miscellaneousDataService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
