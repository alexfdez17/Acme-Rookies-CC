
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ProviderController() {
		super();
	}


	@Autowired
	public ProviderService		providerService;

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Provider> providers;
		providers = this.providerService.findAll();
		result = new ModelAndView("provider/list");
		result.addObject("providers", providers);
		result.addObject("requestURI", "provider/list.do");

		return result;
	}
}
