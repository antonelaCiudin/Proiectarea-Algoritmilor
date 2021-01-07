import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Gard {
	static class Task {
		int n;
		ArrayList<Pair> intervals;

		private static final String INPUT_FILE = "gard.in";
		private static final String OUTPUT_FILE = "gard.out";

		public void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));

				n = scanner.nextInt();
				intervals = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					intervals.add(new Pair(scanner.nextInt(), scanner.nextInt()));
				}
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public int getResult() {
			int res = 0;

			//sortez crescator dupa start, daca sunt egale, descrescator dupa end
			Collections.sort(intervals, Pair.Comparators.STARTANDEND);

			//initializez min si max din primul interval
			int min = intervals.get(0).start;
			int max = intervals.get(0).end;

			for (int i = 1; i < n; i++) {
				//verific daca intervalul se include
				if (intervals.get(i).start >= min && intervals.get(i).end <= max) {
					res++;
				}
				//actualizez sau nu min
				if (intervals.get(i).start > max) {
					min = intervals.get(i).start;
				}
				//actualizez sau nu max
				if (intervals.get(i).end > max) {
					max = intervals.get(i).end;
				}
			}

			return res;
		}

		public void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}