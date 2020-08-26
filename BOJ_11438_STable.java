package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_11438_STable {

	public int N;
	public ArrayList<ArrayList<Integer>> tree;
//public ArrayList<Integer> euler;
	public boolean[] visited;
	public int[] depth;
	public int[][] STable;

	public BOJ_11438_STable(int n) {
		this.N = n;
		tree = new ArrayList<ArrayList<Integer>>();
		tree.add(null);
		for (int i = 0; i < N; ++i)
			tree.add(new ArrayList<Integer>());
//	euler = new ArrayList<Integer>();
		visited = new boolean[N + 1];
		depth = new int[N + 1];
		STable = new int[18][N + 1];
		STable[0][1] = 1;
	}

	public void setTree(int u, int v) {
		tree.get(u).add(v);
		tree.get(v).add(u);
	}

	public void dfs(int now, int dd) {
		visited[now] = true;
		depth[now] = dd + 1;

		for (int i = 0; i < tree.get(now).size(); ++i) {
			if (visited[tree.get(now).get(i)] == false) {
				STable[0][tree.get(now).get(i)] = now;
				this.dfs(tree.get(now).get(i), depth[now]);
			}
		}
	}

	public void makeSTable() {
		this.dfs(1, -1);
		for (int i = 1; i < 18; ++i) {
			for (int j = 1; j <= N; ++j) {
				STable[i][j] = STable[i - 1][STable[i - 1][j]];
			}
		}
	}

	public int getAns(int a, int b) {
		int aa = a;
		int bb = b;
		if (depth[b] > depth[a]) {
			aa = b;
			bb = a;
		}

		for (int i = 17; i >= 0; --i) {
			if (((depth[aa] - depth[bb]) & (1 << i)) != 0) {
				aa = STable[i][aa];
			}
		}
		if (aa == bb)
			return bb;

		for (int i = 17; i >= 0; --i) {
			if (STable[i][aa] != STable[i][bb]) {
				aa = STable[i][aa];
				bb = STable[i][bb];
			}
		}
		return STable[0][aa];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		BOJ_11438_STable main = new BOJ_11438_STable(N);

		for (int i = 1; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			main.setTree(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		main.makeSTable();

		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; ++i) {
			st = new StringTokenizer(br.readLine());
			System.out.println(main.getAns(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}

		bw.close();
		br.close();
	}
}