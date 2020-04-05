import back.AlgorithmSolution;
import back.algorithms.*;
import back.game.GameImplementation;
import back.heuristics.BruteforceAssignationHeuristic;
import back.heuristics.GreedyAssignationHeuristic;
import back.heuristics.ManhattanHeuristic;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import reader.MapReader;

import java.io.IOException;

public class Main {
	public static void main(String[] args){
		MapReader mapReader;
		try {
			mapReader = new MapReader("sokoban-solver/level3");
		}catch(IOException e) {
			System.err.println(e);
			return;
		}

		Game game = new GameImplementation(mapReader.getMap(), mapReader.getPlayerPosition(), mapReader.getBoxesPositions(), mapReader.getGoalsPositions(), null, 0);

		AlgorithmSolution solution;

		/*System.out.println("IDDFS ---\n");
		Algorithm iddfs = new IDDFS();
		solution = iddfs.run(game);
		System.out.print(solution);*/

		/*System.out.println("DFS ---\n");
		DFS dfs = new DFS();
		solution = dfs.run(game);
		System.out.print(solution);*/
		
		/*System.out.println("BFS ---\n");
		BFS bfs = new BFS();
		solution = bfs.run(game);
		System.out.print(solution);*/
		
		System.out.println("A* ---\n");
		AStar aStar = new AStar(new ManhattanHeuristic());
		solution = aStar.run(game);
		System.out.print(solution);

	}
}
