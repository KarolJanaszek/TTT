package pl.karol.ttt.model;

import java.util.Arrays;
import java.util.Objects;

public final class Board {
	public enum Row { R1, R2, R3 }
	public enum Column { C1, C2, C3 }
	public enum Player { X, O }

	private final Player[][] cells = new Player[3][3];

	public Player get(Row row, Column column) {
		return cells[row.ordinal()][column.ordinal()];
	}

	public void put(Row row, Column column, Player player) {
		cells[row.ordinal()][column.ordinal()] = player;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean isFull() {
		return size() == 9;
	}

	public boolean hasWinner() {
		return winner() != null;
	}

	public int size() {
		return (int) Arrays.stream(cells).flatMap(Arrays::stream).filter(Objects::nonNull).count();
	}

	public int count(Player player) {
		return (int) Arrays.stream(cells).flatMap(Arrays::stream).filter(player::equals).count();
	}

	public Player winner() {
		int[] threes = new int[2];

		if (get(Row.R1, Column.C1) == get(Row.R1, Column.C2) && get(Row.R1, Column.C2) == get(Row.R1, Column.C3) && get(Row.R1, Column.C1) != null) {
			threes[get(Row.R1, Column.C1).ordinal()]++;
		}
		if (get(Row.R2, Column.C1) == get(Row.R2, Column.C2) && get(Row.R2, Column.C2) == get(Row.R2, Column.C3) && get(Row.R2, Column.C1) != null) {
			threes[get(Row.R2, Column.C1).ordinal()]++;
		}
		if (get(Row.R3, Column.C1) == get(Row.R3, Column.C2) && get(Row.R3, Column.C2) == get(Row.R3, Column.C3) && get(Row.R3, Column.C1) != null) {
			threes[get(Row.R3, Column.C1).ordinal()]++;
		}

		if (get(Row.R1, Column.C1) == get(Row.R2, Column.C1) && get(Row.R2, Column.C1) == get(Row.R3, Column.C1) && get(Row.R1, Column.C1) != null) {
			threes[get(Row.R1, Column.C1).ordinal()]++;
		}
		if (get(Row.R1, Column.C2) == get(Row.R2, Column.C2) && get(Row.R2, Column.C2) == get(Row.R3, Column.C2) && get(Row.R1, Column.C2) != null) {
			threes[get(Row.R1, Column.C2).ordinal()]++;
		}
		if (get(Row.R1, Column.C3) == get(Row.R2, Column.C3) && get(Row.R2, Column.C3) == get(Row.R3, Column.C3) && get(Row.R1, Column.C3) != null) {
			threes[get(Row.R1, Column.C3).ordinal()]++;
		}

		if (get(Row.R1, Column.C1) == get(Row.R2, Column.C2) && get(Row.R2, Column.C2) == get(Row.R3, Column.C3) && get(Row.R1, Column.C1) != null) {
			threes[get(Row.R1, Column.C1).ordinal()]++;
		}
		if (get(Row.R1, Column.C3) == get(Row.R2, Column.C2) && get(Row.R2, Column.C2) == get(Row.R3, Column.C1) && get(Row.R1, Column.C3) != null) {
			threes[get(Row.R1, Column.C3).ordinal()]++;
		}

		return threes[Player.X.ordinal()] > threes[Player.O.ordinal()] ? Player.X : threes[Player.X.ordinal()] < threes[Player.O.ordinal()] ? Player.O : null;
	}
}
