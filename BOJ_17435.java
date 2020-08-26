package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_17435 {

	public int[][] table;
	public int m;

	public BOJ_17435(int M) {
		this.m = M;
		table = new int[19][m + 1];
	}

	public void setFunc(int i, int to) {
		table[0][i] = to;
	}

	public void setSparceTable() {
		for (int k = 1; k < 19; ++k) {
			for (int i = 1; i <= m; ++i) {
				table[k][i] = table[k - 1][table[k - 1][i]];
			}
		}
	}

	public int query(int n, int x) {
		int num = n;
		int tmp = x;
		for (int i = 18; i >= 0; --i) {
			if (num == 0)
				break;
			int n2 = 1 << i;
			if ((num & n2) != 0) {
				tmp = table[i][tmp];
				num -= n2;
			}
		}
		return tmp;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int m = Integer.parseInt(br.readLine());
		BOJ_17435 main = new BOJ_17435(m);

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= m; ++i) {
			main.setFunc(i, Integer.parseInt(st.nextToken()));
		}

		int q = Integer.parseInt(br.readLine());
		main.setSparceTable();
		for (int i = 1; i <= q; ++i) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			bw.write(main.query(u, v) + "\n");
			bw.flush();
		}
		bw.close();
		br.close();
	}
}