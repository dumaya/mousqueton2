package dumaya.dev.repository;

import dumaya.dev.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long>,SiteRepositoryCustom {

    Site findById(int id);


}
