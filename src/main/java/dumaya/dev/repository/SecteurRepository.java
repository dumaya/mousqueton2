package dumaya.dev.repository;

import dumaya.dev.model.Secteur;
import dumaya.dev.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {

    Secteur findById(int id);

}
