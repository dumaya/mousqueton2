package dumaya.dev.repository;

import dumaya.dev.model.Site;
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

    EntityManager em;

    @Override
    public List<Site> rechercheSiteMultiCriteres(Site siteCherche) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Site> cq = cb.createQuery(Site.class);

        Root<Site> book = cq.from(Site.class);
        List<Predicate> predicates = new ArrayList<>();

        if (siteCherche.getNom().equals("")) {
            predicates.add(cb.like(book.get("nom"), "%" + siteCherche.getNom() + "%"));
        }
        if (siteCherche.getDescription().equals("")) {
            predicates.add(cb.like(book.get("description"), "%" + siteCherche.getDescription() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
