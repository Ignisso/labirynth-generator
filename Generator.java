import java.util.Stack;
import java.util.Random;

public class Generator {
    private Labirynth maze;
    long seed;
    
    public Generator(Labirynth lab, long seed) {
    	this.maze = lab;
    	this.seed = seed;
    }

    /**
     * Generates walls inside Labirynth using Random DFS
     * 
     * It starts generatin from beginCell it looks for all
     * non-visited neighbours and enters one of them at random.
     * Repeats this process untill there is no more neighbours,
     * Then it returns and on its way back it checks whether that
     * cell has neighbours that can be entered. It repeats this
     * process until it returns to begin.
     * Additionally during travers it marks furthest cell that is
     * still on border as endCell and sets a parent for every cell.
     */
    public boolean generate() {

    	int width = maze.getWidth();
    	int height = maze.getHeight();

		Stack <Cell> stack = new Stack<Cell>();
		Random rand = new Random(seed); // We can pass seed here		

		stack.push(maze.getCell(1,1));
		while(stack.size() != 0) {

			Cell v = stack.pop();
			
			maze.getCell(v.x, v.y).setType(CellType.ROUTE);
			v.visit();

			int offset = rand.nextInt(Direction.LENGTH + 1);
			for(int direction = 0; direction < Direction.LENGTH; direction++) {		
				Cell u = maze.getCell(v, (offset + direction) % Direction.LENGTH, 2);

				if(u == null || u.isVisited())
					continue;

				if(u.x != 0 && u.x != width - 1 && u.y != 0 && u.y != height - 1)
				{
					maze.getCell((v.x + u.x)/2, (v.y + u.y)/2).setType(CellType.ROUTE);
					stack.push(v);
					stack.push(u);
					break;
				}

			}
		}

    	return true;
    }
}