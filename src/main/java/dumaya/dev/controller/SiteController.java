package dumaya.dev.controller;

import dumaya.dev.model.*;
import dumaya.dev.service.SiteService;
import dumaya.dev.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class SiteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

    private final SiteService siteService;

    private final UtilisateurService utilisateurService;

    @Value("${erreur.saisie.site}")
    private String erreurSaisieSite;

    @Value("${erreur.saisie.secteur}")
    private String erreurSaisieSecteur;

    @Value("${erreur.saisie.voie}")
    private String erreurSaisieVoie;

    @Value("${erreur.saisie.longueur}")
    private String erreurSaisieLongueur;

    @Value("${erreur.saisie.commentaire}")
    private String erreurSaisieCommentaire;

    public SiteController(SiteService siteService, UtilisateurService utilisateurService) {
        this.siteService = siteService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/sites")
    public String affichelesSites(Model model) {
        LOGGER.info("page afficher les sites");
        List<Site> sites= siteService.listeSites();
        model.addAttribute("sites", sites);
        return "sites";
    }

    @GetMapping("/ajoutsite")
    public String ajoutSite(Model model) {

        /* Formulaire de création d'un site */
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
            return "ajoutsite";
        } else {
            siteService.enregistrer(site);
            List<Site> sites= siteService.listeSites();
            model.addAttribute("sites", sites);
            return "sites";
        }
    }

    // un Site et les secteurs associés
    @GetMapping("/secteurs")
    public String affichelesSecteurs(Model model, @RequestParam("idSite") int idSite, HttpSession httpSession) {
        LOGGER.debug("page afficher les secteurs d'un site");

        majModelSecteur(model,idSite,httpSession);
        Commentaire commentaire = new Commentaire();
        model.addAttribute("commentaire",commentaire);
        return "secteurs";
    }

    @PostMapping(value = "/secteurs/ajoutCommentaire")
    public String ajouterCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,  BindingResult result, @RequestParam("idSite") int idSite,  @RequestParam("email") String email,HttpSession httpSession) {

        LOGGER.debug("submit du formulaire commentaire");

        if (result.hasErrors()){
            /** Garder la liste des secteurs de l'utilisateur */
            majModelSecteur(model,idSite,httpSession);
            commentaire.setMessage("");
            model.addAttribute("commentaire",commentaire);
            model.addAttribute("erreurSaisieCommentaire", erreurSaisieCommentaire);
            return "secteurs";
        } else {
            siteService.ajoutCommentaire(commentaire,idSite,email);
            majModelSecteur(model,idSite,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
            return "secteurs";
        }
    }
    @PostMapping(value = "/secteurs/modifCommentaire")
    public String modifCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,  BindingResult result, @RequestParam("idSite") int idSite, HttpSession httpSession) {

        LOGGER.debug("modif un commentaire");

        if (result.hasErrors()){
            /** Garder la liste des secteurs de l'utilisateur */
            majModelSecteur(model,idSite,httpSession);
            model.addAttribute("commentaire",commentaire);
            model.addAttribute("erreurSaisieCommentaire", erreurSaisieCommentaire);
            return "secteurs";
        } else {
            siteService.modifCommentaire(commentaire,idSite);
            majModelSecteur(model,idSite,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
            return "secteurs";
        }
    }
    @PostMapping(value = "/secteurs/supprCommentaire")
    public String supprCommentaireSubmit(Model model, @RequestParam("idCommentaire") int idCommentaire,  @RequestParam("idSite") int idSite,  HttpSession httpSession) {

        LOGGER.debug("suppression du formulaire commentaire");

        siteService.supprCommentaire(idCommentaire,idSite);
        majModelSecteur(model,idSite,httpSession);
        Commentaire com = new Commentaire();
        model.addAttribute("commentaire",com);
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
    public String proposerSecteurSubmit(Model model, @Valid @ModelAttribute("secteur") Secteur secteur, BindingResult result, @RequestParam("idSite") int idSite,HttpSession httpSession) {

        LOGGER.debug("submit du formulaire secteur");

        if (result.hasErrors()){
            LOGGER.debug("erreur du formulaire secteur");
            model.addAttribute("erreurSaisieSecteur", erreurSaisieSecteur);
            Secteur secteurNouveau = new Secteur();
            model.addAttribute("site", siteService.getSite(idSite));
            model.addAttribute("secteur", secteurNouveau);
            return "ajoutsecteur";
        } else {
            siteService.ajoutSecteur(idSite, secteur);
            majModelSecteur(model,idSite,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
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
    public String proposerVoieSubmit(Model model, @Valid @ModelAttribute("voie") Voie voie, BindingResult result, @RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur) {

        LOGGER.debug("submit du formulaire voie");

        if (result.hasErrors()){
            LOGGER.debug("erreur du formulaire voie");
            model.addAttribute("erreurSaisieVoie", erreurSaisieVoie);
            Site site = siteService.getSite(idSite);
            Secteur secteur = siteService.getSecteur(idSecteur);
            model.addAttribute("site", site);
            model.addAttribute("secteur", secteur);
            return "ajoutvoie";
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
    public String proposerLongueurSubmit(Model model, @Valid @ModelAttribute("longueur") Longueur longueur, BindingResult result, @RequestParam("idSite") int idSite,@RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie) {

        LOGGER.debug("submit du formulaire longueur");

        if (result.hasErrors()){
            LOGGER.debug("erreur du formulaire longueur");
            model.addAttribute("erreurSaisieLongueur", erreurSaisieLongueur);
            Longueur longueurNouveau = new Longueur();
            model.addAttribute("site", siteService.getSite(idSite));
            model.addAttribute("secteur", siteService.getSecteur(idSecteur));
            model.addAttribute("voie", siteService.getVoie(idVoie));
            model.addAttribute("longueur", longueurNouveau);
            return "ajoutlongueur";
        } else {
            siteService.ajoutLongueur(idVoie, longueur);
            model.addAttribute("site", siteService.getSite(idSite));
            model.addAttribute("secteur", siteService.getSecteur(idSecteur));
            model.addAttribute("voie", siteService.getVoie(idVoie));
            return "longueurs";
        }
    }

    @PostMapping(value = "/secteurs/modifAmi")
    public String modifAmi (@RequestParam int idSite, RedirectAttributes redirectAttributes) {
        LOGGER.debug("Changement de du TOP Ami de l'escalade");
        siteService.changeAmi(idSite);
        redirectAttributes.addAttribute("idSite",idSite);
        return "redirect:/secteurs";
    }

    private void majModelSecteur (Model model, int idSite, HttpSession httpSession) {
        boolean ami = false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurService.findUtilisateurByEmail(auth.getName());
        if (utilisateur != null) {
            httpSession.setAttribute("utilisateurSession", utilisateur);
            Set<Role> roles = utilisateur.getRoles();
            for (Role role : roles) {
                if (role.getRole().equals("ROLE_AMI_ESCALADE")) { ami = true;}
            }
            model.addAttribute("ami", ami);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("roles", roles);
        } else {
            Utilisateur utilisateurVide = new Utilisateur();
            model.addAttribute("utilisateur", utilisateurVide);
        }

        Site site = siteService.getSite(idSite);
        model.addAttribute("site", site);
    }

    @GetMapping("/recherchersite")
    public String rechercherSite(Model model) {
        LOGGER.debug("page rechercher les sites");
        Site siteCherche = new Site();
        List<Site> sites= new ArrayList<>();
        model.addAttribute("siteCherche", siteCherche);
        model.addAttribute("sites", sites);
        return "recherchersite";
    }

    @PostMapping(value = "/recherchersite/recherche")
    public String rechercherSiteCherche (Model model,@ModelAttribute ("siteCherche") Site siteCherche) {
        LOGGER.debug("lancement d'une recherche");
        List<Site> sites= siteService.chercheSites(siteCherche);
        model.addAttribute("siteCherche", siteCherche);
        model.addAttribute("sites", sites);
        return "recherchersite";
    }

}

