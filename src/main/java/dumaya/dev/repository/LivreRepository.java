package dumaya.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dumaya.dev.model.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

}
