import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ArrayIndexOutOfBoundsException;

public class FileBMP
implements FileIO {
	private Labirynth maze;
	private BMPInfo   bitmap;

	public static final int WALL_COLOR    = 0x000000;
	public static final int ROUTE_COLOR   = 0xffffff;
	public static final int CORRECT_COLOR = 0x00ff00;
	public static final int END_COLOR     = 0xff0000;
	
	public FileBMP(Labirynth maze) {
		this.maze   = maze;
		this.bitmap = null;
	}
	
	public boolean load() {
		this.bitmap = new BMPInfo();
		int width = this.maze.getWidth();
		int height = this.maze.getHeight();
		this.bitmap.setInfo(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Cell c;
				try {
					c = this.maze.getCell(i, j);
				} catch (IncorrectCoordsException e) {
					System.err.println("Bitmap coords is not valid");
					return false;
				}
				try {
					switch (c.getType()) {
						case WALL:
							this.bitmap.setPixelAt(i, j, WALL_COLOR);
							break;
						case ROUTE:
							this.bitmap.setPixelAt(i, j, ROUTE_COLOR);
							break;
						case CORRECT:
							this.bitmap.setPixelAt(i, j, CORRECT_COLOR);
							break;
						case END:
							this.bitmap.setPixelAt(i, j, END_COLOR);
							break;
					}
				} catch (UninitializedDataException e) {
					System.err.println("Bitmap info is not set");
					return false;
				} catch (IncorrectCoordsException e) {
					System.err.println("X or Y coords is not valid");
					return false;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("Initialized not enough bitmap memory");
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean write(String path) {
		File fileout;
		try {
			fileout = new File(path);
		} catch (NullPointerException e) {
			System.err.println("Invalid path to file");
			return false;
		}
		try (FileOutputStream streamout = new FileOutputStream(fileout)){
			try {
				streamout.write(this.bitmap.getBytes());
			} catch (IOException e) {
				System.err.println("Error occured while writing to file " + path);
				return false;
			} catch (NullPointerException e) {
				System.err.println("No data to write");
				return false;
			}
		} catch (IOException e) {
			System.err.println("Could not open or create file " + path);
			return false;
		}
		return true;
	}
	
	public boolean read(String path) {
		this.maze.clear();
		File filein;
		try {
			filein = new File(path);
		} catch (NullPointerException e) {
			System.err.println("Invalid path to file");
			return false;
		}
		byte arr[] = new byte[1024 * 1024 * 512]; // 512 MB
		int len = 0;
		try (FileInputStream streamin = new FileInputStream(filein)) {
			try {
				len = streamin.read(arr);
			} catch (IOException e) {
				System.err.println("Error occured while reading from file " + path);
				return false;
			}
		} catch (IOException e) {
			System.err.println("Could not read from file " + path);
			return false;
		}
		
		if (len <= 26) {
			System.err.println("Tried to load empty or invalid labirynth");
			return false;
		}
		
		this.bitmap = new BMPInfo();
		try {
			this.bitmap.loadHeader(arr);
			this.bitmap.load(arr);
		} catch (NullPointerException e) {
			System.err.println("Reading data is null");
			return false;
		} catch (UninitializedDataException e) {
			System.err.println("Bitmap was not initialized");
			return false;
		} catch (IncorrectDataFormatException e) {
			System.err.println();
			return false;
		}
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		try {
			this.maze.setSize(width, height);
		} catch (IncorrectSizeException e) {
			System.err.println("Width and height can not be larger than 2048");
			return false;
		}
		
		Cell grid[][] = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				try {
					switch (bitmap.getPixelAt(i, j)) {
						case WALL_COLOR:
							grid[i][j] = new Cell(i, j, CellType.WALL);
							break;
						case ROUTE_COLOR:
							grid[i][j] = new Cell(i, j, CellType.ROUTE);
							break;
						case CORRECT_COLOR:
							grid[i][j] = new Cell(i, j, CellType.CORRECT);
							break;
						case END_COLOR:
							grid[i][j] = new Cell(i, j, CellType.END);
							this.maze.setCompleted();
							break;
					}
				} catch (UninitializedDataException e) {
					System.err.println("Bitmap is uninitialized");
					return false;
				} catch (IncorrectCoordsException e) {
					System.err.println("Bitmap is inconsistent");
					return false;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("Bitmap memory was not properly allocated");
					return false;
				}
			}
		}
		try {
			this.maze.setGrid(grid);
		} catch (NullPointerException e) {
			System.err.println("No data to read/write");
				return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Grid is inconsistent");
				return false;
		}
		return true;
	}
}
