import java.util.LinkedList;

public class SolverBFS
implements Solver {
    private Labirynth maze;
    
    public SolverBFS(Labirynth lab) {
        this.maze = lab;
    }

    /**
    * Solve labirynth usign BFS
    * @return true if everything went succesfully
    * @throws IncorrectCoordsException if cell index is out of bounds
    */
    public boolean solve() {
        
        LinkedList<Cell> q = new LinkedList<Cell>();
        q.push(maze.getBegin());
        maze.clearVisits();


        while(q.size() > 0) {
            Cell v = q.pop();
            v.visit();

            for(int direction = 0; direction < Direction.LENGTH; direction++) {     
                
                Cell u;
				try {
					u = maze.getCell(v, direction, 1);
				} catch (IncorrectCoordsException e) {
					System.err.println("Cell index is out of bounds");
					return false;
				}
                if(u.getType() == CellType.WALL)
                    continue;
                if(u == null || u.isVisited())
                    continue;

                if(u.x != 0 && u.x != maze.getWidth() - 1 && u.y != 0 && u.y != maze.getHeight() - 1)
                {
                    u.setParent(v);
                    q.push(u);
                }
            }
        }

        Cell cellNow = maze.getEnd();

        while(cellNow != maze.getBegin()) {
            cellNow.setType(CellType.CORRECT);
            cellNow = cellNow.getParent();
        }

        maze.setCompleted();
        maze.getBegin().setType(CellType.END);
        maze.getEnd().setType(CellType.END);

        return true;
    }
}