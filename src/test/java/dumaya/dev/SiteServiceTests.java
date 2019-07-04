package dumaya.dev;

import dumaya.dev.MousquetonApp;
import dumaya.dev.model.Secteur;
import dumaya.dev.service.SiteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= MousquetonApp.class)
//@SpringBootTest
public class SiteServiceTests {
	//@Autowired
	SiteService siteService;

	//@Test
	public void testGetSecteur() {
		//initialisation
		int idSecteur = 100;
		//call service / response
		Secteur reponseSecteur = siteService.getSecteur(idSecteur);
		//Assert
		Assert.assertNotNull(reponseSecteur);
		Assert.assertEquals("gare",reponseSecteur.getNom());
	}
}
