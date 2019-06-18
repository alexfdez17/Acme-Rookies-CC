
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
import services.PositionDataService;
import services.RookieService;
import domain.Curricula;
import domain.PositionData;
import domain.Rookie;
import forms.PositionDataForm;

@Controller
@RequestMapping("curricula/positionData")
public class PositionDataRookieController {

	@Autowired
	public RookieService		rookieService;
	@Autowired
	public PositionDataService	positionDataService;
	@Autowired
	public CurriculaService		curriculaService;


	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		final PositionData res;

		res = this.positionDataService.create();
		final PositionDataForm positionDataForm = this.positionDataService.toPositionDataForm(res);
		positionDataForm.setCurriculaId(curriculaId);
		result = new ModelAndView("positionData/edit");
		result.addObject("positionDataForm", positionDataForm);
		result.addObject("id", res.getId());
		result.addObject("requestURI", "curricula/positionData/create.do");
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionDataId) {
		final ModelAndView result;
		PositionData res;

		res = this.positionDataService.findOne(positionDataId);
		final PositionDataForm positionDataForm = this.positionDataService.toPositionDataForm(res);
		final Curricula curricula = this.curriculaService.findByPositionData(res);
		positionDataForm.setCurriculaId(curricula.getId());
		result = this.createEditModelAndView(positionDataForm);
		result.addObject("requestURI", "curricula/positionData/edit.do");
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("positionDataForm") @Valid final PositionDataForm res, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(res);
		else
			try {
				final PositionData positionData = this.positionDataService.reconstruct(res);
				final int curriculaId = res.getCurriculaId();
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				this.positionDataService.save(positionData, curricula);
				result = new ModelAndView("redirect:/curricula/display.do?curriculaId=" + curriculaId);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(res, "curricula.commit.error");
			}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionDataId) {
		ModelAndView result;
		final Rookie rookie = this.rookieService.findByPrincipal();
		final PositionData positionData = this.positionDataService.findOne(positionDataId);
		final Curricula curricula = this.curriculaService.findByPositionData(positionData);
		try {
			Assert.isTrue(curricula.getRookie().equals(rookie));

			this.positionDataService.delete(positionData);
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

	protected ModelAndView createEditModelAndView(final PositionDataForm res) {
		return this.createEditModelAndView(res, null);
	}

	protected ModelAndView createEditModelAndView(final PositionDataForm res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("positionData/edit");
		result.addObject("positionDataForm", res);
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
