/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.AuditService;
import services.CompanyService;
import services.CurriculaService;
import services.FinderService;
import services.ItemService;
import services.PositionService;
import services.ProviderService;
import services.RookieService;
import domain.Actor;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;
import forms.BroadcastForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RookieService rookieService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private FinderService finderService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ProviderService providerService;

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/computeAuditScore", method = RequestMethod.GET)
	public ModelAndView computeAuditScores() {
		Assert.notNull(this.adminService.findByPrincipal());
		this.adminService.calculateEveryAuditScore();
		final ModelAndView result = new ModelAndView("redirect:/");
		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		Double[] ppc = new Double[4];

		ppc = this.positionService.positionsPerCompanyStats();

		if (ppc == null) {
			ppc = new Double[4];
			ppc[0] = 0.;
			ppc[1] = 0.;
			ppc[2] = 0.;
			ppc[3] = 0.;

		}

		Double[] aph = new Double[4];

		aph = this.applicationService.applicationsPerRookieStats();

		if (aph == null) {
			aph = new Double[4];
			aph[0] = 0.;
			aph[1] = 0.;
			aph[2] = 0.;
			aph[3] = 0.;

		}

		final Collection<Company> morePositions = this.companyService
				.getCompaniesMorePositions();

		final Collection<Rookie> moreApplications = this.rookieService
				.getRookiesMoreApplications();

		Double[] so = new Double[4];

		so = this.positionService.getSalariesOfferedStats();

		if (so == null) {
			so = new Double[4];
			so[0] = 0.;
			so[1] = 0.;
			so[2] = 0.;
			so[3] = 0.;

		}

		Position better = this.positionService.getTopPosition();

		if (better == null) {
			better = new Position();
			better.setTitle("No hay ninguna posición");
		}

		Position worst = this.positionService.getBotPosition();

		if (worst == null) {
			worst = new Position();
			worst.setTitle("No hay ninguna posición");
		}

		Double[] cph = new Double[4];

		cph = this.curriculaService.curriculaPerRookieStats();
		
		if (cph == null) {
			cph = new Double[4];
			cph[0] = 0.;
			cph[1] = 0.;
			cph[2] = 0.;
			cph[3] = 0.;

		}

		Double[] fr = new Double[4];

		fr = this.finderService.getFinderResultsStats();

		if (fr == null) {
			fr = new Double[4];
			fr[0] = 0.;
			fr[1] = 0.;
			fr[2] = 0.;
			fr[3] = 0.;

		}

		final Double empvsful = this.finderService.emptyVsFullRatio();

		Double[] ps = new Double[4];

		ps = this.auditService.getAuditScore();

		if (ps == null) {
			ps = new Double[4];
			ps[0] = 0.;
			ps[1] = 0.;
			ps[2] = 0.;
			ps[3] = 0.;

		}

		Double[] cs = new Double[4];

		cs = this.companyService.getAuditCompanyScore();

		if (cs == null) {
			cs = new Double[4];
			cs[0] = 0.;
			cs[1] = 0.;
			cs[2] = 0.;
			cs[3] = 0.;

		}

		final Collection<Company> highestScore = this.companyService
				.getTopScoreCompanies();

		Double avgSalaryTop = this.positionService.getAvgTopPositionsSalary();

		Double[] ipp = new Double[4];

		ipp = this.itemService.getItemsProviderStats();

		if (ipp == null) {
			ipp = new Double[4];
			ipp[0] = 0.;
			ipp[1] = 0.;
			ipp[2] = 0.;
			ipp[3] = 0.;

		}

		Collection<Provider> topProviders = this.itemService.getTopProviders();

		Double[] spp = new Double[4];

		spp = this.providerService.getSponsorshipProviderStats();

		if (spp == null) {
			spp = new Double[4];
			spp[0] = 0.;
			spp[1] = 0.;
			spp[2] = 0.;
			spp[3] = 0.;

		}

		Double[] sppo = new Double[4];

		sppo = this.positionService.getSponsorshipsPositionStats();

		if (sppo == null) {
			sppo = new Double[4];
			sppo[0] = 0.;
			sppo[1] = 0.;
			sppo[2] = 0.;
			sppo[3] = 0.;

		}

		Collection<Provider> providersMAVG=this.providerService.getSponsorshipProviderMAVG();

		result = new ModelAndView("administrator/dashboard");

		// PositionsPerCompany
		result.addObject("maximumppc", ppc[2]);
		result.addObject("minimumppc", ppc[1]);
		result.addObject("averageppc", ppc[0]);
		result.addObject("stdevppc", ppc[3]);

		// ApplicationsPerRookie
		result.addObject("maximumaph", aph[2]);
		result.addObject("minimumaph", aph[1]);
		result.addObject("averageaph", aph[0]);
		result.addObject("stdevaph", aph[3]);

		result.addObject("morePositions", morePositions);
		result.addObject("moreApplications", moreApplications);

		// SalariesOffered
		result.addObject("maximumso", so[2]);
		result.addObject("minimumso", so[1]);
		result.addObject("averageso", so[0]);
		result.addObject("stdevso", so[3]);

		result.addObject("better", better);
		result.addObject("worst", worst);

		// CurriculaPerRookie
		result.addObject("maximumcph", cph[2]);
		result.addObject("minimumcph", cph[1]);
		result.addObject("averagecph", cph[0]);
		result.addObject("stdevcph", cph[3]);

		// Finder results
		result.addObject("maximumfr", fr[1]);
		result.addObject("minimumfr", fr[2]);
		result.addObject("averagefr", fr[0]);
		result.addObject("stdevfr", fr[3]);

		result.addObject("empvsful", empvsful);

		// AuditScorePositions
		result.addObject("maximumps", ps[2]);
		result.addObject("minimumps", ps[1]);
		result.addObject("averageps", ps[0]);
		result.addObject("stdevps", ps[3]);

		// CompanyScore
		result.addObject("maximumcs", cs[2]);
		result.addObject("minimumcs", cs[1]);
		result.addObject("averagecs", cs[0]);
		result.addObject("stdevcs", cs[3]);

		result.addObject("highestScore", highestScore);

		result.addObject("avgSalaryTop", avgSalaryTop);

		// ItemsPerProvider
		result.addObject("maximumipp", ipp[2]);
		result.addObject("minimumipp", ipp[1]);
		result.addObject("averageipp", ipp[0]);
		result.addObject("stdevipp", ipp[3]);

		result.addObject("topProviders", topProviders);

		// SponsorshipsPerProvider
		result.addObject("maximumspp", spp[2]);
		result.addObject("minimumspp", spp[1]);
		result.addObject("averagespp", spp[0]);
		result.addObject("stdevspp", spp[3]);

		// SponsorshipsPerPosition
		result.addObject("maximumsppo", sppo[2]);
		result.addObject("minimumsppo", sppo[1]);
		result.addObject("averagesppo", sppo[0]);
		result.addObject("stdevsppo", sppo[3]);

		result.addObject("providersMAVG", providersMAVG);
		
		return result;
	}

	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public ModelAndView userManagement() {
		ModelAndView result;

		Collection<Actor> actors;

		final Collection<Authority> a = new ArrayList<>();
		final Collection<Authority> b = new ArrayList<>();
		final Authority h = new Authority();
		h.setAuthority("HACKER");
		final Authority c = new Authority();
		c.setAuthority("COMPANY");
		a.add(h);
		b.add(c);

		actors = this.actorService.findAll();

		result = new ModelAndView("administrator/management");

		result.addObject("actors", actors);
		result.addObject("authH", h);
		result.addObject("authC", c);

		return result;

	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {

		ModelAndView result;

		final UserAccount uc = this.adminService.findByActor(actorId);

		final boolean banned = this.actorService.banActor(uc);

		result = new ModelAndView("redirect:management.do");

		if (!banned)
			result.addObject("showErrorBan", true);

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {

		ModelAndView result;

		final UserAccount uc = this.adminService.findByActor(actorId);

		final boolean unbanned = this.actorService.unbanActor(uc);

		result = new ModelAndView("redirect:management.do");

		if (!unbanned)
			result.addObject("showErrorUnban", true);

		return result;
	}

	@RequestMapping(value = "/getSpammers")
	public ModelAndView getSpammers() {
		ModelAndView result;
		this.actorService.evaluateSpammers();
		result = new ModelAndView("redirect:management.do");

		return result;
	}

	protected ModelAndView createBroadcastModelAndView() {
		final ModelAndView result = new ModelAndView("administrator/broadcast");
		final BroadcastForm broadcastForm = new BroadcastForm();

		result.addObject("broadcastForm", broadcastForm);
		return result;
	}

	protected ModelAndView createBroadcastModelAndView(
			final BroadcastForm broadcastForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/broadcast");
		result.addObject("broadcastForm", broadcastForm);
		result.addObject("message", messageCode);

		return result;
	}
}
