package dumaya.dev;

import java.util.HashSet;

import dumaya.dev.model.Role;
import dumaya.dev.model.Utilisateur;
import dumaya.dev.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MousquetonApp implements CommandLineRunner{
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;*/

	public static void main(String[] args) {
		SpringApplication.run(MousquetonApp.class, args);
	}

	public void run(String... args) throws Exception {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setName("ADMIN");
		utilisateur.setLastName("ADMIN");
		utilisateur.setEmail("admin@cnss.ne");
		//BCryptPasswordEncoder bCryptPasswordEncoderLocal = new BCryptPasswordEncoder();
		//String encodepwd = bCryptPasswordEncoderLocal.encode("admin2017");
		//utilisateur.setPassword(encodepwd);
		//System.out.println("admin2017  encoder = " +encodepwd);
		utilisateur.setPassword("$2a$10$fE7BKQcc.tesDzaptjL8luXZB6MV5rvUJ13ub5aVYKqnoPmMqYd8m");
		utilisateur.setActive(true);
		//Role
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		utilisateur.setRoles(roles);
		utilisateurRepository.save(utilisateur);
	}
}
