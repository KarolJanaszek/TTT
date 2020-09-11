package pl.karol.ttt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import pl.karol.ttt.model.Board.Column;
import pl.karol.ttt.model.Board.Row;

class GameTest {
	@Test
	void unknownUser() {
		Game game = new Game(new User(), new User());
		assertThrows(IllegalStateException.class, () -> game.put(Row.R1, Column.C1, new User()));
	}

	@Test
	void sameUser() {
		Game game = new Game(new User(), new User());
		game.put(Row.R1, Column.C1, game.getPlayerX());
		assertThrows(IllegalStateException.class, () -> game.put(Row.R2, Column.C2, game.getPlayerX()));
	}

	@Test
	void samePlace() {
		Game game = new Game(new User(), new User());
		game.put(Row.R1, Column.C1, game.getPlayerX());
		assertThrows(IllegalStateException.class, () -> game.put(Row.R1, Column.C1, game.getPlayerO()));
	}

	@Test
	void canWin() {
		//___
		//___
		//___
		Game game = new Game(new User(), new User());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//___
		//_x_
		//___
		game.put(Row.R2, Column.C2, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//_o_
		//_x_
		//___
		game.put(Row.R1, Column.C2, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//xo_
		//_x_
		//___
		game.put(Row.R1, Column.C1, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//xo_
		//_x_
		//__o
		game.put(Row.R3, Column.C3, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//xo_
		//_x_
		//x_o
		game.put(Row.R3, Column.C1, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//xoo
		//_x_
		//x_o
		game.put(Row.R1, Column.C3, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//xoo
		//xx_
		//x_o
		game.put(Row.R2, Column.C1, game.getPlayerX());
		assertEquals(game.getPlayerX(), game.getWinner());
		assertTrue(game.isOver());
	}

	@Test
	void canDraw() {
		//___
		//___
		//___
		Game game = new Game(new User(), new User());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//___
		//_x_
		//___
		game.put(Row.R2, Column.C2, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//o__
		//_x_
		//___
		game.put(Row.R1, Column.C1, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//o__
		//xx_
		//___
		game.put(Row.R2, Column.C1, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//o__
		//xxo
		//___
		game.put(Row.R2, Column.C3, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//o__
		//xxo
		//x__
		game.put(Row.R3, Column.C1, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//o_o
		//xxo
		//x__
		game.put(Row.R1, Column.C3, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//oxo
		//xxo
		//x__
		game.put(Row.R1, Column.C2, game.getPlayerX());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//oxo
		//xxo
		//xo_
		game.put(Row.R3, Column.C2, game.getPlayerO());
		assertNull(game.getWinner());
		assertFalse(game.isOver());

		//oxo
		//xxo
		//xox
		game.put(Row.R3, Column.C3, game.getPlayerX());
		assertNull(game.getWinner());
		assertTrue(game.isOver());
	}

	@Test
	void aiMove() {
		Game game = new Game(new User(), new User());
		try {
			game.parse();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
