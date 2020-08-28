package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_14002 {

	private static int N;
	private static int max;
	private static int idx;
	private static int[] dp;
	private static int[] before;
	private static ArrayList<Integer> arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		dp = new int[N];
		before = new int[N];
		arr = new ArrayList<Integer>();

		st = new StringTokenizer(br.readLine());
		arr.add(Integer.parseInt(st.nextToken()));
		dp[0] = max = 1;
		before[0] = idx = 0;
		for (int i = 1; i < N; ++i) {
			int now = Integer.parseInt(st.nextToken());
			arr.add(now);
			caclDP(now, i);
		}

		Stack<Integer> q = new Stack<Integer>();
		while (true) {
			q.add(arr.get(idx));
			if (idx == before[idx])
				break;
			idx = before[idx];
		}
		sb.append(max).append('\n');
		while (!q.isEmpty()) {
			sb.append(q.pop()).append(" ");
		}
		System.out.println(sb);

		bw.close();
		br.close();
	}

	public static void caclDP(int now, int index) {
		for (int i = index - 1; i >= 0; --i) {
			if (now > arr.get(i)) {
				if (dp[index] < dp[i] + 1) {
					dp[index] = dp[i] + 1;
					before[index] = i;
					if (dp[index] > max) {
						idx = index;
						max = dp[index];
					}
				}
			}
		}
		if (dp[index] == 0) {
			dp[index] = 1;
			before[index] = index;
		}
	}
}