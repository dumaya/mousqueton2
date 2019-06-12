package dumaya.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dumaya.dev.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
	public Genre findByCode(String code);
}
