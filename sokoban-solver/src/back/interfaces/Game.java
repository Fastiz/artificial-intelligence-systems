package back.interfaces;

import java.util.List;

public interface Game {
		
	public boolean gameFinished();
	
	public void setEstimatedCost(int estimatedCost);
	
	public List<Game> calculateChilds();
}
