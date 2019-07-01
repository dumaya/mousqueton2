package dumaya.dev.service;

import dumaya.dev.model.Secteur;
import dumaya.dev.model.Site;
import dumaya.dev.repository.SecteurRepository;
import dumaya.dev.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SecteurRepository secteurRepository;

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
}
