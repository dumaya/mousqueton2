package dumaya.dev.controller;

import javax.validation.Valid;

import dumaya.dev.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dumaya.dev.service.UtilisateurService;

@Controller
public class LoginController {
	
	@Autowired
	private UtilisateurService utilisateurService;

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(Model model){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Utilisateur utilisateur = new Utilisateur();
		modelAndView.addObject("utilisateur", utilisateur);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView creerNouveauUtilisateur(@Valid Utilisateur utilisateur, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Utilisateur utilisateurExists = utilisateurService.findUtilisateurByEmail(utilisateur.getEmail());
		if (utilisateurExists != null) {
			bindingResult
					.rejectValue("email", "error.utilisateur",
							"There is already a utilisateur registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			utilisateurService.saveUtilisateur(utilisateur);
			modelAndView.addObject("successMessage", "Utilisateur has been registered successfully");
			modelAndView.addObject("utilisateur", new Utilisateur());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurService.findUtilisateurByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + utilisateur.getName() + " " + utilisateur.getLastName() + " (" + utilisateur.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("index");
		return modelAndView;
	}

}
