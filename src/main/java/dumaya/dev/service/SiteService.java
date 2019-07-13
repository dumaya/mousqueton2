package dumaya.dev.service;

import dumaya.dev.model.*;
import dumaya.dev.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    private final SiteRepository siteRepository;
    private final SecteurRepository secteurRepository;
    private final VoieRepository voieRepository;
    private final LongueurRepository longueurRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CommentaireRepository commentaireRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

    public SiteService(SiteRepository siteRepository, SecteurRepository secteurRepository, VoieRepository voieRepository, LongueurRepository longueurRepository, UtilisateurRepository utilisateurRepository, CommentaireRepository commentaireRepository) {
        this.siteRepository = siteRepository;
        this.secteurRepository = secteurRepository;
        this.voieRepository = voieRepository;
        this.longueurRepository = longueurRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.commentaireRepository = commentaireRepository;
    }


    /**
     * @return liste de tous les topos en base
     */
    public List<Site> listeSites() {
        return siteRepository.findAll();
    }

    /**
     * Enregistrement du formulaire de création de Site. Seul le nom est obligatoire
     * @param site un objet de type site à enregistrer
     *
     */
    public void enregistrer(Site site) {
        siteRepository.save(site);
    }

    /**
     * Ramener le site correspondant à l'idSite
     * @param siteId
     * @return Site
     */
    public Site getSite(int siteId) {
        return siteRepository.findById(siteId);
    }

    /**
     * Ajouter un secteur à un site
     * @param idSite
     * @param secteur
     */
    public void ajoutSecteur(int idSite, Secteur secteur) {
        Site site = siteRepository.findById(idSite);
        Secteur secteurEnreg = new Secteur();
        secteurEnreg.setSite(site);
        secteurEnreg.setDescription(secteur.getDescription());
        secteurEnreg.setNom(secteur.getNom());
        secteurRepository.save(secteurEnreg);
        List<Secteur> secteurs = site.getSecteurs();
        secteurs.add(secteurEnreg);
        site.setSecteurs(secteurs);
        siteRepository.save(site);
    }

    /**
     * chercher le secteur par id
     * @param idSecteur
     * @return Secteur
     */
    public Secteur getSecteur(int idSecteur) {
        return secteurRepository.findById(idSecteur);
    }

    /**
     * ajouter une voie à un secteur
     * @param idSecteur
     * @param voie
     */
    public void ajoutVoie(int idSecteur, Voie voie) {

        Secteur secteur = secteurRepository.findById(idSecteur);
        Voie voieEnreg = new Voie();
        voieEnreg.setSecteur(secteur);
        voieEnreg.setDescription(voie.getDescription());
        voieEnreg.setNom(voie.getNom());
        voieEnreg.setCotation(voie.getCotation());
        voieRepository.save(voieEnreg);
        List<Voie> voies = secteur.getVoies();
        voies.add(voieEnreg);
        secteur.setVoies(voies);
        secteurRepository.save(secteur);
    }

    /**
     * ramener une voie par id
     * @param idVoie
     * @return Voie
     */
    public Voie getVoie(int idVoie) {
        return voieRepository.findById(idVoie);
    }

    /**
     * ajouter une longueur à une voie
     * @param idVoie
     * @param longueur
     */
    public void ajoutLongueur(int idVoie, Longueur longueur) {

        Voie voie = voieRepository.findById(idVoie);
        Longueur longueurEnreg = new Longueur();
        longueurEnreg.setVoie(voie);
        longueurEnreg.setDescription(longueur.getDescription());
        longueurEnreg.setNom(longueur.getNom());
        longueurEnreg.setCotation(longueur.getCotation());
        longueurRepository.save(longueurEnreg);
        List<Longueur> longueurs = voie.getLongueurs();
        longueurs.add(longueurEnreg);
        voie.setLongueurs(longueurs);
        voieRepository.save(voie);
    }

    /**
     * switcher le top "site officiel ami de l'escalade" oui / non
     * @param idSite
     */
    public void changeAmi(int idSite) {
        Site site = siteRepository.findById(idSite);
        if (site.isOfficielAmiEscalade()) {
            site.setOfficielAmiEscalade(false);
        } else {
            site.setOfficielAmiEscalade(true);
        }
        siteRepository.save(site);
    }

    /**
     * recherche multi criteres
     * @param siteCherche objet de type suite qui contient les critères de recherche à appliquer
     * @return Liste des sites correspondant aux critères renseignés (portés par un objet de type suite)
     */
    public List<Site> chercheSites(Site siteCherche) {
        List<Site> sitesTrouve= new ArrayList<>();
        sitesTrouve=siteRepository.rechercheSiteMultiCriteres(siteCherche);
        return sitesTrouve;
    }

    /**
     * ajouter un commentaire à un site
     * @param commentaire
     * @param idSite
     * @param email
     */
    public void ajoutCommentaire(Commentaire commentaire, int idSite, String email) {
        Site site=siteRepository.findById(idSite);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        commentaire.setUtilisateurCommentaire(utilisateur);
        commentaire.setSite(site);
        commentaireRepository.save(commentaire);
        List<Commentaire> commentaires = site.getCommentaires();
        commentaires.add(commentaire);
        site.setCommentaires(commentaires);
        siteRepository.save(site);

    }

    /**
     * supprimer le commentaire
     * @param idCommentaire
     * @param idSite
     */
    public void supprCommentaire(int idCommentaire, int idSite) {
        Site site=siteRepository.findById(idSite);
        Commentaire commentaire=commentaireRepository.findById(idCommentaire);
        commentaireRepository.delete(commentaire);
        siteRepository.save(site);
    }

    /**
     * modifier le message du commentaire
     * @param commentaire (message modifié)
     * @param idSite
     */
    public void modifCommentaire(Commentaire commentaire, int idSite) {
        Site site=siteRepository.findById(idSite);
        Commentaire commModifie = commentaireRepository.findById(commentaire.getId());
        commModifie.setMessage(commentaire.getMessage());
        commentaireRepository.save(commModifie);
        siteRepository.save(site);
    }
}
