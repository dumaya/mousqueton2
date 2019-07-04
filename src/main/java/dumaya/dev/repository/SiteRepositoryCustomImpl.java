package dumaya.dev.repository;

import dumaya.dev.model.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SiteRepositoryCustomImpl implements SiteRepositoryCustom{
    @Autowired
    EntityManager em;

    @Override
    public List<Site> rechercheSiteMultiCriteres(Site siteCherche) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Site> cq = cb.createQuery(Site.class);

        Root<Site> site = cq.from(Site.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!siteCherche.getNom().equals("")) {
            predicates.add(cb.like(site.get("nom"), "%" + siteCherche.getNom() + "%"));
        }

        if (!siteCherche.getDescription().equals("")) {
            predicates.add(cb.like(site.get("description"), "%" + siteCherche.getDescription() + "%"));
        }
        if (!(siteCherche.getCotationMin().isEmpty())) {
            predicates.add(cb.greaterThanOrEqualTo(site.get("cotationMin"), siteCherche.getCotationMin()));
        }
        if (!siteCherche.getCotationMax().isEmpty()) {
            predicates.add(cb.lessThanOrEqualTo(site.get("cotationMax"), siteCherche.getCotationMax()));
        }
        if (siteCherche.getAltitude() != null) {
            predicates.add(cb.greaterThanOrEqualTo(site.get("altitude"), siteCherche.getAltitude()));
        }
        if (!siteCherche.getOrientation().equals("")) {
            predicates.add(cb.equal(site.get("orientation"), siteCherche.getOrientation()));
        }
        if (!siteCherche.getLieu().equals("")) {
            predicates.add(cb.like(site.get("lieu"), "%" + siteCherche.getLieu() + "%"));
        }
        if (!siteCherche.getTypeRoche().equals("")) {
            predicates.add(cb.like(site.get("typeRoche"), "%" + siteCherche.getTypeRoche() + "%"));
        }
        if (!siteCherche.getAncrage().equals("")) {
            predicates.add(cb.like(site.get("ancrage"), "%" + siteCherche.getAncrage() + "%"));
        }
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return em.createQuery(cq).getResultList();
    }
}
