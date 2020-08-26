package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_2263 {

	private int n;
	private int[] inorder;
	private int[] postorder;

	public BOJ_2263(int n) {
		this.n = n;
		this.inorder = new int[n + 1];
		this.postorder = new int[n + 1];
	}

	public void getInOrder(String str) {
		StringTokenizer st = new StringTokenizer(str);
		for (int i = 1; i <= n; ++i)
			inorder[Integer.parseInt(st.nextToken())] = i;
	}

	public void getPostOrder(String str) {
		StringTokenizer st = new StringTokenizer(str);
		for (int i = 1; i <= n; ++i)
			postorder[i] = Integer.parseInt(st.nextToken());
	}

	void preOrder(int is, int ie, int ps, int pe) {
		if (is > ie || ps > pe)
			return;
		int root = postorder[pe];
		System.out.print(root + " ");
		int ir = inorder[root];
		int left = ir - is;
		preOrder(is, ir - 1, ps, ps + left - 1);
		preOrder(ir + 1, ie, ps + left, pe - 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		BOJ_2263 main = new BOJ_2263(n);

		main.getInOrder(br.readLine());
		main.getPostOrder(br.readLine());
		main.preOrder(1, n, 1, n);

		br.close();
	}
}