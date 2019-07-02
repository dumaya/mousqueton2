package dumaya.dev.repository;

import dumaya.dev.model.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoieRepository extends JpaRepository<Voie, Long> {

    Voie findById(int id);

}
