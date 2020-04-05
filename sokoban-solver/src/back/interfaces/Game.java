package back.interfaces;

import java.util.List;
import java.util.Stack;

public interface Game {
		
	public boolean gameFinished();
	
	public void setEstimatedCost(int estimatedCost);

	public List<Game> calculateChildren();

	public List<Game> calculateChildrenWithStack();

	public int getAccumulatedCost();

	public int getEstimatedCost();

	public Stack<Game> getGameStack();

	public void setGameStack(Stack<Game> gameStack);

	public int[][] getBoxesPositions();

	public int[][] getGoalsPositions();
}
