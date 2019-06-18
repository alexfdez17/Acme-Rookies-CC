
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ItemController() {
		super();
	}


	@Autowired
	public ItemService		itemService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@Valid final String providerId) {
		final ModelAndView result;
		Collection<Item> items;
		items = this.itemService.findProviderItems(providerId);
		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "item/list.do");

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer itemId) {
		ModelAndView result;
		final Item item;
		
		item = this.itemService.findOne(itemId);

		result = new ModelAndView("item/display");
		result.addObject("item", item);
		result.addObject("requestURI", "item/display.do");

		return result;
	}
}
