package dumaya.dev.repository;

import dumaya.dev.model.Longueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongueurRepository extends JpaRepository<Longueur, Long> {

    Longueur findById(int id);

}
