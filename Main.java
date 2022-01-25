public class Main {
	public static void main(String[] args) {
		Labirynth lab = new Labirynth(2000, 2000);
		lab.generateLabirynth();
		lab.writeToBinary("../maze.bin");
		Labirynth rlab = new Labirynth();
		rlab.readFromBinary("../maze.bin");
		//System.out.println(lab);
		//System.out.println(rlab);
		rlab.setBegin(7, 7);
		rlab.setEnd(5, 5);
		rlab.solveLabirynth();
		rlab.writeToBitmap("../maze2.bmp");
	}
}