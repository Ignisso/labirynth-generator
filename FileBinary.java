public class FileBinary
implements FileIO {
	Labirynth maze;
	
	public FileIO() {
		this.maze = null;
	}
	
	public void load(Labirynth maze) {
		this.maze = maze;
	}
	
	public void write(String path);
	public void read(String path);
}
