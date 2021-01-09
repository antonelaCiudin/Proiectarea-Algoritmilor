import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class P1 {
	static class Task {
		int n, m, k;
		int[] cities;
		int[] permut;

		private static final String INPUT_FILE = "p1.in";
		private static final String OUTPUT_FILE = "p1.out";
		public static final int NMAX = 100005; // 10^5

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] graph = new ArrayList[NMAX];

		public void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				n = scanner.nextInt();
				m = scanner.nextInt();
				k = scanner.nextInt();
				cities = new int[k];
				for (int i = 0; i < k; i++) {
					cities[i] = scanner.nextInt();
				}
				permut = new int[n - 1];
				for (int i = 0; i < n - 1; i++) {
					permut[i] = scanner.nextInt();
				}
				for (int i = 1; i <= n; i++) {
					graph[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = scanner.nextInt();
					y = scanner.nextInt();
					graph[x].add(y);
					graph[y].add(x);
				}
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public int getResult() {
			return 0;
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