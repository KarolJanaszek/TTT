package pl.karol.ttt.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import pl.karol.ttt.model.Board.Column;
import pl.karol.ttt.model.Board.Row;
import pl.karol.ttt.model.Game;
import pl.karol.ttt.model.User;

//todo
@Controller
@RequestMapping("/leplayer")
public class SingleplayerGameController {

	private List<Game> games = new LinkedList<Game>();

	private User pcUser = new User();
	private Game game;

	@GetMapping("/")
	public String players(@SessionAttribute("sessionUser") User user) {
		return "singleplayer";
	}

	@PostMapping("/player-created")
	public String playerCreated(@RequestParam("nickname") String nickname, @SessionAttribute("sessionUser") User user) {
		user.setNick(nickname);
		return "redirect:/singleplayer/";
	}

	@PostMapping("/play")
	public String play(@RequestParam("role") String role, @SessionAttribute("sessionUser") User user) {
		pcUser.setNick("Kommputer");
		if(role.equals("X")) {
			game = new Game(user, pcUser);
			games.add(game);
		} else {
			game = new Game(pcUser, user);
			games.add(game);
		}
		return "redirect:/singleplayer/game/" + games.indexOf(game);
	}

	@GetMapping("/game/{game}")
	public String game(@PathVariable("game") Integer game, @SessionAttribute("sessionUser") User user, Model model) {
		model.addAttribute("gameName", game);
		Game myGame = games.get(game);

		if (myGame!=null) {
			if(myGame.getLenght()==0 && myGame.getPlayerX().equals(pcUser)) {
				myGame.bestMove();
			}
	       model.addAttribute("game", games.get(game));
	    } else {
	       return "redirect:/singleplayer/";
	    }
		return "game";
	}

	@GetMapping("/game/{game}/{row}/{column}")
	public String setField(@PathVariable("game") Integer game, @PathVariable("row") Row row, @PathVariable("column") Column column, @SessionAttribute("sessionUser") User user) {

		if (games.get(game)!=null) {
			Game myGame = games.get(game);
			myGame.put(row, column, user);
			myGame.bestMove();
			return "redirect:/singleplayer/game/{game}";
		} else {
			return "redirect:/singleplayer/";
		}
	}
}
