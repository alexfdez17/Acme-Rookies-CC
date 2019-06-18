
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.ProviderService;
import services.RookieService;
import services.SocialProfileService;
import services.SystemConfigService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Company;
import domain.Provider;
import domain.Rookie;
import domain.SocialProfile;
import domain.SystemConfig;
import forms.EditAuditorForm;
import forms.EditCompanyForm;
import forms.EditProviderForm;
import forms.EditRookieForm;
import forms.RegisterAdminForm;
import forms.RegisterAuditorForm;
import forms.RegisterCompanyForm;
import forms.RegisterProviderForm;
import forms.RegisterRookieForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private SystemConfigService		systemConfigService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ProviderService			providerService;
	
	@Autowired
	private SocialProfileService	socialProfileService;


	//-----------------------------------

	@RequestMapping(value = "/displayAdmin", method = RequestMethod.GET)
	public ModelAndView displayAdmin(Integer actorId) {
		final ModelAndView result;
		Administrator administrator;
		result = new ModelAndView("actor/displayAdmin");

		if (actorId != null) {
			administrator = this.administratorService.findOne(actorId);
			final Actor actor2 = this.actorService.findByPrincipal();
			result.addObject("actor2", actor2);
		} else {
			actorId = this.actorService.findByPrincipal().getId();
			administrator = this.administratorService.findOne(this.actorService.findByPrincipal().getId());
		}

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(this.actorService.findByPrincipal());
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("actor", administrator);
		result.addObject("requestURI", "actor/displayAdmin.do");
		result.addObject("actorId", administrator.getId());
		return result;
	}
	
	
	@RequestMapping(value = "/displayRookie", method = RequestMethod.GET)
	public ModelAndView displayRookie(Integer actorId) {
		final ModelAndView result;
		Rookie rookie;
		result = new ModelAndView("actor/displayRookie");

		if (actorId != null) {
			rookie = this.rookieService.findOne(actorId);
		} else {
			actorId = this.actorService.findByPrincipal().getId();
			rookie = this.rookieService.findOne(this.actorService.findByPrincipal().getId());
		}

		result.addObject("actor", rookie);
		result.addObject("requestURI", "actor/displayRookie.do");
		result.addObject("actorId", rookie.getId());
		return result;
	}

	@RequestMapping(value = "/displayAuditor", method = RequestMethod.GET)
	public ModelAndView displayAuditor(Integer actorId) {
		final ModelAndView result;
		Auditor auditor;
		result = new ModelAndView("actor/displayAuditor");

		if (actorId != null) {
			auditor = this.auditorService.findOne(actorId);
		} else {
			actorId = this.actorService.findByPrincipal().getId();
			auditor = this.auditorService.findOne(this.actorService.findByPrincipal().getId());
		}

		result.addObject("actor", auditor);
		result.addObject("requestURI", "actor/displayAuditor.do");
		result.addObject("actorId", auditor.getId());
		return result;
	}

	@RequestMapping(value = "/displayCompany", method = RequestMethod.GET)
	public ModelAndView displayCompany(Integer actorId) {
		final ModelAndView result;
		Company company;
		result = new ModelAndView("actor/displayCompany");

		if (actorId != null) {
			company = this.companyService.findOne(actorId);
		} else {
			actorId = this.actorService.findByPrincipal().getId();
			company = this.companyService.findOne(this.actorService.findByPrincipal().getId());
		}
		
		result.addObject("actor", company);
		result.addObject("requestURI", "actor/displayCompany.do");
		result.addObject("actorId", company.getId());
		return result;
	}

	@RequestMapping(value = "/displayProvider", method = RequestMethod.GET)
	public ModelAndView displayProvider(Integer actorId) {
		final ModelAndView result;
		Provider provider;
		result = new ModelAndView("actor/displayProvider");

		if (actorId != null) {
			provider = this.providerService.findOne(actorId);
		} else {
			actorId = this.actorService.findByPrincipal().getId();
			provider = this.providerService.findOne(this.actorService.findByPrincipal().getId());
		}

		result.addObject("actor", provider);
		result.addObject("requestURI", "actor/displayProvider.do");
		result.addObject("actorId", provider.getId());
		return result;
	}

	//-------------------------------------------------------------

	@RequestMapping(value = "/registerRookie", method = RequestMethod.GET)
	public ModelAndView createRookie() {
		return this.createRegisterRookieModelAndView();
	}

	@RequestMapping(value = "/registerCompany", method = RequestMethod.GET)
	public ModelAndView createCompany() {
		return this.createRegisterCompanyModelAndView();
	}

	@RequestMapping(value = "/registerAdministrator", method = RequestMethod.GET)
	public ModelAndView createAdministrator() {
		return this.createRegisterAdministratorModelAndView();
	}

	@RequestMapping(value = "/registerAuditor", method = RequestMethod.GET)
	public ModelAndView createAuditor() {
		return this.createRegisterAuditorModelAndView();
	}

	@RequestMapping(value = "/registerProvider", method = RequestMethod.GET)
	public ModelAndView createProvider() {
		return this.createRegisterProviderModelAndView();
	}

	//-------------------------------------------------------

	@RequestMapping(value = "/registerRookie", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterRookieForm registerRookieForm, final BindingResult binding) {
		SystemConfig systemConfig;

		try {
			if (binding.hasErrors())
				return this.createRegisterRookieModelAndView(registerRookieForm, "actor.commit.error");
			else if (registerRookieForm.getPhone().matches("\\d{4,99}")) {
				systemConfig = this.systemConfigService.findSystemConfiguration();
				String newPhone = systemConfig.getPhonePrefix();
				newPhone += " " + registerRookieForm.getPhone();
				registerRookieForm.setPhone(newPhone);
			}
			this.rookieService.registerRookie(registerRookieForm);
			return new ModelAndView("redirect:/");

		} catch (final Throwable oops) {
			return this.createRegisterRookieModelAndView(registerRookieForm, "actor.commit.error");

		}
	}

	@RequestMapping(value = "/registerCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterCompanyForm registerCompanyForm, final BindingResult binding) {
		SystemConfig systemConfig;

		try {
			if (binding.hasErrors())
				return this.createRegisterCompanyModelAndView(registerCompanyForm, "actor.commit.error");
			else if (registerCompanyForm.getPhone().matches("\\d{4,99}")) {
				systemConfig = this.systemConfigService.findSystemConfiguration();
				String newPhone = systemConfig.getPhonePrefix();
				newPhone += " " + registerCompanyForm.getPhone();
				registerCompanyForm.setPhone(newPhone);
			}
			this.companyService.registerCompany(registerCompanyForm);
			return new ModelAndView("redirect:/");

		} catch (final Throwable oops) {
			return this.createRegisterCompanyModelAndView(registerCompanyForm, "actor.commit.error");

		}
	}

	@RequestMapping(value = "/registerAdministrator", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterAdminForm registerAdminForm, final BindingResult binding) {
		SystemConfig systemConfig;

		try {
			if (binding.hasErrors())
				return this.createRegisterAdministratorModelAndView(registerAdminForm, "actor.commit.error");
			else if (registerAdminForm.getPhone().matches("\\d{4,99}")) {
				systemConfig = this.systemConfigService.findSystemConfiguration();
				String newPhone = systemConfig.getPhonePrefix();
				newPhone += " " + registerAdminForm.getPhone();
				registerAdminForm.setPhone(newPhone);
			}
			this.administratorService.registerAdministrator(registerAdminForm);
			return new ModelAndView("redirect:/");

		} catch (final Throwable oops) {
			return this.createRegisterAdministratorModelAndView(registerAdminForm, "actor.commit.error");
		}
	}

	@RequestMapping(value = "/registerAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterAuditorForm registerAuditorForm, final BindingResult binding) {
		SystemConfig systemConfig;

		try {
			if (binding.hasErrors())
				return this.createRegisterAuditorModelAndView(registerAuditorForm, "actor.commit.error");
			else if (registerAuditorForm.getPhone().matches("\\d{4,99}")) {
				systemConfig = this.systemConfigService.findSystemConfiguration();
				String newPhone = systemConfig.getPhonePrefix();
				newPhone += " " + registerAuditorForm.getPhone();
				registerAuditorForm.setPhone(newPhone);
			}
			this.auditorService.registerAuditor(registerAuditorForm);
			return new ModelAndView("redirect:/");

		} catch (final Throwable oops) {
			return this.createRegisterAuditorModelAndView(registerAuditorForm, "actor.commit.error");
		}
	}

	@RequestMapping(value = "/registerProvider", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterProviderForm registerProviderForm, final BindingResult binding) {
		SystemConfig systemConfig;

		try {
			if (binding.hasErrors())
				return this.createRegisterProviderModelAndView(registerProviderForm, "actor.commit.error");
			else if (registerProviderForm.getPhone().matches("\\d{4,99}")) {
				systemConfig = this.systemConfigService.findSystemConfiguration();
				String newPhone = systemConfig.getPhonePrefix();
				newPhone += " " + registerProviderForm.getPhone();
				registerProviderForm.setPhone(newPhone);
			}
			this.providerService.registerProvider(registerProviderForm);
			return new ModelAndView("redirect:/");

		} catch (final Throwable oops) {
			return this.createRegisterProviderModelAndView(registerProviderForm, "actor.commit.error");
		}
	}

	//-------------------------------------------------------

	@RequestMapping(value = "/editAdmin", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		final ModelAndView result;
		final EditRookieForm editRookieForm = new EditRookieForm();
		final Administrator administrator;

		administrator = this.administratorService.findOne(this.actorService.findByPrincipal().getId());

		editRookieForm.setAddress(administrator.getAddress());
		editRookieForm.setEmail(administrator.getEmail());
		editRookieForm.setName(administrator.getName());
		editRookieForm.setPhone(administrator.getPhone());
		editRookieForm.setPhoto(administrator.getPhoto());
		editRookieForm.setSurname(administrator.getSurname());
		editRookieForm.setVat(administrator.getVat());

		result = new ModelAndView("actor/editAdmin");
		result.addObject("editRookieForm", editRookieForm);
		result.addObject("requestURI", "actor/editAdmin.do");

		return result;
	}
	
	@RequestMapping(value = "/editAdmin", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdmin(@Valid final EditRookieForm editRookieForm, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		try {
			if (binding.hasErrors())
				result = this.createEditAdminModelAndView(editRookieForm, "actor.commit.error");
			else {
				administrator = this.administratorService.findOne(this.actorService.findByPrincipal().getId());

				administrator.setAddress(editRookieForm.getAddress());
				administrator.setEmail(editRookieForm.getEmail());
				administrator.setName(editRookieForm.getName());
				administrator.setPhone(editRookieForm.getPhone());
				administrator.setPhoto(editRookieForm.getPhoto());
				administrator.setSurname(editRookieForm.getSurname());
				administrator.setVat(editRookieForm.getVat());
				this.administratorService.save(administrator);

				result = new ModelAndView("redirect:/actor/displayAdmin.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditAdminModelAndView(editRookieForm, "actor.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/editRookie", method = RequestMethod.GET)
	public ModelAndView editRookie() {
		final ModelAndView result;
		final EditRookieForm editRookieForm = new EditRookieForm();
		final Rookie rookie;

		rookie = this.rookieService.findOne(this.actorService.findByPrincipal().getId());

		editRookieForm.setAddress(rookie.getAddress());
		editRookieForm.setEmail(rookie.getEmail());
		editRookieForm.setName(rookie.getName());
		editRookieForm.setPhone(rookie.getPhone());
		editRookieForm.setPhoto(rookie.getPhoto());
		editRookieForm.setSurname(rookie.getSurname());
		editRookieForm.setVat(rookie.getVat());

		result = new ModelAndView("actor/editRookie");
		result.addObject("editRookieForm", editRookieForm);
		result.addObject("requestURI", "actor/editRookie.do");

		return result;
	}

	@RequestMapping(value = "/editRookie", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRookie(@Valid final EditRookieForm editRookieForm, final BindingResult binding) {
		ModelAndView result;
		Rookie rookie;

		try {
			if (binding.hasErrors())
				result = this.createEditRookieModelAndView(editRookieForm, "actor.commit.error");
			else {
				rookie = this.rookieService.findOne(this.actorService.findByPrincipal().getId());

				rookie.setAddress(editRookieForm.getAddress());
				rookie.setEmail(editRookieForm.getEmail());
				rookie.setName(editRookieForm.getName());
				rookie.setPhone(editRookieForm.getPhone());
				rookie.setPhoto(editRookieForm.getPhoto());
				rookie.setSurname(editRookieForm.getSurname());
				rookie.setVat(editRookieForm.getVat());
				this.rookieService.save(rookie);

				result = new ModelAndView("redirect:/actor/displayRookie.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditRookieModelAndView(editRookieForm, "actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/editAuditor", method = RequestMethod.GET)
	public ModelAndView editAuditor() {
		final ModelAndView result;
		final EditAuditorForm editAuditorForm = new EditAuditorForm();
		final Auditor auditor;

		auditor = this.auditorService.findOne(this.actorService.findByPrincipal().getId());

		editAuditorForm.setAddress(auditor.getAddress());
		editAuditorForm.setEmail(auditor.getEmail());
		editAuditorForm.setName(auditor.getName());
		editAuditorForm.setPhone(auditor.getPhone());
		editAuditorForm.setPhoto(auditor.getPhoto());
		editAuditorForm.setSurname(auditor.getSurname());
		editAuditorForm.setVat(auditor.getVat());

		result = new ModelAndView("actor/editAuditor");
		result.addObject("editAuditorForm", editAuditorForm);
		result.addObject("requestURI", "actor/editAuditor.do");

		return result;
	}

	@RequestMapping(value = "/editAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(@Valid final EditAuditorForm editAuditorForm, final BindingResult binding) {
		ModelAndView result;
		Auditor auditor;

		try {
			if (binding.hasErrors())
				result = this.createEditAuditorModelAndView(editAuditorForm, "actor.commit.error");
			else {
				auditor = this.auditorService.findOne(this.actorService.findByPrincipal().getId());

				auditor.setAddress(editAuditorForm.getAddress());
				auditor.setEmail(editAuditorForm.getEmail());
				auditor.setName(editAuditorForm.getName());
				auditor.setPhone(editAuditorForm.getPhone());
				auditor.setPhoto(editAuditorForm.getPhoto());
				auditor.setSurname(editAuditorForm.getSurname());
				auditor.setVat(editAuditorForm.getVat());
				this.auditorService.save(auditor);

				result = new ModelAndView("redirect:/actor/displayAuditor.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditAuditorModelAndView(editAuditorForm, "actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/editCompany", method = RequestMethod.GET)
	public ModelAndView editCompany() {
		final ModelAndView result;
		final EditCompanyForm editCompanyForm = new EditCompanyForm();
		final Company company;

		company = this.companyService.findOne(this.actorService.findByPrincipal().getId());

		editCompanyForm.setAddress(company.getAddress());
		editCompanyForm.setEmail(company.getEmail());
		editCompanyForm.setName(company.getName());
		editCompanyForm.setPhone(company.getPhone());
		editCompanyForm.setPhoto(company.getPhoto());
		editCompanyForm.setSurname(company.getSurname());
		editCompanyForm.setVat(company.getVat());
		editCompanyForm.setComercialName(company.getComercialName());

		result = new ModelAndView("actor/editCompany");
		result.addObject("editCompanyForm", editCompanyForm);
		result.addObject("requestURI", "actor/editCompany.do");

		return result;
	}

	@RequestMapping(value = "/editCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCompany(@Valid final EditCompanyForm editCompanyForm, final BindingResult binding) {
		ModelAndView result;
		Company company;

		try {
			if (binding.hasErrors())
				result = this.createEditCompanyModelAndView(editCompanyForm, "actor.commit.error");
			else {
				company = this.companyService.findOne(this.actorService.findByPrincipal().getId());

				company.setAddress(editCompanyForm.getAddress());
				company.setEmail(editCompanyForm.getEmail());
				company.setName(editCompanyForm.getName());
				company.setPhone(editCompanyForm.getPhone());
				company.setPhoto(editCompanyForm.getPhoto());
				company.setSurname(editCompanyForm.getSurname());
				company.setVat(editCompanyForm.getVat());
				company.setComercialName(editCompanyForm.getComercialName());

				this.companyService.save(company);

				result = new ModelAndView("redirect:/actor/displayCompany.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditCompanyModelAndView(editCompanyForm, "actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/editProvider", method = RequestMethod.GET)
	public ModelAndView editProvider() {
		final ModelAndView result;
		final EditProviderForm editProviderForm = new EditProviderForm();
		final Provider provider;

		provider = this.providerService.findOne(this.actorService.findByPrincipal().getId());

		editProviderForm.setAddress(provider.getAddress());
		editProviderForm.setEmail(provider.getEmail());
		editProviderForm.setName(provider.getName());
		editProviderForm.setPhone(provider.getPhone());
		editProviderForm.setPhoto(provider.getPhoto());
		editProviderForm.setSurname(provider.getSurname());
		editProviderForm.setVat(provider.getVat());
		editProviderForm.setProviderMake(provider.getProviderMake());

		result = new ModelAndView("actor/editProvider");
		result.addObject("editProviderForm", editProviderForm);
		result.addObject("requestURI", "actor/editProvider.do");

		return result;
	}

	@RequestMapping(value = "/editProvider", method = RequestMethod.POST, params = "save")
	public ModelAndView saveProvider(@Valid final EditProviderForm editProviderForm, final BindingResult binding) {
		ModelAndView result;
		Provider provider;

		try {
			if (binding.hasErrors())
				result = this.createEditProviderModelAndView(editProviderForm, "actor.commit.error");
			else {
				provider = this.providerService.findOne(this.actorService.findByPrincipal().getId());

				provider.setAddress(editProviderForm.getAddress());
				provider.setEmail(editProviderForm.getEmail());
				provider.setName(editProviderForm.getName());
				provider.setPhone(editProviderForm.getPhone());
				provider.setPhoto(editProviderForm.getPhoto());
				provider.setSurname(editProviderForm.getSurname());
				provider.setVat(editProviderForm.getVat());
				provider.setProviderMake(editProviderForm.getProviderMake());

				this.providerService.save(provider);

				result = new ModelAndView("redirect:/actor/displayProvider.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditProviderModelAndView(editProviderForm, "actor.commit.error");
		}
		return result;
	}

	//-------------------------------------------------------

	protected ModelAndView createRegisterRookieModelAndView() {
		final ModelAndView result = new ModelAndView("actor/registerRookie");
		final RegisterRookieForm registerRookieForm = new RegisterRookieForm();

		result.addObject("registerRookieForm", registerRookieForm);

		return result;
	}

	protected ModelAndView createRegisterRookieModelAndView(final RegisterRookieForm registerRookieForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/registerRookie");
		result.addObject("registerRookieForm", registerRookieForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterCompanyModelAndView() {
		final ModelAndView result = new ModelAndView("actor/registerCompany");
		final RegisterCompanyForm registerCompanyForm = new RegisterCompanyForm();

		result.addObject("registerCompanyForm", registerCompanyForm);

		return result;
	}

	protected ModelAndView createRegisterCompanyModelAndView(final RegisterCompanyForm registerCompanyForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/registerCompany");
		result.addObject("registerCompanyForm", registerCompanyForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterAdministratorModelAndView() {
		final ModelAndView result = new ModelAndView("actor/registerAdministrator");
		final RegisterAdminForm registerAdminForm = new RegisterAdminForm();

		result.addObject("registerAdminForm", registerAdminForm);

		return result;
	}

	protected ModelAndView createRegisterAdministratorModelAndView(final RegisterAdminForm registerAdminForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/registerAdministrator");
		result.addObject("registerAdminForm", registerAdminForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterAuditorModelAndView() {
		final ModelAndView result = new ModelAndView("actor/registerAuditor");
		final RegisterAuditorForm registerAuditorForm = new RegisterAuditorForm();

		result.addObject("registerAuditorForm", registerAuditorForm);

		return result;
	}

	protected ModelAndView createRegisterAuditorModelAndView(final RegisterAuditorForm registerAuditorForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/registerAuditor");
		result.addObject("registerAuditorForm", registerAuditorForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterProviderModelAndView() {
		final ModelAndView result = new ModelAndView("actor/registerProvider");
		final RegisterProviderForm registerProviderForm = new RegisterProviderForm();

		result.addObject("registerProviderForm", registerProviderForm);

		return result;
	}

	protected ModelAndView createRegisterProviderModelAndView(final RegisterProviderForm registerProviderForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/registerProvider");
		result.addObject("registerProviderForm", registerProviderForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditRookieModelAndView(final EditRookieForm editRookieForm) {
		ModelAndView result;

		result = this.createEditRookieModelAndView(editRookieForm, null);

		return result;
	}

	protected ModelAndView createEditAdminModelAndView(final EditRookieForm editRookieForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/editAdmin");

		result.addObject("editRookieForm", editRookieForm);
		result.addObject("message", messageCode);

		return result;
	}
	
	protected ModelAndView createEditRookieModelAndView(final EditRookieForm editRookieForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/editRookie");

		result.addObject("editRookieForm", editRookieForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditProviderModelAndView(final EditProviderForm editProviderForm) {
		ModelAndView result;

		result = this.createEditProviderModelAndView(editProviderForm, null);

		return result;
	}

	protected ModelAndView createEditProviderModelAndView(final EditProviderForm editProviderForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/editProvider");

		result.addObject("editProviderForm", editProviderForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditAuditorModelAndView(final EditAuditorForm editAuditorForm) {
		ModelAndView result;

		result = this.createEditAuditorModelAndView(editAuditorForm, null);

		return result;
	}

	protected ModelAndView createEditAuditorModelAndView(final EditAuditorForm editAuditorForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/editAuditor");

		result.addObject("editAuditorForm", editAuditorForm);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditCompanyModelAndView(final EditCompanyForm editCompanyForm) {
		ModelAndView result;

		result = this.createEditCompanyModelAndView(editCompanyForm, null);

		return result;
	}

	protected ModelAndView createEditCompanyModelAndView(final EditCompanyForm editCompanyForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/editCompany");

		result.addObject("editCompanyForm", editCompanyForm);
		result.addObject("message", messageCode);

		return result;
	}
}
