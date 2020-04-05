import back.AlgorithmSolution;
import back.algorithms.BFS;
import back.algorithms.DFS;
import back.algorithms.IDDFS;
import back.game.GameImplementation;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import reader.MapReader;

import java.io.IOException;

public class Main {
	public static void main(String[] args){
		MapReader mapReader;
		try {
			mapReader = new MapReader("sokoban-solver/map2.txt");
		}catch(IOException e) {
			System.err.println(e);
			return;
		}

		Game game = new GameImplementation(mapReader.getMap(), mapReader.getPlayerPosition(), mapReader.getBoxesPositions(), mapReader.getGoalsPositions());

		AlgorithmSolution solution;

		/*System.out.println("IDDFS ---\n");
		Algorithm iddfs = new IDDFS();
		((IDDFS) iddfs).setMaxDepth(75);
		solution = iddfs.run(game);
		System.out.print(solution);*/

		/*System.out.println("DFS ---\n");
		Algorithm dfs = new DFS();
		solution = dfs.run(game);
		System.out.print(solution);*/
		
		/*System.out.println("BFS ---\n");
		Algorithm bfs = new BFS();
		solution = bfs.run(game);
		System.out.print(solution);*/
		


	}
}
