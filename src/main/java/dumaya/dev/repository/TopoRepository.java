package dumaya.dev.repository;

import dumaya.dev.model.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopoRepository extends JpaRepository<Topo, Long> {


    Topo findById(int id);

    List<Topo> findAllByDispoPretTrueAndUtilisateurEmprunteurIsNull();

    Topo findByIdAndDispoPretTrueAndUtilisateurEmprunteurIsNull(int id);

    Topo findByIdAndUtilisateurEmprunteurIsNull(int id);

    List<Topo> findAllByUtilisateurProprietaire_Id(int idCourant);

    Topo findByIdAndUtilisateurEmprunteurIsNotNull(int id);
}
