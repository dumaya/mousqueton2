package dumaya.dev.service;

import dumaya.dev.model.Secteur;
import dumaya.dev.model.Site;
import dumaya.dev.model.Topo;
import dumaya.dev.model.User;
import dumaya.dev.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

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
        site.getSecteurs().add(secteur);
        siteRepository.save(site);
    }
}
