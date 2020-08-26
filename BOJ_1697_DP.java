package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1697_DP {
	private int N;
	private int K;
	private int[] dp;

	public BOJ_1697_DP(int n, int k) {
		this.N = n;
		this.K = k;
		if (k != 0)
			dp = new int[k + 2];
	}

	public void solveDP() {
		if (N >= K) {
			System.out.println(N - K);
			return;
		}

		dp[N] = 0;
		if (N == 0)
			dp[N + 1] = 1;
		else
			dp[N - 1] = dp[N + 1] = 1;

		int idx = N - 2, tmp = 2;
		while (idx >= 0) {
			dp[idx] = tmp;
			idx--;
			tmp++;
		}

		for (int i = N + 2; i <= K + 1; i++) {
			if (i % 2 == 0)
				dp[i] = Math.min(dp[i - 1] + 1, dp[i / 2] + 1);
			else
				dp[i] = dp[i - 1] + 1;

			dp[i - 1] = Math.min(dp[i - 1], dp[i] + 1);
		}

		System.out.println(dp[K]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		BOJ_1697_DP main = new BOJ_1697_DP(N, K);
		main.solveDP();
	}
}
