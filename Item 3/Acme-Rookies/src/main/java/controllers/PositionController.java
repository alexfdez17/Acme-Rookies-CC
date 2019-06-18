
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.CompanyService;
import services.PositionService;
import services.SponsorshipService;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;
import domain.Sponsorship;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public PositionController() {
		super();
	}


	@Autowired
	public PositionService		positionService;

	@Autowired
	public CompanyService		companyService;

	@Autowired
	public ActorService			actorService;

	@Autowired
	public AuditService			auditService;

	@Autowired
	public SponsorshipService	sponsorshipService;


	// Positions

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Position> positions;
		positions = this.positionService.findbyFinalMode();
		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do");

		return result;
	}

	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer positionId) {
		ModelAndView result;
		Position position;
		position = this.positionService.findOne(positionId);
		boolean canAudit = true;

		final Collection<Audit> audits = this.auditService.findByPosition(positionId);
		final Collection<Audit> finals = new ArrayList<Audit>();
		for (final Audit a : audits)
			if (!a.isDraftMode())
				finals.add(a);

		result = new ModelAndView("position/display");

		final Sponsorship sponsorship = this.sponsorshipService.charge(position);
		result.addObject("sponsorship", sponsorship);
		if (!finals.isEmpty())
			result.addObject("audits", finals);

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken))
			if (!audits.isEmpty()) {
				final Auditor assigned = audits.iterator().next().getAuditor();
				final Actor principal = this.actorService.findByPrincipal();
				if (assigned.getId() != principal.getId())
					canAudit = false;

			}

		result.addObject("position", position);
		result.addObject("canAudit", canAudit);
		return result;
	}
}
