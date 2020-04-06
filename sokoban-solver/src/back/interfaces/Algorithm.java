package back.interfaces;

import back.AlgorithmSolution;
import back.interfaces.Game;

public interface Algorithm {
	public AlgorithmSolution run(Game game);

	public String getName();
}
