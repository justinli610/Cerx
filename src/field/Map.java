package field;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

import javax.swing.JFileChooser;

// Represents a field map
public class Map implements Serializable {
	private static final long serialVersionUID = -7469268715860056275L;
	
	public static final int GRASS = 1, PAVEMENT = 2, EMPTY = 3;
	public static final int WORLD = 1, TOWN = 2;
	private Terrain[][] terrain;
	private Landscape[][] landscape;
	private char[][] logicscape; // Determines collision and event squares
	private int type = TOWN; // TODO: Must be set
	
	public Map(int rows, int cols, int defaultVal) {
		terrain = new Terrain[rows][cols];
		landscape = new Landscape[rows][cols];
		logicscape = new char[rows][cols];
		
		if (defaultVal == PAVEMENT) {
			// Initialise tiles and border
			for (int row = 0; row < terrain.length; row++)
				for (int col = 0; col < terrain[0].length; col++) {
					if (row == 0) {
						if (col == 0)
							add(Terrain.pathQ, row, col); // NW
						else if (col == terrain[0].length - 1)
							add(Terrain.pathE, row, col); // NE
						else
							add(Terrain.pathW, row, col); // N
					} else if (row == terrain.length - 1) {
						if (col == 0)
							add(Terrain.pathZ, row, col); // SW
						else if (col == terrain[0].length - 1)
							add(Terrain.pathC, row, col); // SE
						else
							add(Terrain.pathX, row, col); // S
					} else {
						if (col == 0)
							add(Terrain.pathA, row, col); // W
						else if (col == terrain[0].length - 1)
							add(Terrain.pathD, row, col); // E
						else
							add(Terrain.pathS, row, col); // C
					}
				}
		} else if (defaultVal == GRASS) {
			// Initialise tiles
						for (int row = 0; row < terrain.length; row++)
							for (int col = 0; col < terrain[0].length; col++) {
								terrain[row][col] = Terrain.grass;
							}
		} else if (defaultVal == EMPTY) {
			// Initialise tiles
			for (int row = 0; row < terrain.length; row++)
				for (int col = 0; col < terrain[0].length; col++) {
					terrain[row][col] = Terrain.nothing;
				}
		}
		
		// Initialise logicscape
		for (int i = 0; i < logicscape.length; i++) {
			Arrays.fill(logicscape[i], ' ');
		}
		
//		// Add some trees
//		for (int i = 0; i < 10; i++) {
//			int randRow = (int) (Math.random() * landscape.length);
//			int randCol = (int) (Math.random() * landscape[0].length);
//			
//			add(Landscape.treeWide, randRow, randCol);
//			
//			randRow = (int) (Math.random() * landscape.length);
//			randCol = (int) (Math.random() * landscape[0].length);
//			
//			add(Landscape.treeTall, randRow, randCol);
//		}
	}
	
	/**
	 * Takes care of updating the logicscape as well as the landscape when
	 * features are added.
	 * 
	 * @param feature - the object to add
	 * @param row - the row to add to
	 * @param col - the column to add to
	 */
	public void add(Landscape feature, int row, int col) {
		// Add the feature to the landscape
		landscape[row][col] = feature;
		
		// Check what should be added to the logicscape...
		// for a wide tree
		if (feature == Landscape.treeWide || feature == Landscape.tree2) {
			logicscape[row][col] = '1';

			// Attempt to add another solid tile
			if (col + 1 < logicscape[0].length)
				logicscape[row][col+1] = '1';
		} 
		
		else if (feature == null) {
			logicscape[row][col] = ' ';
		}
		
		else {
			logicscape[row][col] = '1';
		}
			
	}
	
	/**
	 * Adds an object to the terrain
	 * 
	 * @param feature - the object to add
	 * @param row - the row to add to
	 * @param col - the column to add to
	 */
	public void add(Terrain feature, int row, int col) {
		// Add the feature to the terrain
		terrain[row][col] = feature;
	}
	
	/**
	 * Adds a value to the logicscape.
	 * 
	 * @param feature - the value to add
	 * @param row - the row to add to
	 * @param col - the column to add to
	 */
	public void add(char feature, int row, int col) {
		// Add the feature to the terrain
		logicscape[row][col] = feature;
	}

	/**
	 * Adds a row to the map at the specified position and shifts all
	 * other elements down.
	 * 
	 * @param row - the row to add
	 */
	public void addRow(int row) {
		int rowLength = terrain.length + 1, colLength = terrain[0].length;
		
		// Size the new arrays
		Terrain[][] newTerrain = new Terrain[rowLength][colLength];
		Landscape[][] newLandscape = new Landscape[rowLength][colLength];
		char[][] newLogicscape = new char[rowLength][colLength];
		
		// Copy rows up until and including the one to add
		for (int i = 0; i < row; i++) {
			newTerrain[i] = terrain[i];
			newLandscape[i] = landscape[i];
			newLogicscape[i] = logicscape[i];
		}
		
		// Add the additional row
		newTerrain[row] = new Terrain[colLength];
		newLandscape[row] = new Landscape[colLength];
		newLogicscape[row] = new char[colLength];
		
		// Legalise the row to default values
		for (int i = 0; i < colLength; i++) {
			newTerrain[row][i] = Terrain.nothing;
			newLandscape[row][i] = null;
			newLogicscape[row][i] = ' ';
		}
		
		// Copy all further elements
		for (int i = row; i < terrain.length; i++) {
			newTerrain[i+1] = terrain[i];
			newLandscape[i+1] = landscape[i];
			newLogicscape[i+1] = logicscape[i];
		}
		
		// Replace the old arrays
		terrain = newTerrain;
		landscape = newLandscape;
		logicscape = newLogicscape;
	}

	/**
	 * Removes the specified row from the map and shifts all other elements up.
	 * 
	 * @param row - the row to remove
	 */
	public void removeRow(int row) {
		int rowLength = terrain.length - 1, colLength = terrain[0].length;
		
		// Size the new arrays
		Terrain[][] newTerrain = new Terrain[rowLength][colLength];
		Landscape[][] newLandscape = new Landscape[rowLength][colLength];
		char[][] newLogicscape = new char[rowLength][colLength];
		
		// Copy rows up until the one to remove
		for (int i = 0; i < row; i++) {
			newTerrain[i] = terrain[i];
			newLandscape[i] = landscape[i];
			newLogicscape[i] = logicscape[i];
		}
		
		// Copy all further elements
		for (int i = row+1; i < rowLength + 1; i++) {
			newTerrain[i-1] = terrain[i];
			newLandscape[i-1] = landscape[i];
			newLogicscape[i-1] = logicscape[i];
		}
		
		// Replace the old arrays
		terrain = newTerrain;
		landscape = newLandscape;
		logicscape = newLogicscape;
	}
	
	/**
	 * Adds a column to the map at the specified position and shifts all
	 * other elements to the right.
	 * 
	 * @param column - the position to add at
	 */
	public void addColumn(int column) {
		int rowLength = terrain.length, colLength = terrain[0].length + 1;
		
		// Size the new arrays
		Terrain[][] newTerrain = new Terrain[rowLength][colLength];
		Landscape[][] newLandscape = new Landscape[rowLength][colLength];
		char[][] newLogicscape = new char[rowLength][colLength];
		
		// Copy elements in each row up until the one to add
		for (int row = 0; row < rowLength; row++) {
			for (int col = 0; col < column; col++) {
				newTerrain[row][col] = terrain[row][col];
				newLandscape[row][col] = landscape[row][col];
				newLogicscape[row][col] = logicscape[row][col];
			}
		}
		
		// Legalise the column to default values
		for (int row = 0; row < rowLength; row++) {
			newTerrain[row][column] = Terrain.nothing;
			newLandscape[row][column] = null;
			newLogicscape[row][column] = ' ';
		}
		
		// Copy elements in each row after the added one
		for (int row = 0; row < rowLength; row++) {
			for (int col = column; col < terrain[0].length; col++) {
				newTerrain[row][col+1] = terrain[row][col];
				newLandscape[row][col+1] = landscape[row][col];
				newLogicscape[row][col+1] = logicscape[row][col];
			}
		}
		
		// Replace the old arrays
		terrain = newTerrain;
		landscape = newLandscape;
		logicscape = newLogicscape;
	}
	
	/**
	 * Removes a column from the map and shifts all other elements to the left.
	 * 
	 * @param column - the column to remove
	 */
	public void removeColumn(int column) {
		int rowLength = terrain.length, colLength = terrain[0].length - 1;
		
		// Size the new arrays
		Terrain[][] newTerrain = new Terrain[rowLength][colLength];
		Landscape[][] newLandscape = new Landscape[rowLength][colLength];
		char[][] newLogicscape = new char[rowLength][colLength];
		
		// Copy elements in each row up until the one to remove
		for (int row = 0; row < rowLength; row++) {
			for (int col = 0; col < column; col++) {
				newTerrain[row][col] = terrain[row][col];
				newLandscape[row][col] = landscape[row][col];
				newLogicscape[row][col] = logicscape[row][col];
			}
		}
		
		// Copy elements in each row after the removed one
		for (int row = 0; row < rowLength; row++) {
			for (int col = column+1; col < colLength + 1; col++) {
				newTerrain[row][col-1] = terrain[row][col];
				newLandscape[row][col-1] = landscape[row][col];
				newLogicscape[row][col-1] = logicscape[row][col];
			}
		}
		
		// Replace the old arrays
		terrain = newTerrain;
		landscape = newLandscape;
		logicscape = newLogicscape;
	}
	
	public Terrain[][] getTerrain() {
		return terrain;
	}
	
	public Landscape[][] getLandscape() {
		return landscape;
	}
	
	public char[][] getLogicscape() {
		return logicscape;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(terrain[0].length*40, terrain.length*40);
	}

	/**
	 * Retrieves the map's type, whether a town or outdoor map. This is used to
	 * determine the scale of the drawing.
	 * 
	 * @return - the type of map
	 */
	public int getType() {
		return type;
	}

	/**
	 * Adds the specified object to the terrain, as well as a gradient around it. This
	 * method doesn't work properly.
	 * 
	 * @param feature - the object to add
	 * @param row - the row to add to
	 * @param col - the column to add to
	 */
	@Deprecated
	public void addGradient(Terrain feature, int row, int col) {
		terrain[row][col] = feature;
		
		if (feature == Terrain.pathS) {			
			try {
				if (terrain[row-1][col] == Terrain.grass)
					terrain[row-1][col] = Terrain.pathW;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row-1][col+1] == Terrain.grass)
					terrain[row-1][col+1] = Terrain.pathE;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row][col+1] == Terrain.grass)
					terrain[row][col+1] = Terrain.pathD;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row+1][col+1] == Terrain.grass)
					terrain[row+1][col+1] = Terrain.pathC;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row+1][col] == Terrain.grass)
					terrain[row+1][col] = Terrain.pathX;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row+1][col-1] == Terrain.grass)
					terrain[row+1][col-1] = Terrain.pathZ;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row][col-1] == Terrain.grass)
					terrain[row][col-1] = Terrain.pathA;
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if (terrain[row-1][col-1] == Terrain.grass)
					terrain[row-1][col-1] = Terrain.pathQ;
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
	}
}
