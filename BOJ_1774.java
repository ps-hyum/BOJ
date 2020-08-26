package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_1774 {

	private int N;
	private int M;
	private XY[] xys;
	int[] parent;
	private PriorityQueue<XY> pq;
	private double sum = 0;
	private int cLen = 0;

	public BOJ_1774(int N, int M) {
		this.N = N;
		this.M = M;
		this.xys = new XY[N + 1];
		this.parent = new int[N + 1];
		for (int i = 1; i <= N; ++i)
			parent[i] = i;
		pq = new PriorityQueue<XY>();
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

		if (pa == pb)
			return;
		parent[pb] = a;
	}

	public void setXY(int i, int x, int y) {
		xys[i] = new XY(x, y);
	}

	public void connect(int from, int to, int flag) {
		int pa = this.find(from);
		int pb = this.find(to);

		if (pa == pb)
			return;

		this.union(from, to);
		this.cLen++;
		if (flag == 0)
			return;
		this.sum += this.getLen(from, to);
	}

	public double solve() {
		for (int i = 1; i <= N; ++i) {
			for (int j = 1; j <= N; ++j) {
				if (i != j)
					pq.offer(new XY(i, j, this.getLen(i, j)));
			}
		}

		while (this.cLen != N - 1) {
			XY xy = pq.poll();
			int[] data = xy.getXY();
			connect(data[0], data[1], 1);
		}
		return sum;
	}

	public double getLen(int from, int to) {
		int[] fxy = xys[from].getXY();
		int[] txy = xys[to].getXY();
		return Math.sqrt(Math.pow((fxy[0] - txy[0]), 2) + Math.pow((fxy[1] - txy[1]), 2));
	}

	static class XY implements Comparable<XY> {
		private int x;
		private int y;
		private double weight;

		public XY(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public XY(int from, int to, double w) {
			this.x = from;
			this.y = to;
			this.weight = w;
		}

		public int[] getXY() {
			int[] arr = { x, y };
			return arr;
		}

		public double getWeight() {
			return this.weight;
		}

		@Override
		public int compareTo(XY o) {
			return this.weight > o.getWeight() ? 1 : -1;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		BOJ_1774 main = new BOJ_1774(N, M);

		for (int i = 1; i <= N; ++i) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			main.setXY(i, x, y);
		}

		for (int i = 1; i <= M; ++i) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			main.connect(from, to, 0);
		}

		System.out.printf("%.2f", main.solve());

		br.close();
	}
}