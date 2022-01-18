import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileBMP
implements FileIO {
	private Labirynth maze;
	private BMPInfo   bitmap;

	public static final int WALL_COLOR = 0x000000;
	public static final int ROUTE_COLOR = 0xffffff;
	public static final int CORRECT_COLOR = 0x00ff00;
	public static final int END_COLOR = 0xff0000;
	
	public FileBMP(Labirynth maze) {
		this.maze   = maze;
		this.bitmap = null;
	}
	
	public void load() {
		this.bitmap = new BMPInfo();
		int width = this.maze.getWidth();
		int height = this.maze.getHeight();
		this.bitmap.setInfo(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (this.maze.getCell(i, j).getType() == CellType.WALL)
					this.bitmap.setPixelAt(i, j, WALL_COLOR);
				else if (this.maze.getCell(i, j).getType() == CellType.CORRECT)
					this.bitmap.setPixelAt(i, j, CORRECT_COLOR);
				else if (this.maze.getCell(i, j).getType() == CellType.END)
					this.bitmap.setPixelAt(i, j, END_COLOR);
				else
					this.bitmap.setPixelAt(i, j, ROUTE_COLOR);
			}
		}
	}
	
	public void write(String path) {
		File fileout = new File(path);
		FileOutputStream streamout;
		try {
			streamout = new FileOutputStream(fileout);
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
			return;
		}
		try {
			streamout.write(this.bitmap.getBytes());
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		//streamout.close();
	}
	
	public void read(String path) {
		File filein = new File(path);
		FileInputStream streamin;
		try {
			streamin = new FileInputStream(filein);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			return;
		}
		try {
			byte arr[] = new byte[1024 * 1024 * 512]; // 512 MB
			streamin.read(arr);
			this.bitmap = new BMPInfo();
			this.bitmap.loadHeader(arr);
			this.bitmap.load(arr);
			final int width = bitmap.getWidth();
			final int height = bitmap.getHeight();
			this.maze.setSize(width, height);

			Cell grid[][] = new Cell[width][height];
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (bitmap.getPixelAt(i, j) == WALL_COLOR)
						grid[i][j] = new Cell(i, j, CellType.WALL);
					else if (bitmap.getPixelAt(i, j) == CORRECT_COLOR) {
						grid[i][j] = new Cell(i, j, CellType.CORRECT);
						this.maze.setCompleted();
					}
					else if (bitmap.getPixelAt(i, j) == END_COLOR)
						grid[i][j] = new Cell(i, j, CellType.END);
					else
						grid[i][j] = new Cell(i, j, CellType.ROUTE);
				}
			}
			maze.setGrid(grid);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			return;
		}
	}
}
