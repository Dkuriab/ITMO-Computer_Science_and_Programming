import java.io.*;

public class G {

	public static boolean[][] visited;

	public static int[][] graphFirst;
	public static int[][] graphSecond;
	public static boolean[] endsFirst;
	public static boolean[] endsSecond;

	public static boolean compare(int u, int v) { 
		visited[u][v] = true;
			   
		if (endsFirst[u] != endsSecond[v])  {
	     	return false; 
	    }  
	   	boolean ans = true;
	   	for (int symbol = 80; symbol < 150; symbol++) {      
	     	int uStep = graphFirst[u][symbol];
	     	int vStep = graphSecond[v][symbol];

			if (!visited[uStep][vStep]) {
				if (!compare(uStep, vStep)) {
       				ans = false;   	
				}
	        }
       	}               
	         
	   	return ans;
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("equivalence.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("equivalence.out")));

			String[] parametrsFirst = in.readLine().split(" ");
			int nFirst = Integer.parseInt(parametrsFirst[0]); // number of points
			int mFirst = Integer.parseInt(parametrsFirst[1]); // number of roads
			int kFirst = Integer.parseInt(parametrsFirst[2]); // number of ends


// Reading Firrst DKA //
			graphFirst = new int[nFirst + 1][150];
			endsFirst = new boolean[nFirst + 1];

			String[] endsString = in.readLine().split(" ");
			for (int i = 0; i < kFirst; i++) {
				endsFirst[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < mFirst; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graphFirst[a][c + 0] = b;
			}
// Reading Firrst DKA //			


// Reading Second DKA //

			String[] parametrsSecond = in.readLine().split(" ");
			int nSecond = Integer.parseInt(parametrsSecond[0]); // number of points
			int mSecond = Integer.parseInt(parametrsSecond[1]); // number of roads
			int kSecond = Integer.parseInt(parametrsSecond[2]); // number of ends

			graphSecond = new int[nSecond + 1][150];
			endsSecond = new boolean[nSecond + 1];

			endsString = in.readLine().split(" ");
			for (int i = 0; i < kSecond; i++) {
				endsSecond[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < mSecond; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graphSecond[a][c + 0] = b;
			}
// Reading Second DKA //

			visited = new boolean[nFirst + 1][nSecond + 1];


			if (compare(1, 1)) {
				out.write("YES");
			} else {
				out.write("NO");
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}