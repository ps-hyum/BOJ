package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_2533 {

	private ArrayList<ArrayList<Integer>> tree;
	private int N;
	private int[] visited;
	int[][] dp;
	int[] trace;

	public BOJ_2533(int n) {
		this.N = n;
		visited = new int[N + 1];
		// dp[i][0] = not include i, dp[i][1] = include i
		dp = new int[N + 1][2];
		tree = new ArrayList<ArrayList<Integer>>();
		tree.add(null);
		for (int i = 1; i <= N; ++i) {
			tree.add(new ArrayList<Integer>());
			dp[i][1] = 1;
		}
	}

	public void setTree(int u, int v) {
		tree.get(u).add(v);
		tree.get(v).add(u);
	}

	public void dfs(int now) {
		visited[now] = 1;

		for (int i = 0; i < tree.get(now).size(); ++i) {
			int child = tree.get(now).get(i);
			if (visited[child] == 0) {
				dfs(child);
				dp[now][1] += dp[child][0];
				dp[now][0] += Math.max(dp[child][0], dp[child][1]);
			}
		}
	}

	public int getAns() {
		return Math.max(dp[1][1], dp[1][0]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		BOJ_2533 main = new BOJ_2533(N);
		for (int i = 1; i < N; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			main.setTree(u, v);
		}
		main.dfs(1);
		System.out.println(N - main.getAns());

		br.close();
	}
}
