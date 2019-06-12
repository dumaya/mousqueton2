package dumaya.dev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dumaya.dev.model.Genre;
import dumaya.dev.model.Livre;
import dumaya.dev.service.GenreServiceHelper;
import dumaya.dev.service.LivreServiceInterface;

@Controller
public class LivreController {
	
	@Autowired
	LivreServiceInterface livreServiceInterface;
	
	@GetMapping("/livre")
	public String savePage(Model model) {
		model.addAttribute("livreForm", new LivreForm());
		model.addAttribute("genre", new Genre());
		model.addAttribute("allGenres", livreServiceInterface.getAllGenres());
		model.addAttribute("allLivres", livreServiceInterface.getAllLivres());
		return "livre";
	}
	
	
	@PostMapping("/livre/save")
	public String savelivre(@ModelAttribute("livreForm") LivreForm livreForm,
			final RedirectAttributes redirectAttributes) {
		Livre livre = convertLivreFormToLivre(livreForm);
		if(livreServiceInterface.saveLivre(livre)!=null) {
			redirectAttributes.addFlashAttribute("saveLivre", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveLivre", "unsuccess");
		}
		
		return "redirect:/livre";
	}
	
	private Livre convertLivreFormToLivre(LivreForm livreForm) {
		List<Genre> genres = new ArrayList<Genre>();
		Livre livre = new Livre(livreForm.getTitre());
		
		for(String genreCode: livreForm.getTabGenres()) {
			Genre genre = GenreServiceHelper.findOne(genreCode);
		    genre.setLivre(livre);
			genres.add(genre);
		}
		
		livre.setGenres(genres);
		return livre;
	}


	@RequestMapping(value = "/livre/{operation}/{empId}", method = RequestMethod.GET)
	public String editRemovelivre(@PathVariable("operation") String operation,
			@PathVariable("empId") Long livreId, final RedirectAttributes redirectAttributes,
			Model model) {
		if(operation.equals("delete")) {
			if(livreServiceInterface.deleteLivre(livreId)) {
				redirectAttributes.addFlashAttribute("deletion", "success");
			} else {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
			}
		}
		
		return "redirect:/livre";
	}
	
	@RequestMapping(value = "/livre/update", method = RequestMethod.POST)
	public String updatelivre(@ModelAttribute("editLivre") Livre editLivre,
			final RedirectAttributes redirectAttributes) {
		if(livreServiceInterface.editLivre(editLivre)!=null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
//		return "redirect:/savepage";
		return "redirect:/livre";
	}

}
