
package controllers.hacker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RookieService;
import controllers.AbstractController;
import domain.Rookie;

@Controller
@RequestMapping("/exportAndDeleteData/rookie")
public class ExportAndDeleteDataRookieController extends AbstractController {

	@Autowired
	private RookieService	rookieService;


	// Delete --------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		final ModelAndView result;

		final Rookie principal = this.rookieService.findByPrincipal();

		this.rookieService.clear(principal);

		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

	// Export --------------------------------------------------------

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export() {
		final ModelAndView result;

		final Rookie principal = this.rookieService.findByPrincipal();

		final String data = this.rookieService.export(principal);

		result = new ModelAndView("export/display");
		result.addObject("data", data);

		return result;
	}

}
