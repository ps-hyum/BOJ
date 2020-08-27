package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_4195 {

	private int N;
	private Map<String, Integer> Name;
	private int index;
	private int[] parent;
	private int[] network;

	public BOJ_4195(int n) {
		this.N = n;
		Name = new HashMap<String, Integer>();
		index = 0;
		parent = new int[2 * N + 1];
		for (int i = 1; i <= 2 * N; ++i)
			parent[i] = i;
		network = new int[2 * N + 1];
	}

	public void addNetwork(String str) {
		StringTokenizer st = new StringTokenizer(str);

		String fa = st.nextToken();
		String fb = st.nextToken();
		System.out.println(union(checkName(fa), checkName(fb)));
	}

	public int checkName(String str) {
		if (Name.get(str) != null)
			return Name.get(str);
		else {
			network[index] = 1;
			Name.put(str, index++);
			return index - 1;
		}
	}

	public int union(int a, int b) {
		int pa = find(a);
		int pb = find(b);
		if (pa == pb) {
			return network[pb];
		}
		parent[pb] = a;
		network[pa] += network[pb];
		return network[pa];
	}

	public int find(int a) {
		if (a != parent[a]) {
			return find(parent[a]);
		}
		return parent[a];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());

		for (int l = 0; l < T; ++l) {
			int F = Integer.parseInt(br.readLine());
			BOJ_4195 main = new BOJ_4195(F);
			for (int i = 0; i < F; ++i) {
				main.addNetwork(br.readLine());
			}
		}

		bw.close();
		br.close();
	}
}