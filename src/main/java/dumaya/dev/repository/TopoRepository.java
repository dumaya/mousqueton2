package dumaya.dev.repository;

import dumaya.dev.model.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopoRepository extends JpaRepository<Topo, Long> {


    Topo findById(int id);

    List<Topo> findAllByDispoPretTrueAndUserEmprunteurIsNull();

    Topo findByIdAndDispoPretTrueAndUserEmprunteurIsNull(int id);

    Topo findByIdAndUserEmprunteurIsNull(int id);

    List<Topo> findAllByUserProprietaire_Id(int idCourant);

    Topo findByIdAndUserEmprunteurIsNotNull(int id);
}
