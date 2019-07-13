package dumaya.dev.service;


import dumaya.dev.exception.TopoErrorException;
import dumaya.dev.model.Topo;
import dumaya.dev.model.Utilisateur;
import dumaya.dev.repository.TopoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopoService {

    private final TopoRepository topoRepository;

    public TopoService(TopoRepository topoRepository) {
        this.topoRepository = topoRepository;
    }

    /**
     * Enregistrement du formulaire de création de Topo. Seul le nom, le lieu et l'info dispoPret sont obligatoires
     * @param topo un objet de type topo à enregistrer
     * @param utilisateur du utilisateur courant
     */
    public void enregistrer(Topo topo, Utilisateur utilisateur) {
        topo.setUtilisateurProprietaire(utilisateur);
        topoRepository.save(topo);
    }

    /**
     * @return liste de tous les topos en base
     */
    public List<Topo> listeTopos() {
        return topoRepository.findAllByDispoPretTrueAndUtilisateurEmprunteurIsNull();
    }

    /**
     * @return liste de tous les topos de l'utilisateur courant
     * @param idCourant
     */
    public List<Topo> listeMesTopos(Integer idCourant)
    {
        return topoRepository.findAllByUtilisateurProprietaire_Id(idCourant);
    }

    /**
     * @param id Changer la dispo d'un topo par son id
     */

    public void changeDispo(int id) {
        Topo topo = topoRepository.findByIdAndUtilisateurEmprunteurIsNull(id);
        if (topo.isDispoPret()){
            topo.setDispoPret(false);
        } else {
            topo.setDispoPret(true);
        }
        topoRepository.save(topo);
    }

    /**
     * @param id Supprimer un topo par son id
     */
    public void supprimerDispo(int id) {
        Topo topo = topoRepository.findByIdAndUtilisateurEmprunteurIsNull(id);
        topoRepository.delete(topo);
    }

    /**
     * Faire une demande d'emprunt
     * @param idTopo
     * @param utilisateur
     */
    public void emprunterTopo(int idTopo, Utilisateur utilisateur) {
        Topo topo = topoRepository.findByIdAndDispoPretTrueAndUtilisateurEmprunteurIsNull(idTopo);
        if (topo == null) {
            throw new TopoErrorException();
        }
        topo.setUtilisateurEmprunteur(utilisateur);
        topoRepository.save(topo);
    }

    /**
     * Accepter l'emprunt, le user emprunter ne doit pas être vide
     * @param idTopo
     */
    public void accepterEmprunt(int idTopo) {
        Topo topo = topoRepository.findByIdAndUtilisateurEmprunteurIsNotNull(idTopo);
        topo.setDispoPret(false);
        topoRepository.save(topo);
    }
    /**
     * Retour d'emprunt, le user emprunter ne doit pas être vide
     * @param idTopo
     */
    public void retourEmprunt(int idTopo) {
        Topo topo = topoRepository.findByIdAndUtilisateurEmprunteurIsNotNull(idTopo);
        topo.setDispoPret(true);
        topo.setUtilisateurEmprunteur(null);
        topoRepository.save(topo);
    }


}