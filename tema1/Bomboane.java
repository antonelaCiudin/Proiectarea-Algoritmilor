import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Bomboane {
	static class Task {
		int n;
		int candy;
		ArrayList<Pair> intervals;

		private static final int MOD = 1000000007;
		private static final String INPUT_FILE = "bomboane.in";
		private static final String OUTPUT_FILE = "bomboane.out";

		public void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				n = scanner.nextInt();
				candy = scanner.nextInt();
				intervals = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					intervals.add(new Pair(scanner.nextInt(), scanner.nextInt()));
				}
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		//copiez valorile dintr-un vector in alt vector
		long[] copy(long[] array) {
			long[] copy = new long[candy + 1];

			for (int i = 0; i <= candy; i++) {
				copy[i] = array[i];
			}

			return copy;
		}

		public long getResult() {
			//vectorul original si cel de la pasul anterior
			long[] old_candy_vec = new long[candy + 1];
			long[] candy_vec = new long[candy + 1];
			long result = 1;

			//initializez vectorul pentru primul copil
			for (int i = intervals.get(0).start; i <= Math.min(intervals.get(0).end, candy); i++) {
				old_candy_vec[i] = 1;
			}

			for (int i = 1; i < n; i++) {
				for (int j = 0; j <= candy; j++) {
					//calculez noul interval
					int new_start = j - intervals.get(i).end;
					int new_end = j - intervals.get(i).start;

					if ((new_start < 0 && new_end < 0) || (new_start > candy && new_end > candy)) {
						candy_vec[j] = 0;
						continue;
					}
					if (new_start < 0) {
						new_start = 0;
					}
					if (new_end > candy) {
						new_end = candy;
					}
					//sumez valorile din intervalul nou, de la pasul anterior
					for (int k = new_start; k <= new_end; k++) {
						candy_vec[j] += old_candy_vec[k] % MOD;
					}
				}
				//actualizez result, vectorul de la pasul anterior si golesc vectorul original
				result = candy_vec[candy] % MOD;
				old_candy_vec = copy(candy_vec);
				candy_vec = new long[candy + 1];
			}

			return result;
		}

		public void writeOutput(long result) {
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