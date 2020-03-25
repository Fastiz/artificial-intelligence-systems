package reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import game.CellTypeEnum;

public class MapReader {
	private CellTypeEnum[][] map;
	
	public MapReader(String file) throws IOException, FileNotFoundException {
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String newLine = br.readLine();
			
			//Read map dimensions
			String[] dimensions = newLine.split(" ");
			int height = Integer.valueOf(dimensions[0]), width = Integer.valueOf(dimensions[1]);
			this.map = new CellTypeEnum[height][width];
			
			//Read map content
			for(int i = 0; i<height; i++) {
				char[] lineArray = br.readLine().toCharArray();
				for(int j=0; j<width; j++) {
					CellTypeEnum newCell = null;
					switch(lineArray[j]) {
						case ' ':
							newCell = CellTypeEnum.EMPTY;
							break;
						case 'X':
							newCell = CellTypeEnum.WALL;
							break;
						case '*':
							newCell = CellTypeEnum.BOX;
							break;
						case '.':
							newCell = CellTypeEnum.GOAL;
							break;
						case '@':
							newCell = CellTypeEnum.PLAYER;
							break;
					}
					this.map[i][j] = newCell;
				}
			}
		}
	}

	public CellTypeEnum[][] getMap() {
		return map;
	}
}
