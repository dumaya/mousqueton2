package dumaya.dev.service;

import java.util.List;

import dumaya.dev.model.Genre;
import dumaya.dev.model.Livre;

public interface LivreServiceInterface {
	
	public Livre saveLivre(Livre livre);
	public Boolean deleteLivre(Long livreId);
	public Livre editLivre(Livre livre);
	public Livre findLivre(Long livreId);
	public List<Livre> getAllLivres();
	public List<Genre> getAllGenres();

}
