package dumaya.dev.service;

import dumaya.dev.model.Utilisateur;

public interface UtilisateurService {
	public Utilisateur findUtilisateurByEmail(String email);
	public void saveUtilisateur(Utilisateur utilisateur);
}
