package pl.karol.ttt.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.karol.ttt.model.Board.Column;
import pl.karol.ttt.model.Board.Row;
import pl.karol.ttt.model.Game;
import pl.karol.ttt.model.User;

@Controller
@RequestMapping("/multiplayer")
public class GameController {

	private Map<String, User> users = new HashMap<>();
	private Map<String, Game> calls = new HashMap<>();
	private Map<String, Game> games = new HashMap<>();

	@PostMapping("/player-create")
	public String playerCreated(@RequestParam("nickname") String nickname, HttpSession session, Model model, RedirectAttributes red) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher = pattern.matcher(nickname);
		if(users.containsKey(nickname)) {
			red.addFlashAttribute("msg","Gracz o tej nazwie już istnieje");
			return "redirect:/new-player/";
		} else if(!matcher.matches()) {
			red.addFlashAttribute("msg","Nazwa gracza nie może zawierać znaków specjalnych");
			return "redirect:/new-player/";
		} else {
			session.setAttribute("sessionUser", new User());
			return "redirect:/multiplayer/player-created?nickname=" + nickname;
		}
	}

	//Tworzenie nowego gracza
	@GetMapping("/player-created")
	public String playerCreated(@RequestParam("nickname") String nickname, @SessionAttribute("sessionUser") User user) {
		user.setNick(nickname);
		users.put(user.getNick(), user);
		return "redirect:/multiplayer/";
	}

	//Logout
	@GetMapping("/logout")
	public String deletePlayerContent(@SessionAttribute("sessionUser") User user, HttpSession session) {
		calls.values().removeIf(call ->  call.getPlayerX().equals(user) || call.getPlayerO().equals(user));
		games.values().removeIf(call ->  call.getPlayerX().equals(user) || call.getPlayerO().equals(user));
		users.remove(user.getNick());
		session.invalidate();
		return "redirect:/";
	}

	//Wyświetlanie listy graczy
	@GetMapping("/")
	public String players(@SessionAttribute("sessionUser") User user, Model model) {
		model.addAttribute("users", users);
		model.addAttribute("calls", calls);
		model.addAttribute("games", games);
		return "multiplayer";
	}

	//Wyzwanie
	@GetMapping("/call/{calledUser}")
	public String call(@PathVariable("calledUser") String calledUser, @SessionAttribute("sessionUser") User user) {

		if(!calls.containsKey(user.getNick() + "+" + calledUser) && !games.containsKey(user.getNick() + "+" + calledUser)) {
			Game game = new Game(user, users.get(calledUser));
			String gameName = game.getPlayerX().getNick() + "+" + game.getPlayerO().getNick();
			calls.put(gameName, game);
		} else {
			return "redirect:/multiplayer/";
		}
		return "redirect:/multiplayer/game/" + user.getNick() + "+" + calledUser;
	}

	//Akceptacja wyzwania
	@GetMapping("/ready/{userWhoCalled}")
	public String setPlayerOReady(@PathVariable("userWhoCalled") String userWhoCalled, @SessionAttribute("sessionUser") User user) {
		Game myGame = calls.get(userWhoCalled + "+" + user.getNick());
		games.put(userWhoCalled + "+" + user.getNick(), myGame);
		calls.remove(userWhoCalled + "+" + user.getNick());
		return "redirect:/multiplayer/game/" + userWhoCalled + "+" + user.getNick();
	}

	//Usunięcie gry
	@GetMapping("/game/{game}/delete")
	public String deleteGame(@PathVariable("game") String game, @SessionAttribute("sessionUser") User user) {

		if(games.containsKey(game)) {
			Game myGame = games.get(game);
			if(myGame.getPlayerX().equals(user)) {
				games.remove(game);
			} else {
				throw new IllegalAccessError("Nie można usunąc gry, której się nie stworzyło");
			}
		} else {
			throw new IllegalArgumentException("Nie znaleziono gry do usunięcia");
		}
		return "redirect:/multiplayer/";
	}

	//Wyświetlanie gry
	@GetMapping("/game/{game}")
	public String startGame(@PathVariable("game") String game, @SessionAttribute("sessionUser") User user, Model model) {
	    model.addAttribute("gameName", game);

		if (games.containsKey(game)) {
	       model.addAttribute("game", games.get(game));
	    } else if (calls.containsKey(game)){
	    	model.addAttribute("game", calls.get(game));
	    } else {
	       return "redirect:/multiplayer/";
	    }
		return "game";
	}

	//Ustawianie pól
	@GetMapping("/game/{game}/{row}/{column}")
	public String setField(@PathVariable("game") String game, @PathVariable("row") Row row, @PathVariable("column") Column column, @SessionAttribute("sessionUser") User user) {

		if (games.containsKey(game)) {
			Game myGame = games.get(game);
			myGame.put(row, column, user);
			return "redirect:/multiplayer/game/{game}";
		} else if (calls.containsKey(game)) {
			Game myGame = calls.get(game);
			myGame.put(row, column, user);
			return "redirect:/multiplayer/game/{game}";
		} else {
			return "redirect:/multiplayer/";
		}
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}
}
