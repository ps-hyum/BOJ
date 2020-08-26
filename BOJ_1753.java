package pp.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class BOJ_1753 {
	
	private int V;
	private int E;
	private int K;
	private ArrayList<HashMap<Integer, Integer>> ah;
	private int[] arr;
	private int[] check;
	private int sum;
	private TreeMap<Integer, Integer> tm;
	
	public BOJ_1753(int v, int e, int k) {
		this.V = v;
		this.E = e;
		this.K = k;
		ah = new ArrayList<HashMap<Integer, Integer>>();
		ah.add(null);
		for(int i=0; i<V; ++i) {
			ah.add(new HashMap<Integer, Integer>());
		}
		arr = new int[V+1];
		check = new int[V+1];
		this.sum = 0;
		Arrays.fill(arr, Integer.MAX_VALUE);

		tm = new TreeMap<Integer, Integer>();
	}
	
	public void setGraph(int u, int v, int w) {
		if(ah.get(u).get(v) == null)
			ah.get(u).put(v, w);
		if(ah.get(u).get(v) > w)
			ah.get(u).put(v, w);
	}
	
	public void dijkstra() {
		int now = K;
		sum++;
		arr[K] = 0;
		check[K] = 1;
		
		while(sum != V) {
			this.update(now);
			now = this.getNow();
		}
		for(int i=1; i<=V; ++i) {
			if(arr[i] != Integer.MAX_VALUE)
				System.out.println(arr[i]);
			else
				System.out.println("INF");
		}
	}
	
	public void update(int i) {
		HashMap<Integer, Integer> hm = ah.get(i);
		if(hm == null)
			return;
		
		for(Entry<Integer, Integer> e : hm.entrySet()) {
			int u = i;
			int v = e.getKey();
			int w = e.getValue();
			arr[v] = Math.min(arr[v], arr[u] + w);
		}
	}
	
	public int getNow() {
		int minI = 0;
		int minV = Integer.MAX_VALUE;
		for(int i=1; i<=V; ++i) {
			if(check[i] == 0) {
				if(minV > arr[i]) {
					minI = i;
					minV = arr[i];
				}
			}
		}
		check[minI] = 1;
		sum++;
		return minI;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(br.readLine());
		
		BOJ_1753 main = new BOJ_1753(V, E, K);
		
		for(int i=0; i<E; ++i) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			main.setGraph(u, v, w);
		}
		main.dijkstra();
	}
}