package com.javarush.task.task35.task3513;

import java.util.*;
import java.util.stream.IntStream;


public class Model {
	private static final int FIELD_WIDTH = 4;
	private Tile[][] gameTiles;
	private Stack<Tile[][]> previousStates = new Stack<>();
	private Stack<Integer> previousScores = new Stack<Integer>();
	private boolean isSaveNeeded = true;
	protected int score = 0;
	protected int maxTile = 2;

	public Model() {
		resetGameTiles();
	}

	public int getEmptyTilesCount() {
		return getEmptyTiles().size();
	}

	boolean hasBoardChanged() {
		boolean result = false;
		int sumNow = 0;
		int sumPrevious = 0;
		Tile[][] tmp = previousStates.peek();
		for (int i = 0; i < gameTiles.length; i++) {
			for (int j = 0; j < gameTiles[0].length; j++) {
				sumNow += gameTiles[i][j].getValue();
				sumPrevious += tmp[i][j].getValue();
			}
		}
		return sumNow != sumPrevious;
	}

	MoveEfficiency getMoveEfficiency(Move move) {
		MoveEfficiency moveEfficiency;
		move.move();
		if (hasBoardChanged()) moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
		else moveEfficiency = new MoveEfficiency(-1, 0, move);
		rollback();

		return moveEfficiency;
	}

	public Tile[][] getGameTiles() {
		return gameTiles;
	}

	public int getScore() {
		return score;
	}

	public boolean canMove() {
		if(!getEmptyTiles().isEmpty()) return true;

		for (int i = 0; i < gameTiles.length; i++) {
			for (int j = 1; j < gameTiles.length; j++) {
				if (gameTiles[i][j].value == gameTiles[i][j-1].value)
					return true;
			}
		}
		for (int i = 0; i < gameTiles.length; i++) {
			for (int j = 1; j < gameTiles.length; j++) {
				if (gameTiles[j][i].value == gameTiles[j-1][i]. value)return true;
			}
		}
		return false;
	}

	public void resetGameTiles() {
		gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
		for (int i = 0; i < gameTiles.length; i++) {
			for (int j = 0; j < gameTiles[0].length; j++) {
				gameTiles[i][j] = new Tile();
			}
		}
		addTile();
		addTile();
	}

	private void addTile() {
		List<Tile> emptyTiles = getEmptyTiles();
		if (emptyTiles.size() > 0) {
			emptyTiles.get((int) (Math.random() * emptyTiles.size())).value = (Math.random() < 0.9) ? 2 : 4;
		}

	}

	private List<Tile> getEmptyTiles() {
		List<Tile> tiles = new ArrayList<>();
		IntStream.range(0, FIELD_WIDTH).forEach((i) -> {
			Arrays.stream(gameTiles[i])
					.filter(Tile::isEmpty)
					.forEach(tiles::add);
		});

		return tiles;
	}

	private boolean compressTiles(Tile[] tiles) {
		boolean x = false;
		for (int j = tiles.length - 1; j > 0; j--) {
			for (int i = 0; i < j; i++) {
				if (tiles[i].isEmpty() && !tiles[i + 1].isEmpty()) {
					tiles[i].value = tiles[i + 1].value;
					tiles[i + 1].value = 0;
					x = true;
				}
			}
		}
		return x;
	}

	private boolean mergeTiles(Tile[] tiles) {
		boolean x = false;
		for (int i = 1; i < tiles.length; i++) {
			if (tiles[i - 1].value == tiles[i].value && !tiles[i - 1].isEmpty() && !tiles[i].isEmpty()) {
				tiles[i - 1].value *= 2;
				tiles[i].value = 0;
				if (tiles[i - 1].value > maxTile) {
					maxTile = tiles[i - 1].value;
				}
				score += tiles[i - 1].value;
				x = true;

			}
		}
		return x;
	}

	public void rollback() {
		if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
			gameTiles = previousStates.pop();
			score = previousScores.pop();
		}
	}

	private void rotate() {
		int len = FIELD_WIDTH;
		for (int k = 0; k < len / 2; k++) // border -> center
		{
			for (int j = k; j < len - 1 - k; j++) // left -> right
			{

				Tile tmp = gameTiles[k][j];
				gameTiles[k][j] = gameTiles[j][len - 1 - k];
				gameTiles[j][len - 1 - k] = gameTiles[len - 1 - k][len - 1 - j];
				gameTiles[len - 1 - k][len - 1 - j] = gameTiles[len - 1 - j][k];
				gameTiles[len - 1 - j][k] = tmp;
			}
		}
	}

	private void saveState(Tile[][] tiles) {
		Tile[][] tempState = new Tile[FIELD_WIDTH][FIELD_WIDTH];
		for (int i = 0; i < FIELD_WIDTH; i++) {
			for (int j = 0; j < FIELD_WIDTH; j++) {
				tempState[i][j] = new Tile(tiles[i][j].value);
			}
		}

		previousStates.push(tempState);
		previousScores.push(score);
		isSaveNeeded = false;
	}


	public void left() {

		if (isSaveNeeded) saveState(this.gameTiles);

		boolean isChanged = false;
		for (int i = 0; i < FIELD_WIDTH; i++) {
			if (mergeTiles(gameTiles[i]) | compressTiles(gameTiles[i])) {
				isChanged = true;
			}
		}

		if (isChanged) {
			addTile();
			isSaveNeeded = true;
		}
	}

	public void right() {
		saveState(this.gameTiles);
		rotate();
		rotate();
		left();
		rotate();
		rotate();
	}

	public void down() {
		saveState(this.gameTiles);
		rotate();
		rotate();
		rotate();
		left();
		rotate();
	}

	public void up() {
		saveState(this.gameTiles);
		rotate();
		left();
		rotate();
		rotate();
		rotate();
	}

	void randomMove() {
		int n = ((int) (Math.random() * 100)) % 4;

		switch (n) {
			case 0	:	left();
			case 1	:	right();
			case 2	:	down();
			case 3	:	up();
		}
	}

	void autoMove() {
		PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());

		priorityQueue.add(getMoveEfficiency(this::left));
		priorityQueue.add(getMoveEfficiency(this::right));
		priorityQueue.add(getMoveEfficiency(this::up));
		priorityQueue.add(getMoveEfficiency(this::down));

		priorityQueue.peek().getMove().move();
	}
}