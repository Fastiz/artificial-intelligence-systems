package back.game;

import java.util.*;

import back.game.exceptions.InvalidMapException;
import back.interfaces.Heuristic;

public class Game {
	private CellTypeEnum map[][];
	private int playerPosition[];
	private int[][] boxesPositions;
	private LinkedList<Action> actionList;
	private int acumulatedCost = 0;
	private int estimatedCost;

	public Game(CellTypeEnum map[][]) throws InvalidMapException {
		this.map = map;
		actionList = new LinkedList<>();
		gatherMapData();
	}

	public Game(CellTypeEnum map[][], int[] playerPosition, int[][] boxesPositions, LinkedList<Action> actionList) {
		this.map = map;
		this.playerPosition = playerPosition;
		this.boxesPositions = boxesPositions;
		this.actionList = actionList;
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
		for(int[] boxPosition : boxesPositions) {
			if(map[boxPosition[0]][boxPosition[1]] != CellTypeEnum.GOALANDBOX)
				return false;
		}
		return true;
	}

	public void applyMultipleActions(List<Action> actions) {
		for (Action action : actions) {
			applyAction(action);
		}
	}

	public List<Action> getActionList() {
		return actionList;
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
		actionList.add(action);

		//TODO: funcion de costos
		this.acumulatedCost += action.getActionCost();

		map[this.playerPosition[0]][this.playerPosition[1]] = CellTypeEnum.moveFrom(map[this.playerPosition[0]][this.playerPosition[1]]);
		map[action.getBoxTargetPosition()[0]][action.getBoxTargetPosition()[1]] = CellTypeEnum.moveBoxTo(map[action.getBoxTargetPosition()[0]][action.getBoxTargetPosition()[1]]);
		map[action.getBoxCurrentPosition()[0]][action.getBoxCurrentPosition()[1]] = CellTypeEnum.movePlayerTo(map[action.getBoxCurrentPosition()[0]][action.getBoxCurrentPosition()[1]]);
		
		updateBoxPosition(action.getBoxCurrentPosition(), action.getBoxTargetPosition());
		this.playerPosition = action.getBoxCurrentPosition();
		
		int countPlayers = countPlayers();
		
		return;
	}

	public boolean revertAction() {
		if(actionList.isEmpty())
			return false;

		Action lastAction = actionList.getLast();
		actionList.removeLast();

		//TODO:
		this.acumulatedCost -= lastAction.getActionCost();

		map[this.playerPosition[0]][this.playerPosition[1]] = CellTypeEnum.moveFrom(map[this.playerPosition[0]][this.playerPosition[1]]);
		map[lastAction.getBoxTargetPosition()[0]][lastAction.getBoxTargetPosition()[1]] = CellTypeEnum.moveFrom(map[lastAction.getBoxTargetPosition()[0]][lastAction.getBoxTargetPosition()[1]]);
		map[lastAction.getBoxCurrentPosition()[0]][lastAction.getBoxCurrentPosition()[1]] = CellTypeEnum.moveBoxTo(map[lastAction.getBoxCurrentPosition()[0]][lastAction.getBoxCurrentPosition()[1]]);
		map[lastAction.getPlayerPosition()[0]][lastAction.getPlayerPosition()[1]] = CellTypeEnum.movePlayerTo(map[lastAction.getPlayerPosition()[0]][lastAction.getPlayerPosition()[1]]);
		
		updateBoxPosition(lastAction.getBoxTargetPosition(), lastAction.getBoxCurrentPosition());
		this.playerPosition = lastAction.getPlayerPosition();

		//TODO: borrar
		int countPlayers = countPlayers();
		
		return true;
	}
	
	private int countPlayers() {
		int count = 0;
		for(int i=0; i<this.map.length; i++) {
			for(int j=0; j<this.map[0].length; j++) {
				if(map[i][j] == CellTypeEnum.PLAYER || map[i][j] == CellTypeEnum.PLAYERANDGOAL) {
					count++;
				}
			}
		}
		return count;
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
					case PLAYERANDGOAL:
						sb.append("(@)");
						break;
					case GOALANDBOX:
						sb.append("(*)");
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
		LinkedList<Action> actionList = (LinkedList<Action>) this.actionList.clone();

		return new Game(map, playerPosition, boxesPositions, actionList);
	}
	
	private void updateBoxPosition(int[] oldPosition, int[] newPosition) {
		for(int[] boxPosition : this.boxesPositions) {
			if(boxPosition[0] == oldPosition[0] && boxPosition[1] == oldPosition[1]) {
				boxPosition[0] = newPosition[0];
				boxPosition[1] = newPosition[1];
			}
		}
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
	
	public List<Game> getPathToCurrentState(){
		List<Game> path = new LinkedList<>();
		Game state = cloneGame();
		while(state.revertAction());
		
		path.add(state);
		
		for(Action action: this.actionList) {
			Game newState = state.applyActionAndClone(action);
			path.add(newState);
			state = newState;
		}
		
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(boxesPositions);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (!Arrays.deepEquals(boxesPositions, other.boxesPositions))
			return false;
		return true;
	}
	
	
}