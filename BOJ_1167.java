package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_1167 {

	static class Node {
		private int child;
		private int weight;

		public Node(int c, int w) {
			this.child = c;
			this.weight = w;
		}

		public int getChild() {
			return child;
		}

		public int getWeight() {
			return weight;
		}
	}

	private int V;
	private ArrayList<ArrayList<Node>> tree;
	private int[] visited;
	private int maxVal;
	private int maxIdx;

	public BOJ_1167(int v) {
		this.V = v;
		tree = new ArrayList<ArrayList<Node>>();
		for (int i = 0; i <= V; ++i) {
			tree.add(new ArrayList<Node>());
		}
		visited = new int[V + 1];
	}

	public void setTree(String str) {
		StringTokenizer st = new StringTokenizer(str);
		int num = Integer.parseInt(st.nextToken());
		while (true) {
			int to = Integer.parseInt(st.nextToken());
			if (to == -1)
				return;
			int weight = Integer.parseInt(st.nextToken());
			tree.get(num).add(new Node(to, weight));
		}
	}

	public void dfs(int start, int total) {
		visited[start] = 1;

		for (Node e : tree.get(start)) {
			int next = e.getChild();
			if (visited[next] == 1)
				continue;
			int weight = e.getWeight();
			if (maxVal < total + weight) {
				maxVal = total + weight;
				maxIdx = next;
			}

			dfs(next, total + weight);
		}
	}

	public void solve(int start) {
		maxVal = maxIdx = 0;
		this.dfs(start, 0);
		maxVal = 0;
		Arrays.fill(visited, 0);
		this.dfs(maxIdx, 0);
		System.out.println(maxVal);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int V = Integer.parseInt(br.readLine());
		BOJ_1167 main = new BOJ_1167(V);

		for (int i = 1; i <= V; ++i) {
			main.setTree(br.readLine());
		}
		main.solve(1);
	}
}
