import java.util.LinkedList;

public class SolverBFS
implements Solver {
    private Labirynth maze;
    
    public SolverBFS(Labirynth lab) {
        this.maze = lab;
    }

    /**
     * Solves labirynth using data from random DFS
     * 
     * Starting from endCell we traverse to their parent and mark every 
     * cell on our way as correct path until we reach beginCell. Then we
     * mark Labirynth as completed.
     */

    public boolean solve() {
        
        LinkedList<Cell> q = new LinkedList<Cell>();
        q.push(maze.getBegin());
        maze.clearVisits();


        while(q.size() > 0) {
            Cell v = q.pop();
            v.visit();

            for(int direction = 0; direction < Direction.LENGTH; direction++) {     
                
                Cell u = maze.getCell(v, direction, 1);
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