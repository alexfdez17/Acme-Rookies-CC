
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	//Managed Repository
	@Autowired
	private CreditCardRepository	creditCardRepository;


	// Constructor ----------------------------------------------------

	public CreditCardService() {
		super();
	}

	//CRUD

	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();

		return result;

	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;
		result = this.creditCardRepository.save(creditCard);
		this.creditCardRepository.flush();
		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.creditCardRepository.flush();
	}
}
