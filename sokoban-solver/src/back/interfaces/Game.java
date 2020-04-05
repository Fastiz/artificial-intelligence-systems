package back.interfaces;

import back.game.CellTypeEnum;

import java.util.List;
import java.util.Stack;

public interface Game {
		
	public boolean gameFinished();
	
	public void setEstimatedCost(int estimatedCost);

	public List<Game> calculateChildren();

	public int getDepth();

	public int getEstimatedCost();

	public int[][] getBoxesPositions();

	public int[][] getGoalsPositions();

	public int[] getPlayerPosition();

	public Game getParent();

	public List<Game> getPathToRoot();
}
