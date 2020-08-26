package pp.boj;

import java.io.*;
import java.util.*;

public class BOJ_15681 {

static class Node {
	private int num;
	private ArrayList<Node> children;
	private int weight;
	
	public Node(int num) {
		this.num = num;
		this.children = new ArrayList<Node>();
		this.weight = 0;
	}
	
	public void addChild(Node e) {
		children.add(e);
	}
	
	public int getNum() {
		return num;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public void setWeight(int w) {
		weight += w;
	}
	
	public int getWeight() {
		return weight;
	}
}

private ArrayList<ArrayList<Integer>> treemap;
private int[] visited;
private int N;
private Node root;
private int rootNum;
private int[] ans;

public BOJ_15681(int N, int R) {
	this.N = N;
	this.visited = new int[N+1];
	this.root = new Node(R);
	this.rootNum = R;
	this.treemap = new ArrayList<ArrayList<Integer>>();
	for(int i=0; i<=N; ++i)
		this.treemap.add(new ArrayList<Integer>());
	this.visited[rootNum] = 1;
	this.ans = new int[N+1];
}

public void setTreeMap(int u, int v) {
	this.treemap.get(u).add(v);
	this.treemap.get(v).add(u);
}

public void makeTree(Node root) {
	for(int childNum : treemap.get(root.getNum())) {
		if(visited[childNum] == 0) {
			visited[childNum] = 1;
			Node child = new Node(childNum);
			root.addChild(child);
			makeTree(child);
		}
	}
}

public int calcWeight(Node e) {
	if(e.getChildren().size() == 0) {
		ans[e.getNum()] = e.getWeight();
		return e.getWeight();
	}
	for(int i=0; i<e.getChildren().size(); ++i) {
		e.setWeight(calcWeight(e.getChildren().get(i)) + 1);			
	}
	ans[e.getNum()] = e.getWeight();
	return e.getWeight();
}

public void solve() {
	this.makeTree(root);
	this.calcWeight(root);
}

public void getAns(int q) {
	System.out.println(ans[q] + 1);
}

public static void main(String[] args) throws IOException {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	StringTokenizer st = new StringTokenizer(br.readLine());
	
	int N = Integer.parseInt(st.nextToken());
	int R = Integer.parseInt(st.nextToken());
	int Q = Integer.parseInt(st.nextToken());
	BOJ_15681 main = new BOJ_15681(N, R);
	
	for(int i=1; i<N; ++i) {
		st = new StringTokenizer(br.readLine());
		int U = Integer.parseInt(st.nextToken());
		int V = Integer.parseInt(st.nextToken());
		main.setTreeMap(U, V);
	}
	main.solve();
	for(int i=0; i<Q; ++i) {
		int q = Integer.parseInt(br.readLine());
		main.getAns(q);
	}

	br.close();
}
}