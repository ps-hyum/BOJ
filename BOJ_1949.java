package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_1949 {

static class City {
	public int from, to, cost;
	
	public City (int f, int t, int c) {
		from = f;
		to = t;
		cost = c;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public int getCost() {
		return cost;
	}
}

public int N;
public int[][] dp;
public boolean[] visited;
public ArrayList<ArrayList<Integer>> tree;

public BOJ_1949(int n) {
	this.N = n;
	dp = new int[N+1][2];
	visited = new boolean[N+1];
	tree = new ArrayList<ArrayList<Integer>>();
	tree.add(null);
	for(int i=1; i<=N; ++i)
		tree.add(new ArrayList<Integer>());
}

public void setCost(int i, int cost) {
	dp[i][1] = cost;
}

public void setTree(int from, int to) {
	tree.get(from).add(to);
	tree.get(to).add(from);
}

public void dfs(int now) {
	visited[now] = true;
	
	for(int i=0; i<tree.get(now).size(); ++i) {
		int child = tree.get(now).get(i);
		if(!visited[child]) {
			dfs(child);
			dp[now][1] += dp[child][0];
			dp[now][0] += Math.max(dp[child][0], dp[child][1]);
		}
	}
}

public void solve() {
	this.dfs(1);
	System.out.println(Math.max(dp[1][1], dp[1][0]));
}

public static void main(String[] args) throws IOException {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	int N = Integer.parseInt(br.readLine());
	BOJ_1949 main = new BOJ_1949(N);
	
	StringTokenizer st = new StringTokenizer(br.readLine());
	for (int i = 1; i <= N; ++i) {
		main.setCost(i, Integer.parseInt(st.nextToken()));
	}

	for (int i = 1; i < N; i++) {
		st = new StringTokenizer(br.readLine());
		int u = Integer.parseInt(st.nextToken());
		int v = Integer.parseInt(st.nextToken());
		main.setTree(u, v);
	}
	main.solve();

	br.close();
}
}