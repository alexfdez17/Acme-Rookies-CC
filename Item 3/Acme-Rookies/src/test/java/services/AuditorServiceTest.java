
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Auditor;
import forms.RegisterAuditorForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorServiceTest extends AbstractTest {

	//Sentence coverage of 9.8%
	//3263 instructions

	//SUT
	@Autowired
	private AuditorService	auditorService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	//Normal creation of actor
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, null
			}, {//Passwords not matching
				"admin", "password", "notSamePassWord", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, IllegalArgumentException.class
			}, {//IncorrectEmail
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect name
				"admin", "password", "password", "usename", "", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect surname
				"admin", "password", "password", "usename", "name", "", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect photo
				"admin", "password", "password", "usename", "name", "surname", "thisIsNotAURL", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect holder
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "", "make", "375855639752272", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect make
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "", "375855639752272", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect number
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "", 3, 22, 333, ConstraintViolationException.class
			}, {//Incorrect number
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "87398469", 3, 22, 333, ConstraintViolationException.class
			}, {//ExpirationMonth 0
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 0, 22, 333, ConstraintViolationException.class
			}, {//ExpirationMonth 13
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 13, 22, 333, ConstraintViolationException.class
			}, {//ExpirationMonth 1
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 1, 22, 333, null
			}, {//ExpirationMonth 12
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 12, 22, 333, null
			}, {//ExpirationYear 0
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 0, 333, null
			}, {//ExpirationYear 99
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 99, 333, null
			}, {//ExpirationYear 100
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 100, 333, ConstraintViolationException.class
			}, {//CVV 99
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 99, ConstraintViolationException.class
			}, {//CVV 100
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 100, null
			}, {//CVV 999
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 999, null
			}, {//CVV 1000
				"admin", "password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 1000, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (int) testingData[i][14],
					(int) testingData[i][15], (int) testingData[i][16], (Class<?>) testingData[i][17]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void EditAndSaveDriver() {
		final Object testingData[][] = {
			{	//Normal creation of actor
				"newName", "newSurname", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, null
			}, { //Incorrect Name
				"", "newSurname", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, ConstraintViolationException.class
			}, { //Incorrect Surname
				"newName", "", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, ConstraintViolationException.class
			}, { //Incorrect Photo
				"newName", "newSurname", "thisIsNotAURL", "newEmail@acme.com", "newAddress", "+34 555555555", 4, ConstraintViolationException.class
			}, { //Incorrect email
				"newName", "newSurname", "https://newPhoto.com", "", "newAddress", "+34 555555555", 4, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.EditAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (int) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String userNameAdmin, final String password, final String password2, final String userName, final String name, final String surname, final String photo, final String email, final String address,
		final String phone, final int vat, final String holder, final String make, final String number, final int expirationMonth, final int expirationYear, final int cvv, final Class<?> expected) {
		Class<?> caught;
		final RegisterAuditorForm registerAuditorForm = new RegisterAuditorForm();

		caught = null;
		try {
			this.authenticate(userNameAdmin);
			registerAuditorForm.setPassword(password);
			registerAuditorForm.setPassword2(password2);
			registerAuditorForm.setUsername(userName);
			registerAuditorForm.setName(name);
			registerAuditorForm.setSurname(surname);
			registerAuditorForm.setPhone(phone);
			registerAuditorForm.setPhoto(photo);
			registerAuditorForm.setAddress(address);
			registerAuditorForm.setEmail(email);
			registerAuditorForm.setVAT(vat);
			registerAuditorForm.setHolder(holder);
			registerAuditorForm.setMake(make);
			registerAuditorForm.setNumber(number);
			registerAuditorForm.setExpirationMonth(expirationMonth);
			registerAuditorForm.setExpirationYear(expirationYear);
			registerAuditorForm.setCvv(cvv);
			this.auditorService.registerAuditor(registerAuditorForm);
			this.auditorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void EditAndSaveTemplate(final String name, final String surname, final String photo, final String email, final String address, final String phone, final int vat, final Class<?> expected) {
		Class<?> caught;
		Auditor auditor;
		int auditorId;
		caught = null;
		try {
			auditorId = this.getEntityId("auditor1");
			auditor = this.auditorService.findOne(auditorId);
			auditor.setName(name);
			auditor.setSurname(surname);
			auditor.setPhone(phone);
			auditor.setPhoto(photo);
			auditor.setAddress(address);
			auditor.setEmail(email);
			auditor.setVat(vat);
			this.auditorService.save(auditor);
			this.auditorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
