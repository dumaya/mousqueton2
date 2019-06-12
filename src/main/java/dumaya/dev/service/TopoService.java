package dumaya.dev.service;


import dumaya.dev.exception.TopoErrorException;
import dumaya.dev.model.Topo;
import dumaya.dev.model.User;
import dumaya.dev.repository.TopoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopoService {

    @Autowired
    private TopoRepository topoRepository;

    /**
     * Enregistrement du formulaire de création de Topo. Seul le nom, le lieu et l'info dispoPret sont obligatoires
     * @param topo un objet de type topo à enregistrer
     * @param user du user courant
     */
    public void enregistrer(Topo topo, User user) {
        topo.setUserProprietaire(user);
        topoRepository.save(topo);
    }

    /**
     * @return liste de tous les topos en base
     */
    public List<Topo> listeTopos() {
        return topoRepository.findAllByDispoPretTrueAndUserEmprunteurIsNull();
    }

    /**
     * @return liste de tous les topos de l'utilisateur courant
     * @param idCourant
     */
    public List<Topo> listeMesTopos(Integer idCourant)
    {
        return topoRepository.findAllByUserProprietaire_Id(idCourant);
    }

    /**
     * @param id Changer la dispo d'un topo par son id
     */

    public void changeDispo(int id) {
        Topo topo = topoRepository.findByIdAndUserEmprunteurIsNull(id);
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
        Topo topo = topoRepository.findByIdAndUserEmprunteurIsNull(id);
        topoRepository.delete(topo);
    }

    /**
     * Faire une demande d'emprunt
     * @param idTopo
     * @param user
     */
    public void emprunterTopo(int idTopo, User user) {
        Topo topo = topoRepository.findByIdAndDispoPretTrueAndUserEmprunteurIsNull(idTopo);
        if (topo == null) {
            throw new TopoErrorException();
        }
        topo.setUserEmprunteur(user);
        topoRepository.save(topo);
    }

    /**
     * Accepter l'emprunt, le user emprunter ne doit pas être vide
     * @param idTopo
     */
    public void accepterEmprunt(int idTopo) {
        Topo topo = topoRepository.findByIdAndUserEmprunteurIsNotNull(idTopo);
        topo.setDispoPret(false);
        topoRepository.save(topo);
    }
    /**
     * Retour d'emprunt, le user emprunter ne doit pas être vide
     * @param idTopo
     */
    public void retourEmprunt(int idTopo) {
        Topo topo = topoRepository.findByIdAndUserEmprunteurIsNotNull(idTopo);
        topo.setDispoPret(true);
        topo.setUserEmprunteur(null);
        topoRepository.save(topo);
    }


}