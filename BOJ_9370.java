package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_9370 {

	static class Node implements Comparable<Node> {
		private int to;
		private int weight;

		public Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
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

	private int n;
	private int s, g, h;
	private int dist[];
	private ArrayList<ArrayList<Node>> map;
	private PriorityQueue<Node> pq;
	private ArrayList<Integer> ans;

	public BOJ_9370(int n, int s, int g, int h) {
		this.n = n;
		this.s = s;
		this.g = g;
		this.h = h;

		dist = new int[n + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);

		map = new ArrayList<ArrayList<Node>>();
		for (int i = 0; i <= n; ++i) {
			map.add(new ArrayList<Node>());
		}

		pq = new PriorityQueue<Node>();
		ans = new ArrayList<Integer>();
	}

	public void setMap(int from, int to, int weight) {
		if (from == g && to == h) {
			map.get(from).add(new Node(to, 2 * weight - 1));
			map.get(to).add(new Node(from, 2 * weight - 1));
			return;
		}
		if (from == h && to == g) {
			map.get(from).add(new Node(to, 2 * weight - 1));
			map.get(to).add(new Node(from, 2 * weight - 1));
			return;
		}
		map.get(from).add(new Node(to, 2 * weight));
		map.get(to).add(new Node(from, 2 * weight));
	}

	public void dijkstra() {
		dist[s] = 0;
		// in this method, to = nodeNum, weight = weight
		pq.add(new Node(s, 0));

		while (!pq.isEmpty()) {
			Node e = pq.poll();

			for (Node ne : map.get(e.getTo())) {
				if (dist[ne.getTo()] > dist[e.getTo()] + ne.getWeight()) {
					dist[ne.getTo()] = dist[e.getTo()] + ne.getWeight();
					pq.offer(ne);
				}
			}
		}
	}

	public void solve(int x) {
		if (dist[x] != Integer.MAX_VALUE)
			if (dist[x] % 2 == 1) {
				ans.add(x);
			}
	}

	public void getAns() {
		Collections.sort(ans);
		for (int i = 0; i < ans.size(); ++i) {
			System.out.print(ans.get(i) + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int l = 0; l < T; ++l) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int g = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());

			BOJ_9370 main = new BOJ_9370(n, s, g, h);

			for (int i = 0; i < m; ++i) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				main.setMap(a, b, d);
			}
			main.dijkstra();
			for (int i = 0; i < t; ++i) {
				int x = Integer.parseInt(br.readLine());
				main.solve(x);
			}
			main.getAns();
		}
		br.close();
	}
}