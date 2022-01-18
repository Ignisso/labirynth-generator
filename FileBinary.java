public class FileBinary
implements FileIO {
	Labirynth maze;
	
	public FileIO(Labirynth maze) {
		this.maze = null;
	}
	
	public void load() {
		this.maze = maze;
	}
	
	public void write(String path);
	public void read(String path);
}
