
package controllers.provider;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;

@Controller
@RequestMapping("/item/provider")
public class ItemProviderController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ItemProviderController() {
		super();
	}


	@Autowired
	public ActorService				actorService;
	@Autowired
	public ItemService				itemService;
	@Autowired
	public ProviderService			providerService;


	// Applications

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Item> items;

		items = this.itemService.findCurrentProviderItems();
		result = new ModelAndView("item/provider/list");
		result.addObject("items", items);
		result.addObject("requestURI", "item/provider/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer itemId) {
		ModelAndView result;
		final Item item;
		
		item = this.itemService.findOne(itemId);

		result = new ModelAndView("item/provider/display");
		result.addObject("item", item);
		result.addObject("requestURI", "item/provider/display.do");

		return result;
	}
	//CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Item item;
		
		item = this.itemService.create();
		result = this.createEditModelAndView(item);
		
		return result;
	}
	//UPDATE

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView updateApplication(@RequestParam final int itemId) {
		final ModelAndView result;
		final Item item;

		item = this.itemService.findOne(itemId);

		result = this.createEditModelAndView(item);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateApplication(@Valid final Item item, final BindingResult binding) {
		ModelAndView result;
		final Item itm;
		final Authority authP = new Authority();
		authP.setAuthority(Authority.PROVIDER);

		try {
			if (binding.hasErrors())
				result = this.updateItemModelAndView("actor.commit.error");
			else {
				if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authP)) {

					if(item.getId()!=0){
						itm = this.itemService.findOne(item.getId());
					}
					else{
						itm = this.itemService.create();
					}

					itm.setDescription(item.getDescription());
					itm.setLinks(item.getLinks());
					itm.setName(item.getName());
					itm.setPictures(item.getPictures());

					this.itemService.save(itm);
				}

				result = new ModelAndView("redirect:/item/provider/list.do");
			}
		} catch (final Throwable oops) {
			result = this.updateItemModelAndView("actor.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int itemId) {
		ModelAndView result;
		try {
			final Item item= this.itemService.findOne(itemId);
			this.itemService.delete(item);
			result = new ModelAndView("redirect:/item/provider/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/item/provider/list.do");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item) {
		return this.createEditModelAndView(item, null);
	}

	protected ModelAndView createEditModelAndView(final Item res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("item/provider/edit");
		result.addObject("item", res);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView updateItemModelAndView(final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("item/provider/edit");

		result.addObject("message", messageCode);

		return result;
	}
}
