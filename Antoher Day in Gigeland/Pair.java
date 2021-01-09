import java.util.Comparator;

public class Pair implements Comparable<Pair> {

	int start;
	int end;

	public Pair(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "[ " + start + ", " + end + " ]";
	}

	@Override
	public int compareTo(Pair o) {
		return Comparators.START.compare(this, o);
	}


	public static class Comparators {

		public static Comparator<Pair> START = new Comparator<Pair>() {
			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.start - o2.start;
			}
		};
		public static Comparator<Pair> END = new Comparator<Pair>() {
			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.end - o2.end;
			}
		};
		public static Comparator<Pair> STARTANDEND = new Comparator<Pair>() {
			@Override
			public int compare(Pair o1, Pair o2) {
				int i = o1.start - o2.start;
				if (i == 0) {
					i = o2.end - o1.end;
				}
				return i;
			}
		};
	}
}