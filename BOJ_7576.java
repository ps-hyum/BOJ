package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_7576 {
	
	static class Node {
		private int x;
		private int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int[] getXY() {
			int[] arr = { this.x, this.y };
			return arr;
		}
	}
	
	private int[][] map;
	private int[][] check;
	private int N;
	private int M;
	private Queue<Node> q;
	private int[] dx = {-1, 0, 0, 1};
	private int[] dy = {0, 1, -1, 0};
	private PriorityQueue<Integer> pq;
	private int ySum = 0;
	
	public BOJ_7576(int m, int n) {
		this.map = new int[n+1][m+1];
		this.check = new int[n+1][m+1];
		this.N = n;
		this.M = m;
		this.q = new LinkedList<Node>();
		this.pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return -1 * (o1 - o2);
			}
		});
	}
	
	public void setMap(int i, String str) {
		StringTokenizer st = new StringTokenizer(str);
		for(int j=1; j<=M; ++j) {
			this.map[i][j] = Integer.parseInt(st.nextToken());
			if(this.map[i][j] == -1) {
				this.check[i][j] = -1;
				this.ySum += j;
			}
			if(this.map[i][j] == 1) {
				this.q.add(new Node(i,j));
				this.check[i][j] = 1;
			}
		}
	}
	
	public int bfs() {
		
		if(q.isEmpty())
			return -1;
		
		while(!q.isEmpty()) {
			int[] nxy = q.poll().getXY();
			int nx = nxy[0];
			int ny = nxy[1];
			this.ySum += ny;
			
			for(int i=0; i<4; ++i) {
				int xx = nx + dx[i];
				int yy = ny + dy[i];
				if(0<xx && xx<=N && 0<yy && yy<=M) {
					if(check[xx][yy] == 0) {
						check[xx][yy] = check[nx][ny] + 1;
						q.add(new Node(xx, yy));
						pq.add(check[xx][yy]);
					}
				}
			}
		}

		for(int i=1; i<=N; ++i) {
			for(int j=1; j<=M; ++j) {
				System.out.printf("%2d ", check[i][j]);
			}
			System.out.println();
		}
		System.out.println(ySum);
		if(pq.isEmpty()) return 0;
		if(ySum != M*(M+1)/2 * N) return -1;
		else return pq.peek()-1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());

		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		
		BOJ_7576 main = new BOJ_7576(M, N);
		for(int i=1; i<=N; ++i) {
			main.setMap(i, br.readLine());
		}
		System.out.println(main.bfs());
	}
}