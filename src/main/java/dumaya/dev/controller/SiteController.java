package dumaya.dev.controller;

import dumaya.dev.model.*;
import dumaya.dev.service.SiteService;
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

    @Value("${erreur.saisie.secteur}")
    private String erreurSaisieSecteur;

    @Value("${erreur.saisie.voie}")
    private String erreurSaisieVoie;

    @Value("${erreur.saisie.longueur}")
    private String erreurSaisieLongueur;

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

    // un Site et les secteurs associés
    @GetMapping("/secteurs")
    public String affichelesSecteurs(Model model, @RequestParam("idSite") int idSite) {
        LOGGER.debug("page afficher les secteurs d'un site");
        Site site = siteService.getSite(idSite);
        model.addAttribute("site", site);
        return "secteurs";
    }

    @GetMapping("/ajoutsecteur")
    public String ajoutSecteur(Model model,@RequestParam("idSite") int idSite) {

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
            LOGGER.debug("erreur du formulaire secteur");
            model.addAttribute("erreurSaisieSecteur", erreurSaisieSecteur);
            return "secteurs";
        } else {
            siteService.ajoutSecteur(idSite, secteur);
            Site site = siteService.getSite(idSite);
            model.addAttribute("site", site);
            return "secteurs";
        }
    }
// Un secteur et les voies associées
    @GetMapping("/voies")
    public String affichelesVoies(Model model, @RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur) {
        LOGGER.debug("page afficher les voies d'un secteur");
        Site site = siteService.getSite(idSite);
        Secteur secteur = siteService.getSecteur(idSecteur);
        model.addAttribute("site", site);
        model.addAttribute("secteur", secteur);
        return "voies";
    }

    @GetMapping("/ajoutvoie")
    public String ajoutVoie(Model model,@RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur) {

        /* Formulaire de création d'une voie */
        LOGGER.debug("Init formulaire voie");
        Voie voie = new Voie();
        model.addAttribute("site", siteService.getSite(idSite));
        model.addAttribute("secteur", siteService.getSecteur(idSecteur));
        model.addAttribute("voie", voie);

        return "ajoutvoie";
    }

    @PostMapping(value = "/ajoutvoie")
    public String proposerVoieSubmit(Model model, @Valid @ModelAttribute("voie") Voie voie, @RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur, BindingResult result) {

        LOGGER.debug("submit du formulaire voie");

        if (result.hasErrors()){
            LOGGER.debug("erreur du formulaire voie");
            model.addAttribute("erreurSaisieVoie", erreurSaisieVoie);
            return "voies";
        } else {
            siteService.ajoutVoie(idSecteur, voie);
            Site site = siteService.getSite(idSite);
            model.addAttribute("site", site);
            model.addAttribute("secteur", siteService.getSecteur(idSecteur));
            return "voies";
        }
    }
    // une voie et les longueurs associées
    @GetMapping("/longueurs")
    public String affichelesLongueurs(Model model, @RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie) {
        LOGGER.debug("page afficher les longueurs d'une voie");
        Site site = siteService.getSite(idSite);
        Secteur secteur = siteService.getSecteur(idSecteur);
        Voie voie = siteService.getVoie(idVoie);
        model.addAttribute("site", site);
        model.addAttribute("secteur", secteur);
        model.addAttribute("voie",voie);
        return "longueurs";
    }
    @GetMapping("/ajoutlongueur")
    public String ajoutLongueur(Model model,@RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie) {

        /* Formulaire de création d'une longueur */
        LOGGER.debug("Init formulaire longueur");
        Longueur longueur = new Longueur();
        model.addAttribute("site", siteService.getSite(idSite));
        model.addAttribute("secteur", siteService.getSecteur(idSecteur));
        model.addAttribute("voie", siteService.getVoie(idVoie));
        model.addAttribute("longueur", longueur);

        return "ajoutlongueur";
    }
    @PostMapping(value = "/ajoutlongueur")
    public String proposerLongueurSubmit(Model model, @Valid @ModelAttribute("longueur") Longueur longueur, @RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie, BindingResult result) {

        LOGGER.debug("submit du formulaire longueur");

        if (result.hasErrors()){
            LOGGER.debug("erreur du formulaire longueur");
            model.addAttribute("erreurSaisieLongueur", erreurSaisieLongueur);
            return "longueurs";
        } else {
            siteService.ajoutLongueur(idVoie, longueur);
            model.addAttribute("site", siteService.getSite(idSite));
            model.addAttribute("secteur", siteService.getSecteur(idSecteur));
            model.addAttribute("voie", siteService.getVoie(idVoie));
            return "longueurs";
        }
    }

}

