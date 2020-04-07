package back.interfaces;

import back.game.CellTypeEnum;

import java.util.List;
import java.util.Stack;

public interface Game {
		
	public boolean gameFinished();
	
	public void setHeuristicValue(int heuristicValue);

	public List<Game> calculateChildren();

	public boolean isVisited();

	public void setVisited(boolean visited);

	public int getDepth();

	public int getHeuristicValue();

	public int[][] getBoxesPositions();

	public int[][] getGoalsPositions();

	public int[] getPlayerPosition();

	public Game getParent();

	public List<Game> getPathFromRoot();

	public float getCostValue();

	public CellTypeEnum[][] getMap();
}
