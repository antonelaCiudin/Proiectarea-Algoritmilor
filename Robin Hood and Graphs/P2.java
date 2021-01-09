import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class P2 {
	static class Task {
		int n, m;
		int source, dest;

		private static final String INPUT_FILE = "p2.in";
		private static final String OUTPUT_FILE = "p2.out";
		public static final int NMAX = 100005; // 10^5
		public static final int INF = 1000000;

		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] graph = new ArrayList[NMAX];

		int[] visited;
		int[] topSort;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		public void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				n = scanner.nextInt();
				m = scanner.nextInt();
				source = scanner.nextInt();
				dest = scanner.nextInt();
				for (int i = 1; i <= n; i++) {
					graph[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
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

		//functia de parcurgere in adancime a grafului
		public int dfs(int v, int time) {
			visited[v] = 1;

			for (Edge edge: graph[v]) {
				int u = edge.node;

				if (visited[u] == 0) {
					time = dfs(u, time);
				}
			}
			topSort[time] = v;
			time++;

			return time;
		}

		public int getResult() {
			int time = 1;

			//initializarea vectorilor de vizitati si 
			//indecsii nodurilor
			topSort = new int[n + 1];
			visited = new int[n + 1];

			for (int i = 0; i <= n; i++) {
				topSort[i] = -1;
				visited[i] = 0;
			}

			//se parcurge graful in adancime pentru a obtine
			//o sortare topologica
			for (int i = 1; i <= n; i++) {
				if (visited[i] == 0) {
					time = dfs(i, time);
				}
			}

			//intializare vector de costuri
			int[] cost = new int[n + 1];
			for (int i = 0; i <= n; i++) {
				cost[i] = INF;
			}

			cost[source] = 0;

			//pentru fiecare nod din graf in ordinea
			//data de vectorul topSort
			for (int i = n; i >= 1; i--) {
				int v = topSort[i];
				//pentru fiecare copil al nodului respectiv
				for (Edge edge: graph[v]) {
					int u = edge.node;
					int w = edge.cost;

					//se aplica conditia de relaxare
					if (cost[v] + w < cost[u]) {
						cost[u] = cost[v] + w;
					}
				}
			}
			//extragere cost pana la destinatie
			int result = cost[dest];

			if (result == INF) {
				result = -1;
			}

			return result;
		}

		public void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				// pw.printf("blea"); 
				pw.printf("%d\n", result);
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