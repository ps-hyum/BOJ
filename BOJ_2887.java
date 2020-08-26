package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_2887 {

	static class Edge {
		private int x, y, z;

		public Edge(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}
	}

	static class Node implements Comparable<Node> {
		private int from, to, weight;

		public Node(int f, int t, int w) {
			this.from = f;
			this.to = t;
			this.weight = w;
		}

		public Node(int num, int xyz) {
			this.weight = xyz;
			this.from = num;
		}

		public int getFrom() {
			return from;
		}

		public int getTo() {
			return to;
		}

		public int getWeight() {
			return weight;
		}

		public int getNum() {
			return from;
		}

		public int getXYZ() {
			return weight;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.weight - o.getWeight();
		}
	}

	private int N;
	private Edge[] arrE;
	private ArrayList<Node> arrX;
	private int[] x2serial;
	private ArrayList<Node> arrY;
	private int[] y2serial;
	private ArrayList<Node> arrZ;
	private int[] z2serial;
	private int[] parent;
	private PriorityQueue<Node> pq;
	private int sum = 0;
	private int loop = 0;

	public BOJ_2887(int N) {
		this.N = N;
		arrE = new Edge[N];
		arrX = new ArrayList<Node>();
		x2serial = new int[N];
		arrY = new ArrayList<Node>();
		y2serial = new int[N];
		arrZ = new ArrayList<Node>();
		z2serial = new int[N];
		parent = new int[N];
		pq = new PriorityQueue<Node>();
	}

	public int find(int a) {
		if (a == parent[a])
			return a;

		parent[a] = find(parent[a]);
		return parent[a];
	}

	public void union(int a, int b) {
		int pa = this.find(a);
		int pb = this.find(b);

		if (pa == pb)
			return;
		parent[pb] = a;
	}

	public boolean chkCycle(int a, int b) {
		int pa = find(a);
		int pb = find(b);

		if (pa == pb)
			return true;

		this.union(a, b);
		this.loop++;
		return false;
	}

	public void setXYZ(int i, int x, int y, int z) {
		parent[i] = i;
		arrE[i] = new Edge(x, y, z);
		arrX.add(new Node(i, x));
		arrY.add(new Node(i, y));
		arrZ.add(new Node(i, z));
	}

	public void getSerial() {
		Collections.sort(arrX);
		Collections.sort(arrY);
		Collections.sort(arrZ);
		for (int j = 0; j < N; ++j) {
			this.x2serial[arrX.get(j).getNum()] = j;
			this.y2serial[arrY.get(j).getNum()] = j;
			this.z2serial[arrZ.get(j).getNum()] = j;
		}
	}

	public void prim(int from) {
		int[] arr;
		for (int i = 0; i < 3; ++i) {
			for (int j = -1; j < 2; j += 2) {
				arr = this.getClosest(i, j, from);
				if (arr == null)
					continue;
				pq.add(new Node(from, arr[0], arr[1]));
			}
		}

		while (loop != N - 1) {
			Node e = pq.poll();
			if (chkCycle(e.getFrom(), e.getTo()))
				continue;
			sum += e.getWeight();

			int newfrom = e.getTo();
			for (int i = 0; i < 3; ++i) {
				for (int j = -1; j < 2; j += 2) {
					arr = this.getClosest(i, j, newfrom);
					if (arr == null)
						continue;
					pq.add(new Node(newfrom, arr[0], arr[1]));
				}
			}
		}
		System.out.println(sum);
	}

	public int[] getClosest(int flag1, int flag2, int from) {
		int[] arr = new int[2];
		int tmp;
		switch (flag1) {
		// x
		case 0: {
			tmp = x2serial[from];
			if ((tmp + flag2) < 0 || (tmp + flag2) >= N)
				return null;
			arr[0] = arrX.get(tmp + flag2).getNum();
			arr[1] = Math.abs(arrE[from].getX() - arrE[arr[0]].getX());
			return arr;
		}
		// y
		case 1: {
			tmp = y2serial[from];
			if ((tmp + flag2) < 0 || (tmp + flag2) >= N)
				return null;
			arr[0] = arrY.get(tmp + flag2).getNum();
			arr[1] = Math.abs(arrE[from].getY() - arrE[arr[0]].getY());
			return arr;
		}
		// z
		case 2: {
			tmp = z2serial[from];
			if ((tmp + flag2) < 0 || (tmp + flag2) >= N)
				return null;
			arr[0] = arrZ.get(tmp + flag2).getNum();
			arr[1] = Math.abs(arrE[from].getZ() - arrE[arr[0]].getZ());
			return arr;
		}
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		BOJ_2887 main = new BOJ_2887(N);

		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			main.setXYZ(i, x, y, z);
		}

		main.getSerial();
		main.prim(0);
		br.close();
	}
}