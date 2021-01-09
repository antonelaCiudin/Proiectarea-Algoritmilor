import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P3 {
	static class Task {
		int n; //numarul de orase
		int m; //numarul de drumuri
		double e; //energia initiala a lui Robin
		int source = 1;
		double finalEnergy;
		int[] visited;

		private static final String INPUT_FILE = "p3.in";
		private static final String OUTPUT_FILE = "p3.out";
		public static final int NMAX = 100005; // 10^5

		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] graph = new ArrayList[NMAX];

		public class Edge implements Comparable<Edge> {
			public int node;
			public double percent;

			Edge(int _node, double _percent) {
				node = _node;
				percent = _percent;
			}

			@Override
			public int compareTo(Edge other) {
				return (int)(other.percent - this.percent);
			}
		}

		public void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				n = scanner.nextInt();
				m = scanner.nextInt();
				e = scanner.nextInt();
				for (int i = 1; i <= n; i++) {
					graph[i] = new ArrayList<>();
				}
				for (int i = 1; i < m; i++) {
					int x, y, w;
					x = scanner.nextInt();
					y = scanner.nextInt();
					w = scanner.nextInt();
					graph[x].add(new Edge(y, w));
				}
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public int[] getResult() {
			PriorityQueue<Edge> queue = new PriorityQueue<>();
			queue.add(new Edge(source, e));

			ArrayList<Double> energies = new ArrayList<>();
			visited = new int[n + 1];

			//initializare vector de energiesante si visited
			for (int i = 0; i <= n; i++) {
				energies.add(0.0);
				visited[i] = 0;
			}

			//setare valori initiale pentru sursa
			energies.set(source, e);
			visited[source] = 1;

			//initializare vector de parinti
			int[] parents = new int[n + 1];
			parents[source] = -1;

			//se extrage cate un nod din coada prioritara
			while (!queue.isEmpty()) {
				Edge edge = queue.poll();
				int u = edge.node;

				//pentru fiecare copil al nodului respectiv
				for (Edge e: graph[u]) {
					int v = e.node;
					double w = e.percent;

					//verificare conditie de relaxare
					if (visited[v] == 0 && energies.get(v) < energies.get(u) * (1 - w / 100)) {
						energies.set(v, energies.get(u) * (1 - w / 100));
						parents[v] = u;
						queue.add(new Edge(v, energies.get(v)));
					}
				}

				visited[u] = 1;
			}

			//extragere energie finala
			finalEnergy = energies.get(n);

			return parents;
		}

		//functie de reconstruire a drumului
		private void getPath(int[] parents, int i, ArrayList<Integer> path) {
			if (i >= 1) {
				getPath(parents, parents[i], path);
				path.add(i);
			}
		}

		public void writeOutput(int[] result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.print(finalEnergy);
				pw.printf("\n");
				ArrayList<Integer> path = new ArrayList<>();
				getPath(result, n, path);
				for (int i : path) {
					if (i != path.get(0)) {
						pw.printf(" ");
					}
					pw.printf("%d", i);
				}
				pw.printf("\n");
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