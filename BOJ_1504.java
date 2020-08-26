package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1504 {

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
			return this.getWeight() - o.getWeight();
		}
	}

	private int N;
	private int E;
	private ArrayList<ArrayList<Node>> map;
	private int[][] dist;
	private PriorityQueue<Node> pq;

	public BOJ_1504(int n, int e) {
		this.N = n;
		this.E = e;

		map = new ArrayList<ArrayList<Node>>();
		for (int i = 0; i <= N; ++i) {
			map.add(new ArrayList<Node>());
		}

		dist = new int[3][N + 1];
		for (int i = 0; i < 3; ++i)
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		
		pq = new PriorityQueue<Node>();
	}

	public void setMap(int from, int to, int weight) {
		map.get(from).add(new Node(to, weight));
		map.get(to).add(new Node(from, weight));
	}

	public void dijkstra(int i) {

		// in this loop, Node.to = num / Node.weight = dist

		while (!pq.isEmpty()) {
			Node node = pq.poll();

			for (Node n : map.get(node.getTo())) {
				if(dist[i][n.getTo()] > dist[i][node.getTo()] + n.getWeight()){
					dist[i][n.getTo()] = dist[i][node.getTo()] + n.getWeight();
					pq.offer(n);
				}
			}
		}
	}

	public void solve(int must1, int must2) {

		dist[0][1] = 0;
		pq.add(new Node(1, 0));
		this.dijkstra(0);
		dist[1][must1] = 0;
		pq.add(new Node(must1, 0));
		this.dijkstra(1);
		dist[2][must2] = 0;
		pq.add(new Node(must2, 0));
		this.dijkstra(2);

		// 1tomust1
		int way1 = dist[0][must1] + dist[1][must2] + dist[2][N];
		// 1tomust2
		int way2 = dist[0][must2] + dist[2][must1] + dist[1][N];

		if (dist[0][N] == Integer.MAX_VALUE)
			System.out.println(-1);
		else
			System.out.println(Math.min(way1, way2));
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		BOJ_1504 main = new BOJ_1504(N, E);
		for (int i = 0; i < E; ++i) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			main.setMap(a, b, c);
		}
		st = new StringTokenizer(br.readLine());
		main.solve(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		br.close();
	}
}
