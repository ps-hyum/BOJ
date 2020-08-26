package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_3584 {

	public int[] parent;
	public int[] depth;
	private int N;

	public BOJ_3584(int n) {
		this.N = n;
		parent = new int[N + 1];
		depth = new int[N + 1];
		for (int i = 1; i <= N; ++i) {
			parent[i] = i;
			depth[i] = -1;
		}
	}

	public void setParent(int p, int child) {
		parent[child] = p;
	}

	public void calcDepth() {
		for (int i = 1; i <= N; ++i) {
			int now = i;
			if (depth[parent[now]] == -1) {
				int dd = 0;
				while (parent[now] != now) {
					now = parent[now];
					dd++;
				}
				depth[i] = dd;
			} else {
				depth[now] = depth[parent[now]] + 1;
			}
		}
	}

	public int find(int a, int b) {
		int aa = a, bb = b;
		if (depth[a] < depth[b]) {
			bb = a;
			aa = b;
		}

		int depthA = depth[aa];
		int depthB = depth[bb];

		while (depthA != depthB) {
			aa = parent[aa];
			depthA--;
		}

		if (aa == bb) {
			return aa;
		}
		while (aa != bb) {
			aa = parent[aa];
			bb = parent[bb];
		}
		return aa;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int T = Integer.parseInt(br.readLine());
		for (int l = 0; l < T; ++l) {
			int N = Integer.parseInt(br.readLine());

			BOJ_3584 main = new BOJ_3584(N);
			for (int i = 1; i < N; ++i) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				main.setParent(parent, child);
			}
			main.calcDepth();
			StringTokenizer st = new StringTokenizer(br.readLine());
			bw.write(main.find(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
			bw.flush();
		}
		bw.close();
		br.close();
	}
}
