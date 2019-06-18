
package controllers.hacker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationDataService;
import services.RookieService;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;
import forms.EducationDataForm;

@Controller
@RequestMapping("curricula/educationData")
public class EducationDataRookieController {

	@Autowired
	public RookieService		rookieService;
	@Autowired
	public EducationDataService	educationDataService;
	@Autowired
	public CurriculaService		curriculaService;


	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		final EducationData res;

		res = this.educationDataService.create();
		final EducationDataForm educationDataForm = this.educationDataService.toEducationDataForm(res);
		educationDataForm.setCurriculaId(curriculaId);
		result = new ModelAndView("educationData/edit");
		result.addObject("educationDataForm", educationDataForm);
		result.addObject("id", res.getId());
		result.addObject("requestURI", "curricula/educationData/create.do");
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		final ModelAndView result;
		EducationData res;

		res = this.educationDataService.findOne(educationDataId);
		final Curricula curricula = this.curriculaService.findByEducationData(res);
		final EducationDataForm educationDataForm = this.educationDataService.toEducationDataForm(res);
		educationDataForm.setCurriculaId(curricula.getId());
		result = this.createEditModelAndView(educationDataForm);
		result.addObject("requestURI", "curricula/educationData/edit.do");
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("educationDataForm") @Valid final EducationDataForm res, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(res);
		else
			try {
				final EducationData educationData = this.educationDataService.reconstruct(res);
				final int curriculaId = res.getCurriculaId();
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				this.educationDataService.save(educationData, curricula);
				result = new ModelAndView("redirect:/curricula/display.do?curriculaId=" + curriculaId);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(res, "curricula.commit.error");
			}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int educationDataId) {
		ModelAndView result;
		final Rookie rookie = this.rookieService.findByPrincipal();
		final EducationData educationData = this.educationDataService.findOne(educationDataId);
		final Curricula curricula = this.curriculaService.findByEducationData(educationData);
		try {
			Assert.isTrue(curricula.getRookie().equals(rookie));

			this.educationDataService.delete(educationData);
			result = new ModelAndView("curricula/display");
			result.addObject("curricula", curricula);
			result.addObject("role", "rookie");
			result.addObject("requestURI", "curricula/display.do");
			result.addObject("curriculaId", curricula.getId());

		} catch (final Throwable oops) {
			result = new ModelAndView("curricula/display");
			result.addObject("curricula", curricula);
			result.addObject("requestURI", "curricula/display.do");
			result.addObject("role", "rookie");
			result.addObject("curriculaId", curricula.getId());

		}
		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationDataForm res) {
		return this.createEditModelAndView(res, null);
	}

	protected ModelAndView createEditModelAndView(final EducationDataForm res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("educationData/edit");
		result.addObject("educationDataForm", res);
		result.addObject("id", res.getId());
		result.addObject("message", message);
		result.addObject("curriculaId", res.getCurriculaId());
		return result;
	}

	public ModelAndView display(final Curricula curricula) {
		final ModelAndView result;

		result = new ModelAndView("curricula/display");
		result.addObject("curricula", curricula);
		result.addObject("requestURI", "curricula/display.do");
		result.addObject("role", "rookie");
		result.addObject("curriculaId", curricula.getId());
		return result;
	}
}
