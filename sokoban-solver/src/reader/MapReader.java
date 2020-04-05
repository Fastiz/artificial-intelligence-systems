package reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import back.game.CellTypeEnum;

public class MapReader {
	private CellTypeEnum[][] map;
	private int[] playerPosition;
	private int[][] boxesPositions;
	private int[][] goalsPositions;
	
	public MapReader(String file) throws IOException, FileNotFoundException {
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String newLine = br.readLine();
			
			//Read map dimensions
			String[] dimensions = newLine.split(" ");
			int height = Integer.valueOf(dimensions[0]), width = Integer.valueOf(dimensions[1]);
			this.map = new CellTypeEnum[height][width];
			
			int[] playerPosition;
			List<int[]> boxesPositions = new ArrayList<>();
			List<int[]> goalsPositions = new ArrayList<>();
			//Read map content
			for(int i = 0; i<height; i++) {
				char[] lineArray = br.readLine().toCharArray();
				for(int j=0; j<width; j++) {
					switch(lineArray[j]) {
						case ' ':
							this.map[i][j] = CellTypeEnum.EMPTY;
							break;
						case 'X':
							this.map[i][j] = CellTypeEnum.WALL;
							break;
						case '*':
							this.map[i][j] = CellTypeEnum.EMPTY;
							boxesPositions.add(new int[] {i, j});
							break;
						case '.':
							this.map[i][j] = CellTypeEnum.GOAL;
							goalsPositions.add(new int[]{i, j});
							break;
						case '@':
							this.map[i][j] = CellTypeEnum.EMPTY;
							this.playerPosition = new int[] {i, j};
							break;
					}
				}
			}
			this.goalsPositions = goalsPositions.toArray(new int[0][]);
			this.boxesPositions = boxesPositions.toArray(new int[0][]);
		}
	}

	public CellTypeEnum[][] getMap() {
		return map;
	}
	
	public int[] getPlayerPosition() {
		return this.playerPosition;
	}
	
	public int[][] getBoxesPositions(){
		return this.boxesPositions;
	}

	public int[][] getGoalsPositions(){
		return this.goalsPositions;
	}
}