package pl.karol.ttt.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.karol.ttt.model.Board.Column;
import pl.karol.ttt.model.Board.Player;
import pl.karol.ttt.model.Board.Row;

public final class Game {
	private final Board board = new Board();

	private final User playerX;
	private final User playerO;


	public Game(User playerX, User playerO) {
		this.playerX = playerX;
		this.playerO = playerO;
	}

	public User getPlayerX() {
		return playerX;
	}

	public User getPlayerO() {
		return playerO;
	}

	public int getLenght() {
		return board.size();
	}

	public void put(Row row, Column column, User user) {
		if (board.get(row, column) != null) {
			throw new IllegalStateException("Pole tej planszy jest już zapełnione.");
		}
		int xs = board.count(Player.X);
		int os = board.count(Player.O);
		Player authorized = xs == os ? Player.X : Player.O;
		if (user != user(authorized)) {
			throw new IllegalStateException("Nieautoryzowany gracz.");
		}
		board.put(row, column, authorized);
	}

	public List<String[]> parse() throws FileNotFoundException {
		String filePath = "C:/java_workspace/ttt/src/main/webapp/csv/xPc.csv";
		List<String[]> data = new ArrayList<>();

		try (Stream<String> streamData = Files.lines(Paths.get(filePath))){
			data = streamData.map(line -> {
						String[] str = line.split(":");
						return str;
						})
					.collect(Collectors.toList());

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return data;
	};

	public void bestMove() {
		List<String[]> moves = null;
		try {
			moves = parse();
		}  catch (IOException ioe) {
			ioe.printStackTrace();
		}
		long max = moves.stream().count();

		//iteracja po każdym case z csv
		for (int i = 0; i < max; i++) {
			String line[] = moves.get(i);
			String move = line[0];
			String answer = line[1];
			//iteracja po każdym znaku z case

			iterEveryMoveField(move, answer);
		}
	}

	private void iterEveryMoveField(String move, String answer) {
		StringBuilder moveToCompare = new StringBuilder();

		for (int j = 0; j <= 10; j++) {
			char field = move.charAt(j);
				if(field == 'X' || field =='O' || field == 'N') {
					if(field == 'N' && userOnFieldByIter(j) == null) {
						moveToCompare.append(field);
					} else {
						continue;
					}
					if(field == 'X' && userOnFieldByIter(j) != null && userOnFieldByIter(j).equals(playerX)) {
						moveToCompare.append(field);
					} else {
						continue;
					}
					if(field == 'O' && userOnFieldByIter(j) != null && !userOnFieldByIter(j).equals(playerO)) {
						moveToCompare.append(field);
					} else {
						continue;
					}
				} else {
					moveToCompare.append(field);
					continue;
				}
				//jeżeli case pasuje to board to iteruj po znakach z moveToSet
				if(moveToCompare.toString().equals(move)) {
					answerIter(answer);
					break;
			}
		}
	}

	private User userOnFieldByIter(int iter) {
		Row row = rowByIter(iter);
		Column column = columnByIter(iter);
		return get(row, column);
	}

	private void answerIter(String answer) {
		for (int k = 0; k <= 10; k++) {
			char fieldToSet = answer.charAt(k);
			if(fieldToSet == 'X') {
				put(rowByIter(k), columnByIter(k), playerX);
				break;
			}
		}
	}

	private Row rowByIter(int iter) {
		Row row = null;
		switch (iter) {
			case 0:row = Row.R1; break;
			case 1:row = Row.R1; break;
			case 2:row = Row.R1; break;

			case 4:row = Row.R2; break;
			case 5:row = Row.R2; break;
			case 6:row = Row.R2; break;

			case 8:row = Row.R3; break;
			case 9:row = Row.R3; break;
			case 10:row = Row.R3; break;

			default: break;
		}
		return row;
	}

	private Column columnByIter(int iter) {
		Column column = null;
		switch (iter) {
			case 0:column = Column.C1; break;
			case 1:column = Column.C2; break;
			case 2:column = Column.C3; break;

			case 4:column = Column.C1; break;
			case 5:column = Column.C2; break;
			case 6:column = Column.C3; break;

			case 8:column = Column.C1; break;
			case 9:column = Column.C2; break;
			case 10:column = Column.C3; break;

			default: break;
		}
		return column;
	}

	public User get(Row row, Column column) {
		Player occupant = board.get(row, column);
		return user(occupant);
	}

	public boolean isOver() {
		return board.isFull()
			|| board.hasWinner();
	}

	public User getWinner() {
		Player winner = board.winner();
		return user(winner);
	}

	private User user(Player player) {
		if (player == null) {
			return null;
		}
		switch (player) {
			case X: return playerX;
			case O: return playerO;
		}
		throw new Error();
	}
}
