public class IntPair {
	private int first;
	private int second;

	public IntPair(int first, int second) {
		this.first = first;
		this.second = second;
	}

	public int first() {
		return this.first;
	}
	
	public int second() {
		return this.second;
	}

	@Override
	public String toString() {
		String s = "";
		s += "(";
		s += this.first();
		s += ", ";
		s += this.second();
		s += ")";

		return s;
	}
}