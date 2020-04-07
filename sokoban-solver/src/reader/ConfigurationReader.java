package reader;


import reader.exceptions.InvalidFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationReader {
	public enum Algorithms {
		A_STAR, BFS, DFS, GLOBAL_GREEDY, IDA_STAR, IDDFS;

		public boolean isInformed(){
			return this == A_STAR || this == GLOBAL_GREEDY || this == IDA_STAR;
		}
	}

	public enum Heuristics {
		BRUTEFORCE_ASSIGNATION, GREEDY_ASSIGNATION, TRIVIAL, WALKABLE_DISTANCE,
		MANHATTAN;
	}

	private Algorithms algorithm;
	private Heuristics heuristic;
	private int maxDepth;
	private boolean cacheVisitedStates;
	private int mapNumber;

	public ConfigurationReader(String file) throws IOException, InvalidFormat {
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String newLine;

			newLine = readLineIgnoringComment(br);
			try{
				this.mapNumber = Integer.parseInt(newLine);
			}catch (NumberFormatException e){
				throw new InvalidFormat();
			}

			newLine = readLineIgnoringComment(br);
			int algorithm;
			try{
				algorithm = Integer.parseInt(newLine);
			}catch (NumberFormatException e){
				throw new InvalidFormat();
			}

			switch (algorithm){
				case 1:
					this.algorithm = Algorithms.A_STAR;
					break;
				case 2:
					this.algorithm = Algorithms.BFS;
					break;
				case 3:
					this.algorithm = Algorithms.DFS;
					break;
				case 4:
					this.algorithm = Algorithms.GLOBAL_GREEDY;
					break;
				case 5:
					this.algorithm = Algorithms.IDA_STAR;
					break;
				case 6:
					this.algorithm = Algorithms.IDDFS;
					break;
				default:
					throw new InvalidFormat();
			}

			if(this.algorithm.isInformed()){
				newLine = readLineIgnoringComment(br);

				int heuristic;
				try{
					heuristic = Integer.parseInt(newLine);
				}catch (NumberFormatException e){
					throw new InvalidFormat();
				}

				switch (heuristic){
					case 1:
						this.heuristic = Heuristics.BRUTEFORCE_ASSIGNATION;
						break;
					case 2:
						this.heuristic = Heuristics.GREEDY_ASSIGNATION;
						break;
					case 3:
						this.heuristic = Heuristics.TRIVIAL;
						break;
					case 4:
						this.heuristic = Heuristics.WALKABLE_DISTANCE;
						break;
					case 5:
						this.heuristic = Heuristics.MANHATTAN;
						break;
					default:
						throw new InvalidFormat();
				}
			}

			if(this.algorithm == Algorithms.IDDFS){
				newLine = readLineIgnoringComment(br);

				this.maxDepth = Integer.parseInt(newLine);
			}

		}
	}

	public Algorithms getAlgorithm() {
		return algorithm;
	}

	public Heuristics getHeuristic() {
		return heuristic;
	}

	public int getMapNumber() {
		return mapNumber;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public boolean getCacheVisitedStates(){
		return this.cacheVisitedStates;
	}

	private String readLineIgnoringComment(BufferedReader br) throws InvalidFormat{
		try{
			String line = null;
			while(line == null){
				line = br.readLine();
				if(line.toCharArray()[0] == '#')
					line = null;
			}
			return line;
		}catch (IOException e){
			throw new InvalidFormat();
		}

	}
}
