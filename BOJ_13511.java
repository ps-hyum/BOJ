package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_13511 {

	static class Node {
		private int to;
		private long cost;

		public Node(int to, long cost) {
			this.to = to;
			this.cost = cost;
		}

		public int getTo() {
			return to;
		}

		public long getCost() {
			return cost;
		}
	}

	public int N;
	public ArrayList<ArrayList<Node>> tree;
	public boolean[] visited;
	public int[] depth;
	public long[] dpCost;
	public int[][] dpParent;

	public BOJ_13511(int n) {
		this.N = n;
		tree = new ArrayList<ArrayList<Node>>();
		tree.add(null);
		for (int i = 1; i <= N; ++i)
			tree.add(new ArrayList<Node>());
		visited = new boolean[N + 1];
		depth = new int[N + 1];
		dpCost = new long[N + 1];
		dpParent = new int[20][N + 1];
	}

	public void setTree(int from, int to, int weight) {
		tree.get(from).add(new Node(to, weight));
		tree.get(to).add(new Node(from, weight));
	}

	public void dfs(int now, int depthVal, long cost) {
		visited[now] = true;
		depth[now] = depthVal;
		dpCost[now] = cost;

		for (int i = 0; i < tree.get(now).size(); ++i) {
			Node e = tree.get(now).get(i);
			if (!visited[e.getTo()]) {
				dfs(e.getTo(), depth[now] + 1, dpCost[now] + e.getCost());
				dpParent[0][e.getTo()] = now;
			}
		}
	}

	public void setTable() {
		dpParent[0][1] = 1;
		for (int i = 1; i < 20; ++i) {
			for (int j = 1; j <= N; ++j) {
				dpParent[i][j] = dpParent[i - 1][dpParent[i - 1][j]];
			}
		}
	}

	public void query(String str) {
		StringTokenizer st = new StringTokenizer(str);
		int flag = Integer.parseInt(st.nextToken());
		if (flag == 1) {
			System.out.println(this.query1(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		} else {
			System.out.println(this.query2(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken())));
		}
	}

	public long query1(int x, int y) {
		int lca = this.getLCA(x, y);
		return dpCost[x] + dpCost[y] - 2 * dpCost[lca];
	}

	public int query2(int x, int y, int k) {
		int lca = this.getLCA(x, y);

		int diff = depth[x] - depth[lca];

		if (diff + 1 >= k) {
			k--;
			for (int i = 19; i >= 0; --i) {
				if ((1 << i) <= k) {
					k -= (1 << i);
					x = dpParent[i][x];
				}
			}
			return x;
		} else {
			k = depth[y] - depth[lca] + 1 + diff - k;
			for (int i = 19; i >= 0; --i) {
				if (((1 << i)) <= k) {
					k -= (1 << i);
					y = dpParent[i][y];
				}
			}
			return y;
		}
	}

	public int getLCA(int x, int y) {
		if (depth[y] > depth[x]) {
			int tmp = x;
			x = y;
			y = tmp;
		}

		for (int i = 19; i >= 0; --i) {
			if ((depth[x] - depth[y]) - (1 << i) >= 0) {
				x = dpParent[i][x];
			}
		}

		if (x == y) {
			return x;
		}

		for (int i = 19; i >= 0; --i) {
			if (dpParent[i][x] != dpParent[i][y]) {
				x = dpParent[i][x];
				y = dpParent[i][y];
			}
		}
		return dpParent[0][x];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		BOJ_13511 main = new BOJ_13511(N);

		for (int i = 1; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			main.setTree(from, to, weight);
		}
		main.dfs(1, 0, 0);
		main.setTable();
		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; ++i) {
			main.query(br.readLine());
		}

		bw.close();
		br.close();
	}
}