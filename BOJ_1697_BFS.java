package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1697_BFS {

	static class Node {
		private int num;
		private int minLen;

		public Node(int num, int minLen) {
			this.num = num;
			this.minLen = minLen;
		}

		public int getNum() {
			return num;
		}

		public int getMinLen() {
			return minLen;
		}
	}

	private int N;
	private int K;
	private Queue<Node> q;
	private int[] check;

	public BOJ_1697_BFS(int n, int k) {
		this.N = n;
		this.K = k;
		this.q = new LinkedList<Node>();
		this.check = new int[K + 3];
	}

	public int bfs() {
		if(N >= K)
			return N-K;
		
		q.add(new Node(N, 0));

		while (!q.isEmpty()) {
			Node node = q.poll();
			int now = node.getNum();
			int len = node.getMinLen();
			check[now] = 1;
			if(now == K) {
				return len;
			}
			
			for (int i = 0; i < 3; ++i) {
				int next = this.calc(i, now);
				
				if(next <= K+2 && next >= 0 && check[next] == 0) {
					q.add(new Node(next, len+1));
				}
			}
		}
		return 0;
	}

	public int calc(int i, int now) {
		switch (i) {
		case 0:
			return now - 1;
		case 1:
			return now + 1;
		case 2:
			return 2 * now;
		}
		return 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		BOJ_1697_BFS main = new BOJ_1697_BFS(N, K);
		System.out.println(main.bfs());
	}
}