package dumaya.dev.controller;

import dumaya.dev.model.Secteur;
import dumaya.dev.model.Site;
import dumaya.dev.model.Topo;
import dumaya.dev.model.User;
import dumaya.dev.service.SiteService;
import dumaya.dev.service.TopoService;
import dumaya.dev.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class SiteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopoController.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private UserService userService;

    @Value("${erreur.saisie.site}")
    private String erreurSaisieSite;

    @GetMapping("/sites")
    public String affichelesSites(Model model) {
        LOGGER.debug("page afficher les sites");
        List<Site> sites= siteService.listeSites();
        model.addAttribute("sites", sites);
        return "sites";
    }

    @GetMapping("/ajoutsite")
    public String ajoutSite(Model model) {

        /* Formulaire de création d'un topo */
        LOGGER.debug("Init formulaire site");
        Site site = new Site();
        model.addAttribute("site", site);

        return "ajoutsite";
    }

    @PostMapping(value = "/ajoutsite")
    public String proposerSiteSubmit(Model model, @Valid @ModelAttribute("site") Site site, BindingResult result) {

        LOGGER.debug("submit du formulaire site");

        if (result.hasErrors()){
            /** Garder la liste des sites de l'utilisateur */
            List<Site> sites= siteService.listeSites();
            model.addAttribute("sites", sites);
            model.addAttribute("erreurSaisieSite", erreurSaisieSite);
            return "ajouttopo";
        } else {
            siteService.enregistrer(site);
            return "redirect:/sites";
        }
    }
    @GetMapping("/secteurs")
    public String affichelesSecteurs(Model model, @RequestParam("idSite") int idSite) {
        LOGGER.debug("page afficher les secteurs d'un site");
        Site site = siteService.getSite(idSite);
        model.addAttribute("site", site);
        return "secteurs";
    }

    @GetMapping("/ajoutsecteur")
    public String ajoutSescteur(Model model,@RequestParam("idSite") int idSite) {

        /* Formulaire de création d'un secteur */
        LOGGER.debug("Init formulaire secteur");
        Secteur secteur = new Secteur();
        model.addAttribute("site", siteService.getSite(idSite));
        model.addAttribute("secteur", secteur);

        return "ajoutsecteur";
    }

    @PostMapping(value = "/ajoutsecteur")
    public String proposerSecteurSubmit(Model model, @Valid @ModelAttribute("secteur") Secteur secteur, @RequestParam("idSite") int idSite, BindingResult result) {

        LOGGER.debug("submit du formulaire secteur");

        if (result.hasErrors()){
            /** Garder la liste des secteurs de l'utilisateur */
            /**List<Secteur> sites= siteService.listeSecteurs();
            model.addAttribute("secteurs", secteurs);
            model.addAttribute("erreurSaisieSite", erreurSaisieSite);*/
            return "ajouttopo";
        } else {
            siteService.ajoutSecteur(idSite, secteur);
            return "redirect:/secteurs";
        }
    }
}

