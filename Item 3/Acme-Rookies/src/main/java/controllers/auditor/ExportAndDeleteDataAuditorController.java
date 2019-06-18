
package controllers.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/exportAndDeleteData/auditor")
public class ExportAndDeleteDataAuditorController extends AbstractController {

	@Autowired
	private AuditorService	auditorService;


	// Delete --------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		final ModelAndView result;

		final Auditor principal = this.auditorService.findByPrincipal();

		this.auditorService.clear(principal);

		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

	// Export --------------------------------------------------------

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export() {
		final ModelAndView result;

		final Auditor principal = this.auditorService.findByPrincipal();

		final String data = this.auditorService.export(principal);

		result = new ModelAndView("export/display");
		result.addObject("data", data);

		return result;
	}

}
