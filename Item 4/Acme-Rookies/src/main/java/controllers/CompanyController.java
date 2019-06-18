
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.PositionService;
import domain.Company;
import domain.Position;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public CompanyController() {
		super();
	}


	@Autowired
	public CompanyService	companyService;
	@Autowired
	public PositionService	positionService;


	// Companies

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Company> companies;
		companies = this.companyService.findAll();
		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");
		return result;
	}

	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer companyId) {
		ModelAndView result;
		Company company;
		Collection<Position> positions;
		company = this.companyService.findOne(companyId);
		positions = this.positionService.findByCompany(company);
		Collection<Position> finalPositions;
		finalPositions = this.positionService.findbyFinalMode();
		positions.retainAll(finalPositions);
		result = new ModelAndView("company/display");
		result.addObject("company", company);
		result.addObject("positions", positions);

		return result;
	}
}
