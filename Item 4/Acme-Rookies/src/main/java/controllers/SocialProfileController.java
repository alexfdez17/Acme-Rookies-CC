
package controllers;

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

import services.ActorService;
import services.SocialProfileService;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<SocialProfile> socialProfiles;

		final Actor principal = this.actorService.findByPrincipal();

		socialProfiles = this.socialProfileService.findByActor(principal);

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);

		return result;
	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int socialProfileId) {
		final ModelAndView result;
		SocialProfile socialProfile;
		Collection<SocialProfile> socialProfiles;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		socialProfiles = this.socialProfileService.findByActor(actor);
		socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.isTrue(socialProfiles.contains(socialProfile));

		result = new ModelAndView("socialProfile/display");
		result.addObject("socialProfile", socialProfile);

		return result;
	}

	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = new ModelAndView("socialProfile/create");
		result.addObject("socialProfile", socialProfile);
		result.addObject("id", socialProfile.getId());
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findOne(socialProfileId);

		result = this.createEditModelAndView(socialProfile);
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("socialProfile") SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		socialProfile = this.socialProfileService.reconstruct(socialProfile, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
			}
		return result;
	}

	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile socialProfile, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.socialProfileService.delete(socialProfile);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		return this.createEditModelAndView(socialProfile, null);
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String message) {
		final ModelAndView result;

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("id", socialProfile.getId());
		result.addObject("message", message);
		return result;
	}

}
