
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import forms.StringForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	public MessageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final String tag) {
		final ModelAndView result;
		final Collection<Message> messages = this.messageService.findPrincipalMessagesFromTag(tag);
		result = new ModelAndView("message/list");
		final StringForm tagForm = new StringForm();
		tagForm.setValue(tag);
		result.addObject("tag", tagForm);
		result.addObject("messages", messages);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
	public ModelAndView find(final StringForm tag) {
		return new ModelAndView("redirect:/message/list.do?tag=" + tag.getValue());
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;

		result = new ModelAndView("message/display");
		final Message message = this.messageService.findOne(messageId);
		result.addObject("displayedmessage", message);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Message message = this.messageService.create();
		result = this.createEditModelAndView(message);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				this.messageService.send(message);
				result = new ModelAndView("redirect:/message/list.do?tag=");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;
		try {
			final Message message = this.messageService.findOne(messageId);
			this.messageService.remove(message);
			result = new ModelAndView("redirect:/message/list.do?tag=DELETED");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/message/list.do?tag=");
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;

		final Collection<Actor> actors = this.actorService.findAllButPrincipal();
		result = new ModelAndView("message/edit");
		result.addObject("msg", message);
		result.addObject("message", messageCode);
		result.addObject("actors", actors);
		return result;
	}
}
