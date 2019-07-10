package dumaya.dev.repository;

import dumaya.dev.model.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopoRepository extends JpaRepository<Topo, Long> {


    Topo findById(int id);

    /**
     * @return Liste des topos disponibles au pret
     */
    List<Topo> findAllByDispoPretTrueAndUtilisateurEmprunteurIsNull();

    /**
     * ramener le topo par id (avec les crietes pour s'assurer qu'il est toujours dispo au pret)
     * @param id
     * @return Topo
     */
    Topo findByIdAndDispoPretTrueAndUtilisateurEmprunteurIsNull(int id);

    /**
     * Cibler un topo qui est à la main de son propriétaire (pour changer sa dispo par ex)
     * @param id
     * @return Topo
     */
    Topo findByIdAndUtilisateurEmprunteurIsNull(int id);

    /**
     * @param idCourant
     * @return Liste des topos du proprietaire connecté
     */
    List<Topo> findAllByUtilisateurProprietaire_Id(int idCourant);

    /**
     * @param id
     * @return Topo réservé
     */
    Topo findByIdAndUtilisateurEmprunteurIsNotNull(int id);
}
