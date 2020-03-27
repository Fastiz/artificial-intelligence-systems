import java.io.IOException;

import back.AlgorithmSolution;
import back.algorithms.DFS;
import back.interfaces.Game;
import back.game.GameImplementation;
import back.game.exceptions.InvalidMapException;
import back.interfaces.Algorithm;
import reader.MapReader;

public class Main {
	public static void main(String[] args){
		MapReader mapReader;
		try {
			mapReader = new MapReader("map2.txt");
		}catch(IOException e) {
			System.err.println(e);
			return;
		}
		

		
		Game game = new GameImplementation(mapReader.getMap(), mapReader.getPlayerPosition(), mapReader.getBoxesPositions());

		
		Algorithm dfs = new DFS();
		
		AlgorithmSolution solution = dfs.run(game);
		
		if(solution.isGoalFound()) {
			for(Game state : solution.getSolutionStates()) {
				System.out.print(state);
			}
		}else {
			System.out.println("THERE ARE NO SOLUTIONS TO THIS PROBLEM.");
		}
		
	}
}
