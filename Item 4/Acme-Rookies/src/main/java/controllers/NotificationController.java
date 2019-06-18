
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

import services.NotificationService;
import domain.Notification;

@Controller
@RequestMapping("/notification")
public class NotificationController extends AbstractController {

	@Autowired
	private NotificationService	notificationService;

	public NotificationController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Notification> notifications = this.notificationService.findAll();
		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int notificationId) {
		ModelAndView result;

		result = new ModelAndView("notification/display");
		final Notification notification = this.notificationService.findOne(notificationId);
		result.addObject("displayednotification", notification);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Notification notification = this.notificationService.create();
		result = this.createEditModelAndView(notification);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Notification notification, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(notification);
		else
			try {
				this.notificationService.send(notification);
				result = new ModelAndView("redirect:/notification/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(notification, "notification.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Notification notification) {
		ModelAndView result;

		result = this.createEditModelAndView(notification, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Notification notification, final String notificationCode) {
		ModelAndView result;

		result = new ModelAndView("notification/edit");
		result.addObject("msg", notification);
		result.addObject("notification", notificationCode);
		return result;
	}
}
