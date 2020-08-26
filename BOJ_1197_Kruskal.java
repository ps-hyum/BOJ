package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_1197_Kruskal {

	static class Node implements Comparable<Node> {

		private int from, to, weight;

		public Node(int a, int b, int c) {
			this.from = a;
			this.to = b;
			this.weight = c;
		}

		public int getFrom() {
			return from;
		}

		public int getTo() {
			return to;
		}

		public int getWeight() {
			return weight;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.weight - o.getWeight();
		}
	}

	private int V;
	private int E;
	private PriorityQueue<Node> pq;
	int[] parent;
	private int sum;

	public BOJ_1197_Kruskal(int V, int E) {
		this.V = V;
		this.E = E;
		pq = new PriorityQueue<Node>();
		this.parent = new int[V + 1];
		for (int i = 1; i <= V; ++i)
			parent[i] = i;
		this.sum = 0;
	}

	public void getTree(int a, int b, int c) {
		pq.offer(new Node(a, b, c));
	}

	public int kruskal() {

		while (!pq.isEmpty()) {
			Node e = pq.poll();
			int from = e.getFrom();
			int to = e.getTo();
			int weight = e.getWeight();

			int pa = this.find(from);
			int pb = this.find(to);

			if (pa == pb)
				continue;

			this.union(from, to);
			sum += weight;
		}
		return sum;
	}

	public int find(int a) {
		if (a == parent[a])
			return a;

		parent[a] = find(parent[a]);
		return parent[a];
	}

	public void union(int a, int b) {
		int pa = this.find(a);
		int pb = this.find(b);

		if (pa != pb) {
			parent[pb] = a;
		} else
			return;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		BOJ_1197_Kruskal main = new BOJ_1197_Kruskal(V, E);

		for (int i = 1; i <= E; ++i) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			main.getTree(A, B, C);
		}
		System.out.println(main.kruskal());
		br.close();
	}
}

