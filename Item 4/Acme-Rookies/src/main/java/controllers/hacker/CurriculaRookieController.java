
package controllers.hacker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.RookieService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Rookie;

@Controller
@RequestMapping("/curricula")
public class CurriculaRookieController extends AbstractController {

	public CurriculaRookieController() {
		super();
	}


	@Autowired
	private RookieService		rookieService;
	@Autowired
	private CurriculaService	curriculaService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final Integer curriculaId) {
		ModelAndView result;
		Rookie rookie;
		Curricula curricula;

		rookie = this.rookieService.findByPrincipal();
		curricula = this.curriculaService.findOne(curriculaId);
		Assert.isTrue(rookie.equals(curricula.getRookie()));
		result = new ModelAndView("curricula/display");
		result.addObject("curricula", curricula);
		result.addObject("role", "rookie");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final Integer curriculaId) {
		ModelAndView result;
		Rookie rookie;
		Curricula curricula;

		rookie = this.rookieService.findByPrincipal();
		curricula = this.curriculaService.findOne(curriculaId);
		try {
			Assert.isTrue(curricula.getRookie().equals(rookie));

			this.curriculaService.delete(curricula);
			result = new ModelAndView("curricula/list");
			final Collection<Curricula> curriculas = this.curriculaService.getCurriculaByRookie(rookie);
			final Collection<Curricula> originalCurriculas = this.curriculaService.getOriginalCurricula();
			curriculas.retainAll(originalCurriculas);
			result.addObject("curriculas", curriculas);
			result.addObject("requestURI", "curricula/list.do");
			result.addObject("role", "rookie");

		} catch (final Throwable oops) {
			result = new ModelAndView("curricula/display");
			result.addObject("curricula", curricula);
			result.addObject("requestURI", "curricula/display.do");
			result.addObject("role", "rookie");
			result.addObject("curriculaId", curricula.getId());

		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Rookie rookie;
		Collection<Curricula> curriculas;

		rookie = this.rookieService.findByPrincipal();
		curriculas = this.curriculaService.getCurriculaByRookie(rookie);
		final Collection<Curricula> originalCurriculas = this.curriculaService.getOriginalCurricula();
		curriculas.retainAll(originalCurriculas);

		result = new ModelAndView("curricula/list");
		result.addObject("requestURI", "curricula/list.do");
		result.addObject("curriculas", curriculas);
		result.addObject("role", "rookie");

		return result;
	}
}
