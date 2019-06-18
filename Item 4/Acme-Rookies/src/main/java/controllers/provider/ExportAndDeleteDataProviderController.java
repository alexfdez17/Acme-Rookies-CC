
package controllers.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import controllers.AbstractController;
import domain.Provider;

@Controller
@RequestMapping("/exportAndDeleteData/provider")
public class ExportAndDeleteDataProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;


	// Delete --------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		final ModelAndView result;

		final Provider principal = this.providerService.findByPrincipal();

		this.providerService.clear(principal);

		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

	// Export --------------------------------------------------------

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export() {
		final ModelAndView result;

		final Provider principal = this.providerService.findByPrincipal();

		final String data = this.providerService.export(principal);

		result = new ModelAndView("export/display");
		result.addObject("data", data);

		return result;
	}

}
