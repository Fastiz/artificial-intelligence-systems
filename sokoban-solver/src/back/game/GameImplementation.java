package back.game;

import java.util.*;

import back.interfaces.CostFunction;
import back.interfaces.Game;

public class GameImplementation implements Game {

    private CellTypeEnum[][] map;
    private int[][] boxesPositions;
    private int[][] goalsPositions;
    private int[] playerPosition;

    private Game parent;
    private int depth;

    private CostFunction costFunction;

    private int heuristicValue;
    private int costValue;

    private boolean visited;

    private boolean deadlock;


    public GameImplementation(CellTypeEnum[][] map, int[] playerPosition, int[][] boxesPositions, int[][] goalsPositions, Game parent, int depth, int costValue, CostFunction costFunction) {
        this.map = map;
        this.playerPosition = playerPosition;
        this.boxesPositions = boxesPositions;
        this.goalsPositions = goalsPositions;
        this.parent = parent;
        this.depth = depth;
        this.costFunction = costFunction;
        this.costValue = costValue + costFunction.evaluate(parent, this);
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // ** public
    public List<Game> calculateChildren() {
        int i = playerPosition[0], j = playerPosition[1];
        List<Game> children = new LinkedList<>();

        if (this.deadlock)
            return children;

        Game newChild;

        //consider top
        newChild = createChild('t');
        if (newChild != null) {
            children.add(newChild);
        }

        //consider bottom
        newChild = createChild('b');
        if (newChild != null) {
            children.add(newChild);
        }

        //consider left
        newChild = createChild('l');
        if (newChild != null) {
            children.add(newChild);
        }

        //consider right
        newChild = createChild('r');
        if (newChild != null) {
            children.add(newChild);
        }

        return children;
    }

    @Override
    public int getCostValue() {
        return this.costValue;
    }

	@Override
	public CellTypeEnum[][] getMap() {
		return this.map;
	}

    @Override
    public int[] getPlayerPosition() {
        return this.playerPosition;
    }

    @Override
    public int[][] getBoxesPositions() {
        return this.boxesPositions;
    }

    @Override
    public int[][] getGoalsPositions() {
        return this.goalsPositions;
    }

    @Override
    public Game getParent() {
        return this.parent;
    }

    @Override
    public boolean gameFinished() {
        for (int[] boxPosition : this.boxesPositions) {
            if (map[boxPosition[0]][boxPosition[1]] != CellTypeEnum.GOAL)
                return false;
        }
        return true;
    }

    @Override
    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    @Override
    public int getHeuristicValue() {
        return heuristicValue;
    }

    @Override
    public List<Game> getPathFromRoot() {
        List<Game> path = new LinkedList<>();
        getPathFromRootR(this, path);
        return path;
    }

    private void getPathFromRootR(Game current, List<Game> list) {
        if (current.getParent() != null) {
            getPathFromRootR(current.getParent(), list);
        }
        list.add(current);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                CellTypeEnum cell = this.map[i][j];

                if (cell == CellTypeEnum.WALL) {
                    sb.append("#");
                } else if (cell == CellTypeEnum.EMPTY) {
                    if (playerPosition[0] == i && playerPosition[1] == j) {
                        sb.append("@");
                    } else if (checkForBox(new int[]{i, j}) != -1) {
                        sb.append("$");
                    } else {
                        sb.append(" ");
                    }
                } else if (cell == CellTypeEnum.GOAL) {
                    if (playerPosition[0] == i && playerPosition[1] == j) {
                        sb.append("@");
                    } else if (checkForBox(new int[]{i, j}) != -1) {
                        sb.append("G");
                    } else {
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

    public void checkDeadlock() {
        this.deadlock = deadlockState();
    }

    // ** private
    private boolean deadlockState() {

		for(int x = -1; x < 2; x ++) {
			for(int i = 0; i < this.boxesPositions.length && x != 0; i++) {
				int xBox = this.boxesPositions[i][0], yBox = this.boxesPositions[i][1];
				if(xBox + x >= 0 && xBox + x < map.length) {
					if(map[xBox][yBox] != CellTypeEnum.GOAL){
						if(map[xBox + x][yBox].equals(CellTypeEnum.WALL) && (map[xBox][yBox + 1].equals(CellTypeEnum.WALL) || map[xBox][yBox - 1].equals(CellTypeEnum.WALL))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private Game createChild(char direction) {
		int[] pos = this.playerPosition;
		
		int posComponent = direction == 't' || direction == 'b' ? 0 : 1;
		boolean subtract = direction == 't' || direction == 'l';
		
		if(subtract) {
			if(pos[posComponent] -1 < 0)
				return null;
		}else {
			if(pos[posComponent] +1 >= (posComponent == 0 ? map.length : map[0].length))
				return null;
		}
				
		int iDir = (posComponent == 0 ? (subtract ? -1 : 1) : 0), jDir = (posComponent == 1 ? (subtract ? -1 : 1) : 0);
		if(map[pos[0] + iDir][pos[1] + jDir] != CellTypeEnum.WALL) {
			int index = checkForBox(new int[] {pos[0] + iDir, pos[1] + jDir});
			if(index == -1) {
				return new GameImplementation(this.map, new int[] {pos[0]+iDir, pos[1]+jDir}, this.boxesPositions,
						this.goalsPositions, this, this.depth+1, this.costValue, this.costFunction);
			}else {
				if(subtract) {
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

                        GameImplementation newGame = new GameImplementation(this.map, new int[]{pos[0] + iDir, pos[1] + jDir},
                                newBoxes, this.goalsPositions, this, this.depth + 1, this.costValue, this.costFunction);

                        newGame.checkDeadlock();
                        return newGame;
                    }
                }
            }
        }

        return null;

    }

    private int[][] shallowCopyBoxes() {
        int[][] copiedBoxes = new int[this.boxesPositions.length][];
        System.arraycopy(boxesPositions, 0, copiedBoxes, 0, this.boxesPositions.length);
        return copiedBoxes;
    }

    private int[][] moveBoxAndCopyBoxes(int index, int[] newPos) {
        int[][] copiedBoxes = shallowCopyBoxes();

        copiedBoxes[index] = newPos;

        return copiedBoxes;
    }

    private int checkForBox(int[] pos) {
        for (int i = 0; i < this.boxesPositions.length; i++) {
            if (this.boxesPositions[i][0] == pos[0] && this.boxesPositions[i][1] == pos[1])
                return i;
        }
        return -1;
    }


}





