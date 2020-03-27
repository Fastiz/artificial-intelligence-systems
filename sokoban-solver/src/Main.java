import back.AlgorithmSolution;
import back.algorithms.DFS;
import back.game.GameImplementation;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import reader.MapReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
