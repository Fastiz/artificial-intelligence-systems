package back.game;

import java.util.*;

import back.Interfaces.Heuristic;
import back.game.exception.InvalidMapException;

public class Game {
	private CellTypeEnum map[][];
	private int playerPosition[];
	private int[][] boxesPositions;
	private Stack<Action> actionStack;
	private int acumulatedCost = 0;
	private int estimatedCost;

	public Game(CellTypeEnum map[][]) throws InvalidMapException {
		this.map = map;
		actionStack = new Stack<>();
		gatherMapData();
	}

	public Game(CellTypeEnum map[][], int[] playerPosition, int[][] boxesPositions, Stack<Action> actionStack) {
		this.map = map;
		this.playerPosition = playerPosition;
		this.boxesPositions = boxesPositions;
		this.actionStack = actionStack;
	}

	public List<Action> getAvailableActions() {
		List<Action> actions = new ArrayList<>();
		boolean[][] visitedPositions = searchConnectedRegionDFS();

		for (int[] boxPosition : this.boxesPositions) {
			int i = boxPosition[0], j = boxPosition[1];
			if (visitedPositions[i][j]) {
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

	//TODO
	public boolean gameFinished(){
		return false;
	}

	public void applyMultipleActions(List<Action> actions) {
		for (Action action : actions) {
			applyAction(action);
		}
	}

	public Stack<Action> getActionStack() {
		return actionStack;
	}

	public Game applyActionAndClone(Action action, Heuristic heuristic) {
		Game cloned = cloneGame();
		cloned.applyAction(action, heuristic);
		return cloned;
	}

	public Game applyActionAndClone(Action action) {
		Game cloned = cloneGame();
		cloned.applyAction(action);
		return cloned;
	}

	public void applyAction(Action action, Heuristic heuristic) {
		applyAction(action);
		this.estimatedCost = heuristic.evaluate(this);
	}

	public void applyAction(Action action) {
		actionStack.push(action);

		//TODO: funcion de costos
		this.acumulatedCost += action.getActionCost();

		map[this.playerPosition[0]][this.playerPosition[1]] = CellTypeEnum.EMPTY;
		map[action.getBoxTargetPosition()[0]][action.getBoxTargetPosition()[1]] = CellTypeEnum.BOX;
		map[action.getBoxCurrentPosition()[0]][action.getBoxCurrentPosition()[1]] = CellTypeEnum.PLAYER;
		
		this.playerPosition = action.getBoxCurrentPosition();
	}

	public boolean revertAction() {
		if(actionStack.isEmpty())
			return false;

		Action lastAction = actionStack.pop();

		//TODO:
		this.acumulatedCost -= lastAction.getActionCost();

		map[lastAction.getBoxTargetPosition()[0]][lastAction.getBoxTargetPosition()[1]] = CellTypeEnum.EMPTY;
		map[lastAction.getBoxCurrentPosition()[0]][lastAction.getBoxCurrentPosition()[1]] = CellTypeEnum.BOX;
		map[lastAction.getPlayerPosition()[0]][lastAction.getPlayerPosition()[1]] = CellTypeEnum.PLAYER;
		
		this.playerPosition = lastAction.getPlayerPosition();

		return true;
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

	private Game cloneGame() {
		CellTypeEnum[][] map = Arrays.stream(this.map).map(CellTypeEnum[]::clone).toArray(CellTypeEnum[][]::new);
		int[] playerPosition = new int[this.playerPosition.length];
		System.arraycopy( this.playerPosition, 0, playerPosition, 0, this.playerPosition.length);
		int[][] boxesPositions = Arrays.stream(this.boxesPositions).map(int[]::clone).toArray(int[][]::new);
		Stack<Action> actionStack = (Stack<Action>) this.actionStack.clone();

		return new Game(map, playerPosition, boxesPositions, actionStack);
	}

	public int getAcumulatedCost() {
		return acumulatedCost;
	}

	public int getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(int estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
}