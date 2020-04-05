package back.game;

import java.util.*;

import back.game.exceptions.InvalidMapException;
import back.interfaces.Game;

public class GameImplementation implements Game {
	
	private CellTypeEnum[][] map;
	private int[][] boxesPositions;
	private int[] playerPosition;
	private Stack<Game> gameStack;

	//TODO:
	private int accumulatedCost;
	private int estimatedCost;

	
	public GameImplementation(CellTypeEnum[][] map, int[] playerPosition, int[][] boxesPositions) {
		this.map = map;
		this.playerPosition = playerPosition;
		this.boxesPositions = boxesPositions;
		this.gameStack = new Stack<>();
	}
	
	// ** public
	public List<Game> calculateChildren(){
		int i=playerPosition[0], j=playerPosition[1];
		List<Game> children = new LinkedList<>();

		Game newChild;

		//consider top
		newChild = createChild('t');
		if(newChild != null) {
			children.add(newChild);
		}

		//consider bottom
		newChild = createChild('b');
		if(newChild != null) {
			children.add(newChild);
		}

		//consider left
		newChild = createChild('l');
		if(newChild != null) {
			children.add(newChild);
		}

		//consider right
		newChild = createChild('r');
		if(newChild != null) {
			children.add(newChild);
		}

		return children;
	}

	public List<Game> calculateChildrenWithStack(){
		List<Game> children = calculateChildren();
		addStackToChildren(children);
		return children;
	}

	private void addStackToChildren(List<Game> children) {
		if(children.isEmpty())
			return;

		if(this.gameStack == null)
			this.gameStack = new Stack<>();

		this.gameStack.push(this);

		for (Game child : children) {
			child.setEstimatedCost(this.estimatedCost + 1);
			child.getGameStack().addAll(this.gameStack);
		}

	}

	public Stack<Game> getGameStack() {
		return gameStack;
	}

	public void setGameStack(Stack<Game> gameStack) {
		this.gameStack = gameStack;
	}
	
	public boolean gameFinished() {
		for(int[] boxPosition: this.boxesPositions) {
			if(map[boxPosition[0]][boxPosition[1]] != CellTypeEnum.GOAL)
				return false;
		}
		return true;
	}
	
	public void setEstimatedCost(int estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	
	public int getAccumulatedCost() {
		return accumulatedCost;
	}

	public int getEstimatedCost() {
		return estimatedCost;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				CellTypeEnum cell = this.map[i][j];
				
				if(cell == CellTypeEnum.WALL) {
					sb.append("X");
				}else if(cell == CellTypeEnum.EMPTY) {
					if(playerPosition[0] == i && playerPosition[1] == j) {
						sb.append("@");
					}else if(checkForBox(new int[] {i, j}) != -1) {
						sb.append("*");
					}else {
						sb.append(" ");
					}
				}else if(cell == CellTypeEnum.GOAL) {
					if(playerPosition[0] == i && playerPosition[1] == j) {
						sb.append("@");
					}else if(checkForBox(new int[] {i, j}) != -1) {
						sb.append("G");
					}else {
						sb.append(".");
					}
				}
			}
			sb.append('\n');
		}

		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(boxesPositions);
		result = prime * result + Arrays.hashCode(playerPosition);
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
		GameImplementation other = (GameImplementation) obj;
		if (!Arrays.deepEquals(boxesPositions, other.boxesPositions))
			return false;
		if (!Arrays.equals(playerPosition, other.playerPosition))
			return false;
		return true;
	}
	
	// ** private
	
	private void gatherMapInformation() throws InvalidMapException{
		int boxCount=0, goalCount=0;
		boolean playerFound = false;
		
		List<int[]> boxesPositions = new ArrayList<>();
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				switch(this.map[i][j]) {
					case BOX:
						boxesPositions.add(new int[] {i, j});
						boxCount++;
					break;
					case PLAYER:
						if(playerFound)
							throw new InvalidMapException();
					
						this.playerPosition = new int[] {i, j};
						playerFound = true;
						break;
					case GOAL:
						goalCount++;
						break;
					default:
						
				}
			}
		}
		
		if(boxCount != goalCount || boxCount == 0 || !playerFound) {
			throw new InvalidMapException();
		}
		
		this.boxesPositions =  boxesPositions.toArray(new int[0][]);
	}

	
	private Game createChild(char direction) {
		int[] pos = this.playerPosition;
		
		int posComponent = direction == 't' || direction == 'b' ? 0 : 1;
		boolean substract = direction == 't' || direction == 'l' ? true : false;
		
		if(substract) {
			if(pos[posComponent] -1 < 0)
				return null;
		}else {
			if(pos[posComponent] +1 >= (posComponent == 0 ? map.length : map[0].length))
				return null;
		}
				
		int iDir = (posComponent == 0 ? (substract ? -1 : 1) : 0), jDir = (posComponent == 1 ? (substract ? -1 : 1) : 0);
		if(map[pos[0] + iDir][pos[1] + jDir] != CellTypeEnum.WALL) {
			int index = checkForBox(new int[] {pos[0] + iDir, pos[1] + jDir});
			if(index == -1) {
				return new GameImplementation(this.map, new int[] {pos[0]+iDir, pos[1]+jDir}, deepCopyBoxes());
			}else {
				if(substract) {
					if(pos[posComponent] -2 < 0)
						return null;
				}else {
					if(pos[posComponent] +2 >= (posComponent == 0 ? map.length : map[0].length))
						return null;
				}
				
				if(map[pos[0] + iDir*2][pos[1] + jDir*2] != CellTypeEnum.WALL) {
					int[] targetPosition = new int[] {pos[0] + iDir*2, pos[1] + jDir*2};
					if(checkForBox(targetPosition) == -1){
						int[][] newBoxes = moveBoxAndCopyBoxes(index, targetPosition);
						
						return new GameImplementation(this.map, new int[] {pos[0]+iDir, pos[1]+jDir}, newBoxes);
					}
				}
			}
		}
		
		return null;
		
	}
	
	private int[][] deepCopyBoxes(){
		int[][] copiedBoxes = new int[this.boxesPositions.length][];
		for(int i=0; i<this.boxesPositions.length; i++) {
			copiedBoxes[i] = (int[]) boxesPositions[i].clone();
		}
		return copiedBoxes;
	}
	
	private int[][] moveBoxAndCopyBoxes(int index, int[] newPos){
		int[][] copiedBoxes = deepCopyBoxes();
		
		copiedBoxes[index] = newPos;
		
		return copiedBoxes;
	}
	
	private int checkForBox(int[] pos) {
		for(int i=0; i<this.boxesPositions.length; i++) {
			if(this.boxesPositions[i][0] == pos[0] && this.boxesPositions[i][1] == pos[1])
				return i;
		}
		return -1;
	}


}





