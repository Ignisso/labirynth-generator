package com.labirynth.core;

import java.util.Stack;
import java.util.Random;

public class GeneratorDFS
implements Generator {
    private Labirynth maze;
    long seed;
    
    public GeneratorDFS(Labirynth lab, long seed) {
    	this.maze = lab;
    	this.seed = seed;
    }

    /**
    * Generate labirynth using Random DFS algorithm and given seed
    * @return true if everything went succesfully
    * @throws IncorrectCoordsException if Cell is out of bounds
    */
    public boolean generate() {

    	int width = maze.getWidth();
    	int height = maze.getHeight();

		Stack <Cell> stack = new Stack<Cell>();
		Random rand = new Random(seed); // We can pass seed here		
		
		try {
			stack.push(maze.getCell(1,1));
		} catch (IncorrectCoordsException e) {
			System.err.println("Cell index is out of bounds");
			return false;
		}
		while(stack.size() != 0) {

			Cell v = stack.pop();
			
			try {
				maze.getCell(v.x, v.y).setType(CellType.ROUTE);
			} catch (IncorrectCoordsException e) {
				System.err.println("Cell index is out of bounds");
				return false;
			}
			v.visit();

			int offset = rand.nextInt(Direction.LENGTH + 1);
			for(int direction = 0; direction < Direction.LENGTH; direction++) {		
				Cell u;
				try {
					u = maze.getCell(v, (offset + direction) % Direction.LENGTH, 2);
				} catch (IncorrectCoordsException e) {
					System.err.println("Cell index is out of bounds");
					return false;
				}

				if(u == null || u.isVisited())
					continue;

				if(u.x != 0 && u.x != width - 1 && u.y != 0 && u.y != height - 1)
				{
					try {
						maze.getCell((v.x + u.x)/2, (v.y + u.y)/2).setType(CellType.ROUTE);
					} catch (IncorrectCoordsException e) {
						System.err.println("Cell index is out of bounds");
						return false;
					}
					stack.push(v);
					stack.push(u);
					break;
				}

			}
		}

    	return true;
    }
}