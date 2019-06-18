
package controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import controllers.AbstractController;
import domain.Curricula;

@Controller
@RequestMapping("/curricula/company")
public class CurriculaCompanyController extends AbstractController {

	public CurriculaCompanyController() {
		super();
	}


	@Autowired
	public CurriculaService	curriculaService;


	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer curriculaId) {

		ModelAndView result;
		Curricula curricula;
		curricula = this.curriculaService.findOne(curriculaId);

		result = new ModelAndView("curricula/company/display");
		result.addObject("curricula", curricula);
		result.addObject("requestURI", "curricula/company/display.do");

		return result;
	}
}
