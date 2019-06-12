package dumaya.dev.controller;


import dumaya.dev.model.Role;
import dumaya.dev.model.Topo;
import dumaya.dev.model.User;
import dumaya.dev.service.TopoService;
import dumaya.dev.service.UserService;
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

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class TopoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopoController.class);

    @Autowired
    private TopoService topoService;

    @Autowired
    private UserService userService;

    @Value("${erreur.saisie.topo}")
    private String erreurSaisieTopo;

    @Value("${erreur.bouton.topo}")
    private String erreurBoutonTopo;

    @RequestMapping("/")
    public String racine() {
        return "index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user != null) {
            Set<Role> roles = user.getRoles();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
        }

        return "index";
    }

    @GetMapping("/mestopos")
    public String gestionTopo(Model model) {
        LOGGER.debug("Liste des topos de l'utilisateur");

        /** Liste des topos de l'utilisateur */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Integer idCourant = user.getId();
        List<Topo> topos= topoService.listeMesTopos(idCourant);

        model.addAttribute("topos", topos);

        return "mestopos";
    }

    @GetMapping("/ajouttopo")
    public String ajoutTopo(Model model) {

        /* Formulaire de création d'un topo */
        LOGGER.debug("Init formulaire topo");
        Topo topo = new Topo();
        model.addAttribute("topo", topo);

        return "ajouttopo";
    }


    @PostMapping(value = "/ajouttopo")
    public String proposerTopoSubmit(Model model, @Valid @ModelAttribute("topo") Topo topo, BindingResult result, Principal principal) {

        LOGGER.debug("submit du formulaire topo");

        if (result.hasErrors()){
            /** Garder la liste des topos de l'utilisateur */
            List<Topo> topos= topoService.listeTopos();
            model.addAttribute("topos", topos);
            model.addAttribute("erreurSaisieTopo", erreurSaisieTopo);
            return "ajouttopo";
        } else {
            User user = userService.findUserByEmail(principal.getName());

            topoService.enregistrer(topo, user);
            return "redirect:/mestopos";
        }
    }

    @PostMapping(value = "/mestopos/modifDispoPret")
    public String modifDispoPret(@RequestParam int idTopo, RedirectAttributes ra) {
        LOGGER.debug("Changement de la disponibilité du topo");
        topoService.changeDispo(idTopo);
        ra.addFlashAttribute("successFlash", "Dispo Topo modifiée");
        return "redirect:/mestopos";
    }

    @PostMapping(value = "/mestopos/supprimerTopo")
    public String supprimerTopo(@RequestParam int idTopo, RedirectAttributes ra) {
        LOGGER.debug("suppression d'un Topo");
        topoService.supprimerDispo(idTopo);
        ra.addFlashAttribute("successFlash", "Topo supprimé.");
        return "redirect:/mestopos";
    }

    @PostMapping(value = "/mestopos/accepterEmprunt")
    public String accepterEmprunt(@RequestParam int idTopo, RedirectAttributes ra) {
        LOGGER.debug("accepter une demande d'emprunt : passer le dispo pret à false");
        topoService.accepterEmprunt(idTopo);
        ra.addFlashAttribute("successFlash", "accepter demande d'emprunt");
        return "redirect:/mestopos";
    }

    @PostMapping(value = "/mestopos/retourEmprunt")
    public String retourEmprunt(@RequestParam int idTopo, RedirectAttributes ra) {
        LOGGER.debug("retour d'emprunt : passer le dispo pret à true et user emprunteur vide");
        topoService.retourEmprunt(idTopo);
        ra.addFlashAttribute("successFlash", "retour d'emprunt");
        return "redirect:/mestopos";
    }

    @GetMapping("/topos")
    public String affichelesTopos(Model model) {
        LOGGER.debug("page afficher les topos dispos");
        List<Topo> topos= topoService.listeTopos();
        model.addAttribute("topos", topos);
        return "topos";
    }

    @PostMapping(value = "/topos/emprunt")
    public String emprunterTopo(@RequestParam int idTopo, RedirectAttributes ra) {
        LOGGER.debug("bouton emprunter un Topo");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        topoService.emprunterTopo(idTopo, user);
        ra.addFlashAttribute("successFlash", "Demande d'emprunt Topo.");
        return "redirect:/topos";
    }

}
