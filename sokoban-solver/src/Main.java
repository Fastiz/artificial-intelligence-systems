import java.io.IOException;

import back.AlgorithmSolution;
import back.algorithms.DFS;
import back.game.Game;
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
		

		
		Game game;
		try {
			game = new Game(mapReader.getMap());
		}catch(InvalidMapException e) {
			System.err.print(e);
			return;
		}
		
		Algorithm dfs = new DFS();
		
		AlgorithmSolution solution = dfs.run(game);
		
		System.out.println(solution.isGoalFound());
		
		for(Game state : solution.getSolutionStates()) {
			System.out.print(state);
		}
	}
}
