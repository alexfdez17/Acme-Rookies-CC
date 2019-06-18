
package controllers.auditor;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.NuevaEntidadXXXService;
import controllers.AbstractController;
import domain.Auditor;
import domain.NuevaEntidadXXX;

@Controller
@RequestMapping("/nuevaEntidadXXX/auditor")
public class NuevaEntidadXXXAuditorController extends AbstractController {

	@Autowired
	private NuevaEntidadXXXService	nuevaEntidadXXXService;

	@Autowired
	private AuditorService			auditorService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<NuevaEntidadXXX> nuevaEntidadXXXs;

		final Auditor principal = this.auditorService.findByPrincipal();

		nuevaEntidadXXXs = this.nuevaEntidadXXXService.findByAuditor(principal);

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

}
