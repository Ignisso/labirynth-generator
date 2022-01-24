public class Main {
	public static void main(String[] args) {
		Labirynth lab = new Labirynth(10, 10);
		lab.generateLabirynth();
		lab.setBegin(7, 7);
		lab.setEnd(5, 5);
		lab.solveLabirynth();
		lab.writeToBitmap("../maze.bmp");
		Labirynth rlab = new Labirynth();
		rlab.readFromBitmap("../maze.bmp");
		rlab.setBegin(5,5);
		rlab.setEnd(7,7);
		rlab.solveLabirynth();
		rlab.writeToBitmap("../maze2.bmp");
		System.out.println(lab);
	}
}