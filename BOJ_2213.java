package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_2213{

	private int weight[];
	private int n;
	int[] dp1;
	int[] dp2;
	private ArrayList<ArrayList<Integer>> tree;
	ArrayList<Integer> ans;
	private int[] visited;

	public BOJ_2213(int n) {
		this.n = n;
		weight = new int[n + 1];
		dp1 = new int[n + 1];
		dp2 = new int[n + 1];
		tree = new ArrayList<ArrayList<Integer>>();
		ans = new ArrayList<Integer>();
		tree.add(null);
		for (int i = 1; i <= n; ++i) {
			tree.add(new ArrayList<Integer>());
		}
		visited = new int[n + 1];
	}

	public void setWeight(int i, int w) {
		weight[i] = w;
		dp1[i] = w;
	}

	public void setTree(int from, int to) {
		tree.get(from).add(to);
		tree.get(to).add(from);
	}

	public void dfs(int now) {

		visited[now] = 1;
		for (int i = 0; i < tree.get(now).size(); ++i) {
			int child = tree.get(now).get(i);
			if (visited[child] == 0) {
				dfs(child);
				dp1[now] += dp2[child];
				dp2[now] += Math.max(dp1[child], dp2[child]);
			}
			// visited[next] == 1
			else {
				// do nothing
			}
		}
	}

	public void backTrace(int now) {
		visited[now] = 1;
		if (dp1[now] > dp2[now]) {
			ans.add(now);
			for (int i = 0; i < tree.get(now).size(); ++i) {
				int child = tree.get(now).get(i);
				visited[child] = 1;
				for (int j = 0; j < tree.get(child).size(); ++j) {
					if (visited[tree.get(child).get(j)] == 0)
						backTrace(tree.get(child).get(j));
				}
			}
		} else {
			for (int i = 0; i < tree.get(now).size(); ++i) {
				int child = tree.get(now).get(i);
				if (visited[child] == 0)
					backTrace(child);
			}
		}
	}

	public void fill() {
		Arrays.fill(visited, 0);
	}

	public void solve() {
		this.dfs(1);
		this.fill();
		this.backTrace(1);
		System.out.println(Math.max(dp1[1], dp2[1]));
		Collections.sort(ans);
		for (int i = 0; i < ans.size(); ++i) {
			System.out.print(ans.get(i) + " ");
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		BOJ_2213 main = new BOJ_2213(n);

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; ++i) {
			int w = Integer.parseInt(st.nextToken());
			main.setWeight(i, w);
		}

		for (int i = 1; i < n; ++i) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			main.setTree(from, to);
		}
		main.solve();

		br.close();
	}
}
