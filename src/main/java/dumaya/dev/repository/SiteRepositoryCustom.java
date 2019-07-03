package dumaya.dev.repository;

import dumaya.dev.model.Site;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepositoryCustom {
    List<Site> rechercheSiteMultiCriteres(Site siteCherche);
}
