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

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SecteurRepository secteurRepository;
    @Autowired
    private VoieRepository voieRepository;
    @Autowired
    private LongueurRepository longueurRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private CommentaireRepository commentaireRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);


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

    public Site getSite(int siteId) {
        return siteRepository.findById(siteId);
    }

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

    public Secteur getSecteur(int idSecteur) {
        return secteurRepository.findById(idSecteur);
    }

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

    public Voie getVoie(int idVoie) {
        return voieRepository.findById(idVoie);
    }

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

    public void changeAmi(int idSite) {
        Site site = siteRepository.findById(idSite);
        if (site.isOfficielAmiEscalade()) {
            site.setOfficielAmiEscalade(false);
        } else {
            site.setOfficielAmiEscalade(true);
        }
        siteRepository.save(site);
    }

    public List<Site> chercheSites(Site siteCherche) {
        List<Site> sitesTrouve= new ArrayList<>();
        sitesTrouve=siteRepository.rechercheSiteMultiCriteres(siteCherche);
        return sitesTrouve;
    }

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

    public void supprCommentaire(int idCommentaire, int idSite) {
        Site site=siteRepository.findById(idSite);
        Commentaire commentaire=commentaireRepository.findById(idCommentaire);
        commentaireRepository.delete(commentaire);
        siteRepository.save(site);
    }
    public void modifCommentaire(Commentaire commentaire, int idSite) {
        Site site=siteRepository.findById(idSite);
        Commentaire commModifie = commentaireRepository.findById(commentaire.getId());
        commModifie.setMessage(commentaire.getMessage());
        commentaireRepository.save(commModifie);
        siteRepository.save(site);
    }
}
