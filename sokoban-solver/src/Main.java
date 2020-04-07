import back.AlgorithmSolution;
import back.algorithms.*;
import back.cost_functions.TrivialCostFunction;
import back.game.GameImplementation;
import back.heuristics.*;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import back.interfaces.Heuristic;
import reader.ConfigurationReader;
import reader.MapReader;
import reader.exceptions.InvalidFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args){
		ConfigurationReader configReader;
		try{
			configReader = new ConfigurationReader("sokoban-solver/configuration.txt");
		}catch(InvalidFormat e){
			System.out.println("There was a problem with the configuration file. Please check the readme.");
			return;
		}catch (IOException e){
			System.err.print(e);
			return;
		}

		MapReader mapReader;
		try {
			mapReader = new MapReader("sokoban-solver/maps/map"+configReader.getMapNumber());
		}catch(IOException e) {
			System.out.println("There was a problem when reading the specified map.");
			return;
		}

		Game game = new GameImplementation(mapReader.getMap(), mapReader.getPlayerPosition(), mapReader.getBoxesPositions(), mapReader.getGoalsPositions(), null, 0, 0, new TrivialCostFunction());

		Heuristic heuristic = null;
		Algorithm algorithm;

		if(configReader.getHeuristic() != null){
			switch (configReader.getHeuristic()){
				case BRUTEFORCE_ASSIGNATION:
					heuristic = new BruteforceAssignationHeuristic();
					break;
				case GREEDY_ASSIGNATION:
					heuristic = new GreedyAssignationHeuristic();
					break;
				case TRIVIAL:
					heuristic = new TrivialHeuristic();
					break;
				case WALKABLE_DISTANCE:
					heuristic = new WalkableDistanceHeuristic();
					break;
				case MANHATTAN:
					heuristic = new SimpleAssignationHeuristic();
					break;
				default:
					return;
			}
		}

		int maxDepth;
		switch (configReader.getAlgorithm()){
			case A_STAR:
				algorithm = new AStar(heuristic);
				break;
			case BFS:
				algorithm = new BFS();
				break;
			case DFS:
				DFS dfs = new DFS();
				algorithm = dfs;
				break;
			case IDDFS:
				IDDFS iddfs = new IDDFS();
				maxDepth = configReader.getMaxDepth();
				if(maxDepth!=-1)
					iddfs.setMaxDepth(maxDepth);
				algorithm = iddfs;
				break;
			case IDA_STAR:
				algorithm = new IDAStar(heuristic);
				break;
			case GLOBAL_GREEDY:
				algorithm = new GlobalGreedy(heuristic);
				break;
			default:
				return;
		}

		System.out.print("Started running '"+algorithm.getName()+"'");
		if(heuristic != null)
			System.out.print(" with '"+heuristic.getName() +"' heuristic");
		System.out.print('\n');
		AlgorithmSolution solution = algorithm.run(game);

		try{
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("sokoban-solver/output.txt"))){
				bw.write(solution.toString());
			}
		}catch (IOException e){
			System.err.println("Could not save output into file.");
		}

		System.out.println("The algorithm result was saved in output file.");

	}



}
