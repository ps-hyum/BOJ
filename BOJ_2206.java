package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2206 {

	static class Node {
		private int x;
		private int y;
		private int Len;
		// cOne = -1 : 1을 방문한 루트
		// cOne = 1 : 0만 방문한 루트
		private int cOne;

		public Node(int x, int y, int Len, int cOne) {
			this.x = x;
			this.y = y;
			this.Len = Len;
			// cOne = -1 : 1을 방문한 노드
			// cOne = 1 : 0만 방문한 노드
			this.cOne = cOne;
		}

		public int[] getData() {
			int[] arr = new int[4];
			arr[0] = this.x;
			arr[1] = this.y;
			arr[2] = this.Len;
			arr[3] = this.cOne;
			return arr;
		}
	}

	private int N;
	private int M;
	private int[][] map;
	private Queue<Node> q;
	private int[][] check;
	private int[] dx = { -1, 0, 0, 1 };
	private int[] dy = { 0, -1, 1, 0 };

	public BOJ_2206(int N, int M) {
		this.N = N;
		this.M = M;
		this.map = new int[N + 1][M + 1];
		this.check = new int[N + 1][M + 1];
		this.q = new LinkedList<Node>();
	}

	public void setMap(int i, String str) {
		for (int j = 0; j < M; ++j) {
			map[i][j + 1] = str.charAt(j) - '0';
		}
	}

	public int bfs() {
		q.add(new Node(1, 1, 1, 1));
		check[1][1] = 1;

		while (!q.isEmpty()) {
			int[] data = q.poll().getData();
			int nx = data[0];
			int ny = data[1];
			int nlen = data[2];
			int ncOne = data[3];
//			}
			if (nx == N && ny == M) {
				return nlen;
			}

			for (int i = 0; i < 4; ++i) {
				int xx = nx + dx[i];
				int yy = ny + dy[i];
				int llen = nlen + 1;

				if (0 < xx && xx <= N && 0 < yy && yy <= M) {
					if(ncOne == -1) {
						if(map[xx][yy] == 1) {
							//do nothing
						}
						else {
							if (check[xx][yy] == 0) {
								check[xx][yy] = -1;
								q.add(new Node(xx, yy, llen, -1));
							} 
							else if (check[xx][yy] == 1) {
								// do nothing
							}
							// check[xx][yy] = -1
							else {
								// do nothing
							}
						}
					}
					//ncOne == 1
					else {
						if(map[xx][yy] == 0) {
							if(check[xx][yy] == 0) {
								check[xx][yy] = 1;
								q.add(new Node(xx, yy, llen, 1));
							}
							else if(check[xx][yy] == 1) {
								//do nothing
							}
							//check[xx][yy] = -1
							else {
								if(map[xx][yy] == 0) {
									check[xx][yy] = 1;
									q.add(new Node(xx, yy, llen, 1));
								}
							}
						}
						else {
							if(check[xx][yy] == 0) {
								check[xx][yy] = -1;
								q.add(new Node(xx, yy, llen, -1));
							}
							else if(check[xx][yy] == 1) {
								//do nothing
							}
							else {
								if(map[xx][yy] == 0) {
									check[xx][yy] = 1;
									q.add(new Node(xx, yy, llen, 1));
								}
							}
						}
					}
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		BOJ_2206 main = new BOJ_2206(N, M);
		for (int i = 1; i <= N; ++i) {
			main.setMap(i, br.readLine());
		}
		System.out.println(main.bfs());
	}
}