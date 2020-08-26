package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.Vector;

//boj1260_DFSnBFS
public class BOJ_1260 {
	
	private Vector<Vector<Integer>> tree;
	private int[] check;
	private Queue<Integer> checkBFS;
	private Queue<Integer> ans;
	private int N;
	
	public BOJ_1260(int N, int M) {
		this.N = N;
		tree = new Vector<Vector<Integer>>();
		for(int i=0; i<N+1; ++i)
			this.tree.add(new Vector<Integer>());
		this.check = new int[N+1];
		this.checkBFS = new LinkedList<Integer>();
		this.ans = new LinkedList<Integer>();
	}
	
	public void addEdge(int e1, int e2) {
		this.tree.get(e1).add(e2);
		this.tree.get(e2).add(e1);
	}
	
	public void dfs(int root) {
		if(check[root] == 1) return;
		
		this.check[root] = 1;
		this.ans.add(root);
		for(int i : tree.get(root)) {
			this.dfs(i);
		}
	}
	
	public void bfs(int root) {
		checkBFS.add(root);
		while(!checkBFS.isEmpty()) {
			int parent = checkBFS.poll();
			check[root] = 1;
			ans.add(parent);
			for (int i = 0; i < tree.get(parent).size(); ++i) {
				if(check[tree.get(parent).get(i)] != 1){
					check[tree.get(parent).get(i)] = 1;
					checkBFS.add(tree.get(parent).get(i));
				}
			}
		}
	}
	
	public Queue<Integer> solve(int root){
		for(int i=1; i<N+1; i++) {
			Collections.sort(tree.get(i));
		}
		this.dfs(root);
		Arrays.fill(check, 0);
		this.bfs(root);
		return this.ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int V = Integer.parseInt(st.nextToken());

		BOJ_1260 main = new BOJ_1260(N, M);
		for (int l = 0; l < M; l++) {
			st = new StringTokenizer(br.readLine());
			main.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		Queue<Integer> q = main.solve(V);
		int loop = q.size();
		for(int i=0; i<loop/2; ++i) {
			System.out.print(q.poll() + " ");
		}
		System.out.println();
		for(int i=0; i<loop/2; ++i) {
			System.out.print(q.poll() + " ");
		}
	}
}