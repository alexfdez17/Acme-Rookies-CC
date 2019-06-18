
package controllers.hacker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PersonalDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.PersonalData;

@Controller
@RequestMapping("/curricula/personalData")
public class PersonalDataRookieController extends AbstractController {

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private CurriculaService	curriculaService;


	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PersonalData res;

		res = this.personalDataService.create();

		result = new ModelAndView("personalData/edit");
		result.addObject("personalData", res);
		result.addObject("id", res.getId());
		result.addObject("requestURI", "curricula/personalData/create.do");
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		PersonalData res;

		res = this.personalDataService.findOne(personalDataId);

		result = this.createEditModelAndView(res);
		result.addObject("requestURI", "curricula/personalData/edit.do");
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("personalData") @Valid final PersonalData res, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(res);
		else
			try {
				this.personalDataService.save(res);
				result = new ModelAndView("redirect:/curricula/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(res, "record.commit.error");
			}
		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PersonalData res) {
		return this.createEditModelAndView(res, null);
	}

	protected ModelAndView createEditModelAndView(final PersonalData res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("personalData/edit");
		result.addObject("personalData", res);
		result.addObject("id", res.getId());
		result.addObject("message", message);
		result.addObject("curricula", this.curriculaService.findByPersonalData(res));
		return result;
	}

	public ModelAndView display(final Curricula curricula) {
		final ModelAndView result;

		result = new ModelAndView("curricula/display");
		result.addObject("curricula", curricula);
		result.addObject("requestURI", "curricula/display.do");
		result.addObject("curriculaId", curricula.getId());
		return result;
	}

}
