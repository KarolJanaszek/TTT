package pl.karol.ttt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import pl.karol.ttt.model.Board.Column;
import pl.karol.ttt.model.Board.Player;
import pl.karol.ttt.model.Board.Row;

class BoardTest {
	@Test
	void size() {
		Board board = new Board();
		assertEquals(0, board.size());
		board.put(Row.R2, Column.C2, Player.X);
		assertEquals(1, board.size());
		board.put(Row.R1, Column.C2, Player.O);
		assertEquals(2, board.size());
		board.put(Row.R1, Column.C1, Player.X);
		assertEquals(3, board.size());
		board.put(Row.R1, Column.C3, Player.O);
		assertEquals(4, board.size());
		board.put(Row.R3, Column.C3, Player.X);
		assertEquals(5, board.size());
	}
	
	@Test
	void winner() {
		Board board = new Board();
		assertNull(board.winner());
		board.put(Row.R2, Column.C2, Player.X);
		assertNull(board.winner());
		board.put(Row.R1, Column.C2, Player.O);
		assertNull(board.winner());
		board.put(Row.R1, Column.C1, Player.X);
		assertNull(board.winner());
		board.put(Row.R1, Column.C3, Player.O);
		assertNull(board.winner());
		board.put(Row.R3, Column.C3, Player.X);
		assertEquals(Player.X, board.winner());
	}
}
