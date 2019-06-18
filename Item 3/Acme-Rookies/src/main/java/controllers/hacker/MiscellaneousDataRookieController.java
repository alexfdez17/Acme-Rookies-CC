
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
import services.RookieService;
import services.MiscellaneousDataService;
import domain.Curricula;
import domain.Rookie;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Controller
@RequestMapping("curricula/miscellaneousData")
public class MiscellaneousDataRookieController {

	@Autowired
	public RookieService			rookieService;
	@Autowired
	public MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	public CurriculaService			curriculaService;


	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		final MiscellaneousData res;

		res = this.miscellaneousDataService.create();
		final MiscellaneousDataForm miscellaneousDataForm = this.miscellaneousDataService.toMiscellaneousDataForm(res);
		miscellaneousDataForm.setCurriculaId(curriculaId);
		result = new ModelAndView("miscellaneousData/edit");
		result.addObject("miscellaneousDataForm", miscellaneousDataForm);
		result.addObject("id", res.getId());
		result.addObject("requestURI", "curricula/miscellaneousData/create.do");
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		final ModelAndView result;
		MiscellaneousData res;

		res = this.miscellaneousDataService.findOne(miscellaneousDataId);
		final MiscellaneousDataForm miscellaneousDataForm = this.miscellaneousDataService.toMiscellaneousDataForm(res);
		final Curricula curricula = this.curriculaService.findByMiscellaneousData(res);
		miscellaneousDataForm.setCurriculaId(curricula.getId());
		result = this.createEditModelAndView(miscellaneousDataForm);
		result.addObject("requestURI", "curricula/miscellaneousData/edit.do");
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("miscellaneousDataForm") @Valid final MiscellaneousDataForm res, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(res);
		else
			try {
				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.reconstruct(res);
				final int curriculaId = res.getCurriculaId();
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				this.miscellaneousDataService.save(miscellaneousData, curricula);
				result = this.display(curricula);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(res, "curricula.commit.error");
			}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		final Rookie rookie = this.rookieService.findByPrincipal();
		final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
		final Curricula curricula = this.curriculaService.findByMiscellaneousData(miscellaneousData);
		try {
			Assert.isTrue(curricula.getRookie().equals(rookie));

			this.miscellaneousDataService.delete(miscellaneousData);
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

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm res) {
		return this.createEditModelAndView(res, null);
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("miscellaneousData/edit");
		result.addObject("miscellaneousDataForm", res);
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
