
package controllers.provider;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship/provider")
public class SponsorshipProviderController extends AbstractController {

	public SponsorshipProviderController() {
		super();
	}


	@Autowired
	public SponsorshipService	sponsorshipService;
	@Autowired
	public ProviderService		providerService;
	@Autowired
	public PositionService		positionService;
	@Autowired
	public SystemConfigService	systemConfigService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Provider provider = this.providerService.findByPrincipal();
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.getFromProvider(provider);
		result = new ModelAndView("sponsorship/provider/list");
		result.addObject("sponsorships", sponsorships);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/provider/display");
		result.addObject("sponsorship", sponsorship);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		final ModelAndView result;

		final Position position = this.positionService.findOne(positionId);
		final Sponsorship sponsorship = this.sponsorshipService.create(position);
		final SponsorshipForm sponsorshipForm = this.sponsorshipService.toSponsorshipForm(sponsorship);
		result = this.createEditModelAndView(sponsorshipForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		final ModelAndView result;
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		final SponsorshipForm sponsorshipForm = this.sponsorshipService.toSponsorshipForm(sponsorship);
		result = this.createEditModelAndView(sponsorshipForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SponsorshipForm sponsorshipForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorshipForm);
		else
			try {
				Sponsorship sponsorship = this.sponsorshipService.reconstruct(sponsorshipForm);
				sponsorship = this.sponsorshipService.update(sponsorship);
				result = new ModelAndView("redirect:/sponsorship/provider/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorshipForm, "sponsorship.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		this.sponsorshipService.delete(sponsorship);
		result = new ModelAndView("redirect:/sponsorship/provider/list.do");

		return result;
	}
	protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm) {
		return this.createEditModelAndView(sponsorshipForm, null);
	}

	protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("sponsorship/provider/edit");
		result.addObject("sponsorshipForm", sponsorshipForm);
		result.addObject("message", message);
		return result;
	}
}
