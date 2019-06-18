
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/exportAndDeleteData/administrator")
public class ExportAndDeleteDataAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	// Delete --------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		final ModelAndView result;

		final Administrator principal = this.administratorService.findByPrincipal();

		this.administratorService.clear(principal);

		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

	// Export --------------------------------------------------------

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export() {
		final ModelAndView result;

		final Administrator principal = this.administratorService.findByPrincipal();

		final String data = this.administratorService.export(principal);

		result = new ModelAndView("export/display");
		result.addObject("data", data);

		return result;
	}

}
