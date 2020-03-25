package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import game.exception.InvalidMapException;

public class Game {
	private CellTypeEnum map[][];
	private int playerPosition[];
	private int[][] boxesPositions;
	private Stack<Action> actionStack;

	public Game(CellTypeEnum map[][]) throws InvalidMapException {
		this.map = map;
		actionStack = new Stack<>();
		gatherMapData();
	}

	public List<Action> getAvailableActions() {
		List<Action> actions = new ArrayList<>();
		boolean[][] visitedPositions = searchConnectedRegionDFS();

		for (int[] boxPosition : this.boxesPositions) {
			int i = boxPosition[0], j = boxPosition[1];
			if (visitedPositions[i][j] == true) {
				// VERTICAL MOVEMENT
				if (i + 1 < map.length) {
					if (i - 1 >= 0) {
						if (CellTypeEnum.movableCell(map[i + 1][j]) && visitedPositions[i - 1][j] == true
								&& CellTypeEnum.movableCell(map[i - 1][j])) {
							actions.add(new Action(new int[] { i, j }, new int[] { i + 1, j }, new int[] { i - 1, j }));
						}

						if (CellTypeEnum.movableCell(map[i - 1][j]) && visitedPositions[i + 1][j] == true
								&& CellTypeEnum.movableCell(map[i + 1][j])) {
							actions.add(new Action(new int[] { i, j }, new int[] { i - 1, j }, new int[] { i + 1, j }));
						}
					}
				}

				// HORIZONTAL MOVEMENT
				if (j + 1 < map[0].length) {
					if (j - 1 >= 0) {
						if (CellTypeEnum.movableCell(map[i][j + 1]) && visitedPositions[i][j - 1] == true
								&& CellTypeEnum.movableCell(map[i][j - 1])) {
							actions.add(new Action(new int[] { i, j }, new int[] { i, j + 1 }, new int[] { i, j - 1 }));
						}

						if (CellTypeEnum.movableCell(map[i][j - 1]) && visitedPositions[i][j + 1] == true
								&& CellTypeEnum.movableCell(map[i][j + 1])) {
							actions.add(new Action(new int[] { i, j }, new int[] { i, j - 1 }, new int[] { i, j + 1 }));
						}
					}
				}
			}
		}
		return actions;
	}

	public void applyMultipleActions(List<Action> actions) {
		for (Action action : actions) {
			applyAction(action);
		}
	}

	public void applyAction(Action action) {
		actionStack.push(action);

		map[this.playerPosition[0]][this.playerPosition[1]] = CellTypeEnum.EMPTY;
		map[action.getBoxTargetPosition()[0]][action.getBoxTargetPosition()[1]] = CellTypeEnum.BOX;
		map[action.getBoxCurrentPosition()[0]][action.getBoxCurrentPosition()[1]] = CellTypeEnum.PLAYER;
		
		this.playerPosition = action.getBoxCurrentPosition();
	}

	public void revertAction() {
		Action lastAction = actionStack.pop();

		map[lastAction.getBoxTargetPosition()[0]][lastAction.getBoxTargetPosition()[1]] = CellTypeEnum.EMPTY;
		map[lastAction.getBoxCurrentPosition()[0]][lastAction.getBoxCurrentPosition()[1]] = CellTypeEnum.BOX;
		map[lastAction.getPlayerPosition()[0]][lastAction.getPlayerPosition()[1]] = CellTypeEnum.PLAYER;
		
		this.playerPosition = lastAction.getPlayerPosition();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				switch (this.map[i][j]) {
				case WALL:
					sb.append('X');
					break;
				case EMPTY:
					sb.append(' ');
					break;
				case PLAYER:
					sb.append('@');
					break;
				case GOAL:
					sb.append('.');
					break;
				case BOX:
					sb.append('*');
					break;
				default:
				}
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	private void gatherMapData() throws InvalidMapException {
		List<int[]> boxes = new ArrayList<>();
		int goalsFound = 0;
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				CellTypeEnum value = this.map[i][j];
				switch (value) {
				case PLAYER:
					this.playerPosition = new int[] { i, j };
					break;
				case BOX:
					boxes.add(new int[] { i, j });
					break;
				case GOAL:
					goalsFound++;
					break;
				default:
				}

			}
		}

		if (boxes.isEmpty() || this.playerPosition == null || goalsFound != boxes.size())
			throw new InvalidMapException();

		this.boxesPositions = boxes.toArray(new int[0][]);
	}

	private boolean[][] searchConnectedRegionDFS() {
		boolean[][] visitedMap = new boolean[map.length][map[0].length];

		List<int[]> border = new LinkedList<int[]>();
		border.add(this.playerPosition);
		while (!border.isEmpty()) {
			List<int[]> newBorder = new LinkedList<int[]>();
			for (int[] position : border) {
				int i = position[0], j = position[1];

				CellTypeEnum value = this.map[i][j];
				if (CellTypeEnum.movableCell(value)) {
					if(i-1 >= 0 && visitedMap[i-1][j] != true) {
						newBorder.add(new int[] { i-1, j });
					}
					
					if(i+1 < map.length && visitedMap[i+1][j] != true) {
						newBorder.add(new int[] { i+1, j });
					}
					
					if(j-1 >= 0 && visitedMap[i][j-1] != true) {
						newBorder.add(new int[] { i, j-1 });
					}
					
					if(j+1 < map[0].length && visitedMap[i][j+1] != true) {
						newBorder.add(new int[] { i, j+1 });
					}
					
				}

				visitedMap[i][j] = true;
			}
			border = newBorder;
		}

		return visitedMap;
	}
}