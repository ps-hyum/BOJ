package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class BOJ_2667 {

	private int[][] map;
	private int n;
	private int dx[] = { -1, 0, 0, 1 };
	private int dy[] = { 0, 1, -1, 0 };
	private PriorityQueue<Integer> pq;
	private int count;

	public BOJ_2667(int n) {
		this.n = n;
		map = new int[n][n];
		pq = new PriorityQueue<Integer>();
		this.count = 0;
	}

	public PriorityQueue<Integer> getPq() {
		return pq;
	}

	public void setMap(int l, String str) {
		for (int i = 0; i < n; ++i) {
			map[l][i] = str.charAt(i) - '0';
		}
	}

	public void dfs(int x, int y) {
		if (map[x][y] == 0)
			return;

		if (map[x][y] == 1) {
			map[x][y] = 0;
			count++;
		}

		for (int i = 0; i < 4; ++i) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if (0 <= nx && nx < n && 0 <= ny && ny < n)
				if (map[nx][ny] == 1)
					dfs(nx, ny);

		}
	}

	public void setCount() {
		this.count = 0;
	}

	public void addPQ() {
		if (this.count != 0) {
			this.pq.add(this.count);
			this.setCount();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());

		BOJ_2667 main = new BOJ_2667(n);
		for (int i = 0; i < n; ++i) {
			main.setMap(i, br.readLine());
		}

		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				main.dfs(i, j);
				main.addPQ();
			}
		}
		PriorityQueue<Integer> pq = main.getPq();
		int size = pq.size();
		System.out.println(size);
		for (int i = 0; i < size; ++i) {
			System.out.println(pq.poll());
		}
	}
}