package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2178 {

	private int[][] map;
	private int[][] check;
	private int N, M;
	private int[] dx = { -1, 0, 0, 1 };
	private int[] dy = { 0, 1, -1, 0 };
	private Queue<Node> q;

	static class Node {
		private int x;
		private int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int[] getDATA() {
			int[] arr = new int[2];
			arr[0] = x;
			arr[1] = y;
			return arr;
		}
	}

	public BOJ_2178(int n, int m) {
		map = new int[n + 1][m + 1];
		check = new int[n + 1][m + 1];
		N = n;
		M = m;
		q = new LinkedList<Node>();
	}
	
	public void setMap(int i, String str) {
		for(int j=1; j<=M; ++j) {
			map[i+1][j] = str.charAt(j-1) - '0';
		}
	}

	public int bfs(int x, int y) {
		q.add(new Node(x,y));
		
		while(!q.isEmpty()) {
			int[] nxy = q.poll().getDATA();
			int nx = nxy[0];
			int ny = nxy[1];

			for(int i=0; i<4; ++i) {
				int xx = nx + dx[i];
				int yy = ny + dy[i];
				
				if(0<xx && xx <=N && 0<yy && yy<=M) {
					if(map[xx][yy] == 1) {
						if(check[xx][yy] == 0) {
							check[xx][yy] = check[nx][ny] + 1;
							q.add(new Node(xx, yy));
						}
//						if(check[xx][yy] != 0) {
//							check[xx][yy] = Math.min(check[nx][ny]+1, check[xx][yy]);
//						}						
					}
				}
			}
		}
		return check[N][M] + 1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		BOJ_2178 main = new BOJ_2178(N, M);
		for(int i=0; i<N; ++i) {
			main.setMap(i, br.readLine());
		}
		System.out.println(main.bfs(1, 1));
	}
}
