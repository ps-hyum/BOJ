package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_3176 {

	static class Node {
		private int to, cost;

		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		public int getTo() {
			return to;
		}

		public int getCost() {
			return cost;
		}
	}

	private int N;
	private ArrayList<ArrayList<Node>> tree;
	private int[][] minCost;
	private int[][] maxCost;
	private int[][] dp;
	private int[] depth;
	private boolean[] visited;

	public BOJ_3176(int n) {
		this.N = n;
		tree = new ArrayList<ArrayList<Node>>();
		tree.add(null);
		for (int i = 0; i < N; ++i)
			tree.add(new ArrayList<Node>());
		minCost = new int[21][N + 1];
		maxCost = new int[21][N + 1];
		dp = new int[21][N + 1];
		depth = new int[N + 1];
		visited = new boolean[N + 1];
	}

	public void setTree(int from, int to, int w) {
		tree.get(from).add(new Node(to, w));
		tree.get(to).add(new Node(from, w));
	}

	public void dfs(int now, int dd) {
		visited[now] = true;
		depth[now] = dd;

		for (int i = 0; i < tree.get(now).size(); ++i) {
			Node e = tree.get(now).get(i);
			if (!visited[e.getTo()]) {
				dfs(e.getTo(), depth[now] + 1);
				dp[0][tree.get(now).get(i).getTo()] = now;
				minCost[0][e.getTo()] = e.getCost();
				maxCost[0][e.getTo()] = e.getCost();
			}
		}
	}

	public void getSTable() {
		minCost[0][1] = Integer.MAX_VALUE;
		maxCost[0][1] = Integer.MIN_VALUE;
		dp[0][1] = 1;
		for (int i = 1; i < 21; ++i) {
			for (int j = 1; j <= N; ++j) {
				dp[i][j] = dp[i - 1][dp[i - 1][j]];
				minCost[i][j] = Math.min(minCost[i - 1][j], minCost[i - 1][dp[i - 1][j]]);
				maxCost[i][j] = Math.max(maxCost[i - 1][j], maxCost[i - 1][dp[i - 1][j]]);
			}
		}
	}

	public int[] getAns(int a, int b) {
		int[] ans = new int[2];
		int x = a, y = b;
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		if (depth[a] < depth[b]) {
			x = b;
			y = a;
		}
		for (int i = 20; i >= 0; --i) {
			if (((depth[x] - depth[y]) >= (1 << i))) {
				min = Math.min(min, minCost[i][x]);
				max = Math.max(max, maxCost[i][x]);
				x = dp[i][x];
			}
		}
		if (x == y) {
			ans[0] = min;
			ans[1] = max;
			return ans;
		}

		for (int i = 20; i >= 0; --i) {
			if (dp[i][x] != dp[i][y]) {
				min = Math.min(minCost[i][x], Math.min(min, minCost[i][y]));
				max = Math.max(maxCost[i][x], Math.max(max, maxCost[i][y]));
				x = dp[i][x];
				y = dp[i][y];
			}
		}
		ans[0] = Math.min(minCost[0][x], Math.min(min, minCost[0][y]));
		ans[1] = Math.max(maxCost[0][x], Math.max(max, maxCost[0][y]));
		return ans;
	}

	public void init() {
		this.dfs(1, 0);
		this.getSTable();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		BOJ_3176 main = new BOJ_3176(N);

		for (int i = 1; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			main.setTree(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
		}
		main.init();

		int K = Integer.parseInt(br.readLine());
		for (int i = 0; i < K; ++i) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int ans[] = main.getAns(u, v);
			System.out.println(ans[0] + " " + ans[1]);
		}
		bw.close();
		br.close();
	}
}