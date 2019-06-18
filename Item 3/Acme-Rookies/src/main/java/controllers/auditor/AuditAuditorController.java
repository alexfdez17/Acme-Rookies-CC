
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import services.NuevaEntidadXXXService;
import services.PositionService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.NuevaEntidadXXX;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService			auditService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private NuevaEntidadXXXService	nuevaEntidadXXXService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Audit> audits;

		final Auditor principal = this.auditorService.findByPrincipal();

		audits = this.auditService.findByAuditor(principal);

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);

		return result;
	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		final ModelAndView result;
		Audit audit;
		Collection<Audit> audits;
		Auditor auditor;
		final Collection<NuevaEntidadXXX> nuevaEntidadXXXs;

		auditor = this.auditorService.findByPrincipal();
		audits = this.auditService.findByAuditor(auditor);
		audit = this.auditService.findOne(auditId);
		Assert.isTrue(audits.contains(audit));

		nuevaEntidadXXXs = this.nuevaEntidadXXXService.findByAudit(audit);

		result = new ModelAndView("audit/display");
		result.addObject("audit", audit);
		result.addObject("nuevaEntidadXXXs", nuevaEntidadXXXs);
		result.addObject("role", "auditor");

		return result;
	}

	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int positionId) {
		ModelAndView result;
		final Audit audit;
		Position position;

		position = this.positionService.findOne(positionId);
		audit = this.auditService.create();
		audit.setPosition(position);

		result = new ModelAndView("audit/create");
		result.addObject("audit", audit);
		result.addObject("id", audit.getId());
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;

		audit = this.auditService.findOne(auditId);

		result = this.createEditModelAndView(audit);
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("audit") Audit audit, final BindingResult binding) {
		ModelAndView result;

		audit = this.auditService.reconstruct(audit, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}

	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, "socialProfile.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Audit audit) {
		return this.createEditModelAndView(audit, null);
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String message) {
		final ModelAndView result;

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("id", audit.getId());
		result.addObject("message", message);
		return result;
	}

}
