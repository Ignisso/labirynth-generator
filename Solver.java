public class Solver {
    private Labirynth maze;
    
    public Solver(Labirynth lab) {
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
        
        Cell cellNow = maze.getEnd();
        
        while(cellNow != null) {
            
            if(cellNow == maze.getBegin()) {
                maze.getBegin().setType(CellType.CORRECT);
                break;      
            }

            cellNow.setType(CellType.CORRECT);
            cellNow = cellNow.getParent();
        }
        maze.setCompleted();

        return true;
    }
}