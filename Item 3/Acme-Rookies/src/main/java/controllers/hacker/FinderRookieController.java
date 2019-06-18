
package controllers.hacker;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.RookieService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Finder;
import domain.Rookie;
import domain.Position;

@Controller
@RequestMapping("/finder/rookie")
public class FinderRookieController extends AbstractController {

	@Autowired
	RookieService				rookieService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private SystemConfigService	systemConfigService;


	//Constructor
	public FinderRookieController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Rookie rookie = this.rookieService.findByPrincipal();

		Finder finder = this.finderService.findByRookie(rookie);
		if (finder == null) {
			finder = this.finderService.create();
			finder.setFinderDate(new Date());
			finder.setRookie(rookie);
		}
		final Date date = new Date();
		final Date finderDate = finder.getFinderDate();
		final long currentTime = date.getTime();
		final long finderTime = finderDate.getTime();
		final long diff = currentTime - finderTime;
		if (diff > this.systemConfigService.findSystemConfiguration().getFinderCacheHours() * 3600000) {
			finder.getPositions().clear();
			this.finderService.save(finder);
		}

		result = new ModelAndView("finder/member/edit");
		result.addObject("finder", finder);
		result.addObject("requestURI", "finder/member/edit.do");
		result.addObject("positions", finder.getPositions());
		return result;

	}

	//Arcillary methods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;
		Collection<Position> positions;
		positions = finder.getPositions();
		result = new ModelAndView("finder/member/edit");
		result.addObject("finder", finder);
		result.addObject("message", messageCode);
		result.addObject("positions", positions);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				this.finderService.flush();
				result = new ModelAndView("redirect:/finder/rookie/edit.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
				oops.printStackTrace();
			}
		return result;
	}

	@RequestMapping(value = "/clean", method = RequestMethod.GET)
	public ModelAndView clean() {
		final Rookie rookie = this.rookieService.findByPrincipal();

		final Finder finder = this.finderService.findByRookie(rookie);
		if (finder != null)
			this.finderService.delete(finder);
		return new ModelAndView("redirect:/finder/rookie/edit.do");

	}

}
