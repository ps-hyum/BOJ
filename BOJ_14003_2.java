package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_14003_2 {

	static class Node {
		private int num, index;

		public Node(int num, int index) {
			this.num = num;
			this.index = index;
		}

		public int getNum() {
			return num;
		}

		public int getIndex() {
			return index;
		}
	}

	private static int N;
	private static int[] prev;
	private static ArrayList<Node> lis;
	private static int[] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());

		prev = new int[N];
		arr = new int[N];
		lis = new ArrayList<Node>();

		int now = Integer.parseInt(st.nextToken());
		lis.add(new Node(now, 0));
		arr[0] = now;
		for (int i = 1; i < N; ++i) {
			now = Integer.parseInt(st.nextToken());
			arr[i] = now;
			LIS(now, i);
		}
		sb.append(lis.size()).append('\n');
		Stack<Integer> stk = getAns();
		while (!stk.isEmpty()) {
			sb.append(stk.pop()).append(" ");
		}
		System.out.println(sb.toString());

		bw.close();
		br.close();
	}

	public static Stack<Integer> getAns() {
		Stack<Integer> stk = new Stack<Integer>();

		int idx = lis.get(lis.size() - 1).getIndex();
		stk.push(arr[idx]);
		while (idx != prev[idx]) {
			idx = prev[idx];
			stk.push(arr[idx]);
		}
		return stk;
	}

	public static void LIS(int now, int i) {
		int n = lis.size();
		if (lis.get(n - 1).getNum() < now) {
			prev[i] = lis.get(n - 1).getIndex();
			lis.add(new Node(now, i));
			return;
		} else {
			int lb = getLowerBound(0, n - 1, now);
			if (lb == 0) {
				prev[i] = i;
				lis.set(lb, new Node(now, i));
				return;
			}
			prev[i] = lis.get(lb - 1).getIndex();
			lis.set(lb, new Node(now, i));
		}
	}

	public static int getLowerBound(int left, int right, int now) {

		while (left < right) {
			int mid = (left + right) / 2;

			if (lis.get(mid).getNum() >= now) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return right;
	}
}