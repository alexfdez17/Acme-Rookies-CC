
package controllers.company;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.CompanyService;
import services.NuevaEntidadXXXService;
import controllers.AbstractController;
import domain.Audit;
import domain.Company;
import domain.NuevaEntidadXXX;

@Controller
@RequestMapping("/nuevaEntidadXXX/company")
public class NuevaEntidadXXXCompanyController extends AbstractController {

	@Autowired
	private NuevaEntidadXXXService	nuevaEntidadXXXService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AuditService			auditService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<NuevaEntidadXXX> nuevaEntidadXXXs;

		final Company principal = this.companyService.findByPrincipal();

		nuevaEntidadXXXs = this.nuevaEntidadXXXService.findByCompany(principal);

		final Date onemonth = new GregorianCalendar(2019, Calendar.MAY, 24).getTime();
		final Date twomonths = new GregorianCalendar(2019, Calendar.APRIL, 24).getTime();

		result = new ModelAndView("nuevaEntidadXXX/list");
		result.addObject("onemonth", onemonth);
		result.addObject("twomonths", twomonths);
		result.addObject("nuevaEntidadXXXs", nuevaEntidadXXXs);

		return result;
	}
	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int nuevaEntidadXXXId) {
		final ModelAndView result;
		NuevaEntidadXXX nuevaEntidadXXX;

		nuevaEntidadXXX = this.nuevaEntidadXXXService.findOne(nuevaEntidadXXXId);

		result = new ModelAndView("nuevaEntidadXXX/display");
		result.addObject("nuevaEntidadXXX", nuevaEntidadXXX);

		return result;
	}

	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int auditId) {
		ModelAndView result;
		final NuevaEntidadXXX nuevaEntidadXXX;
		Audit audit;

		audit = this.auditService.findOne(auditId);
		nuevaEntidadXXX = this.nuevaEntidadXXXService.create();
		nuevaEntidadXXX.setAudit(audit);

		result = new ModelAndView("nuevaEntidadXXX/create");
		result.addObject("nuevaEntidadXXX", nuevaEntidadXXX);
		result.addObject("id", nuevaEntidadXXX.getId());
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int nuevaEntidadXXXId) {
		ModelAndView result;
		NuevaEntidadXXX nuevaEntidadXXX;

		nuevaEntidadXXX = this.nuevaEntidadXXXService.findOne(nuevaEntidadXXXId);

		result = this.createEditModelAndView(nuevaEntidadXXX);
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("nuevaEntidadXXX") NuevaEntidadXXX nuevaEntidadXXX, final BindingResult binding) {
		ModelAndView result;

		nuevaEntidadXXX = this.nuevaEntidadXXXService.reconstruct(nuevaEntidadXXX, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(nuevaEntidadXXX);
		else
			try {
				this.nuevaEntidadXXXService.save(nuevaEntidadXXX);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(nuevaEntidadXXX, "nuevaEntidadXXX.commit.error");
			}
		return result;
	}

	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(NuevaEntidadXXX nuevaEntidadXXX, final BindingResult bindingResult) {
		ModelAndView result;

		nuevaEntidadXXX = this.nuevaEntidadXXXService.reconstruct(nuevaEntidadXXX, bindingResult);
		try {
			this.nuevaEntidadXXXService.delete(nuevaEntidadXXX);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(nuevaEntidadXXX, "nuevaEntidadXXX.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final NuevaEntidadXXX nuevaEntidadXXX) {
		return this.createEditModelAndView(nuevaEntidadXXX, null);
	}

	protected ModelAndView createEditModelAndView(final NuevaEntidadXXX nuevaEntidadXXX, final String message) {
		final ModelAndView result;

		result = new ModelAndView("nuevaEntidadXXX/edit");
		result.addObject("nuevaEntidadXXX", nuevaEntidadXXX);
		result.addObject("id", nuevaEntidadXXX.getId());
		result.addObject("message", message);
		return result;
	}

}
