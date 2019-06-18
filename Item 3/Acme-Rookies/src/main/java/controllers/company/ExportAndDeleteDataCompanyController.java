
package controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import controllers.AbstractController;
import domain.Company;

@Controller
@RequestMapping("/exportAndDeleteData/company")
public class ExportAndDeleteDataCompanyController extends AbstractController {

	@Autowired
	private CompanyService	companyService;


	// Delete --------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		final ModelAndView result;

		final Company principal = this.companyService.findByPrincipal();

		this.companyService.clear(principal);

		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

	// Export --------------------------------------------------------

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export() {
		final ModelAndView result;

		final Company principal = this.companyService.findByPrincipal();

		final String data = this.companyService.export(principal);

		result = new ModelAndView("export/display");
		result.addObject("data", data);

		return result;
	}

}
