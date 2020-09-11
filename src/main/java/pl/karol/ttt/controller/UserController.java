package pl.karol.ttt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.karol.ttt.model.User;

@Controller
@SessionAttributes("sessionUser")
public class UserController {

	@GetMapping("/")
	public String home() {
		return "start";
	}

	@GetMapping("/new-player")
	public String newPlayer(Model model) {

		if(model.containsAttribute("sessionUser")) {
			return "redirect:/multiplayer/";
		}

		return "new-player";
	}

	@GetMapping("/new-single-player")
	public String newSinglePlayer(Model model) {

		if(model.containsAttribute("sessionUser")) {
			return "redirect:/singleplayer/";
		} else {
			model.addAttribute("sessionUser", new User());
			model.addAttribute("isSinglePlayer", "yes");
			return "new-player";
		}
	}
}
