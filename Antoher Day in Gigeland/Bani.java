import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Bani {
	static class Task {
		long n;
		int type;

		private static final int MOD = 1000000007;
		private static final String INPUT_FILE = "bani.in";
		private static final String OUTPUT_FILE = "bani.out";

		public void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				type = scanner.nextInt();
				n = scanner.nextLong();
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		long logPow(long base, long exp) {
			if (exp == 0) {
				return 1;
			}

			long tmp = 1;
			while (exp != 1) {
				if (exp % 2 == 0) {
					base = (long) ((1L * base * base) % MOD);
					exp /= 2;
				} else {
					tmp = (long) ((1L * tmp * base) % MOD);
					exp--;
				}
			}

			return (long) ((1L * tmp * base) % MOD);
		}

		

		public long getResult() {
			//raspunsul pentru tipul 1
			if (type == 1) {
				return (5 * logPow(2L, n - 1)) % MOD;
			//raspunsul pentru tipul 2
			} else {
				long[] bills_count = new long[5];
				long[] old_bills_count = new long[5];
				long sum = 0;

				//atribui valori pentru cazul de baza
				old_bills_count[0] = 1;
				old_bills_count[1] = 1;
				old_bills_count[2] = 1;
				old_bills_count[3] = 1;
				old_bills_count[4] = 1;

				for (int i = 2; i <= n; i++) {
					//calculez frecventa bancnotelor
					bills_count[0] = (old_bills_count[1] 
						+ old_bills_count[2] + old_bills_count[4]) % MOD;
					bills_count[1] = (old_bills_count[0] + old_bills_count[3]) % MOD;
					bills_count[2] = (old_bills_count[0] 
						+ old_bills_count[2] + old_bills_count[3]) % MOD;
					bills_count[3] = (old_bills_count[1] + old_bills_count[4]) % MOD;
					bills_count[4] = (old_bills_count[3]) % MOD;

					//actualizez vectorul de frecvente vechi
					old_bills_count[0] = bills_count[0];
					old_bills_count[1] = bills_count[1];
					old_bills_count[2] = bills_count[2];
					old_bills_count[3] = bills_count[3];
					old_bills_count[4] = bills_count[4];
				}

				//calculez modurile de aranjare
				for (int i = 0; i < 5; i++) {
					sum += bills_count[i];
					sum = sum % MOD;
				}

				return sum;
			}
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