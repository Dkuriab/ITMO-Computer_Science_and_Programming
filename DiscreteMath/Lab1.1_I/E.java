import java.util.*;

public class E {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        int[] elem = new int[n + 1];		//element 0 ili ne 0
        int[][] bound = new int[n + 1][];	//c chem sviazan
        int[][] table = new int[n + 1][];	//tabliza istinnpsti
        int[] level = new int[n + 1];		//uroven elementa
        int[] now = new int[n + 1];
        int power = 0;						//kol-vo peremennix
        int frog = 0;						//tmp

        for (int i = 1; i <= n; i++) {
        	elem[i] = scan.nextInt();
        	if (elem[i] == 0) {
        		power++;
        	} else {
        		bound[i] = new int[elem[i]];
        		for (int j = 0; j < elem[i]; j++) {
        			bound[i][j] = scan.nextInt();
        		}
        		frog = 1 << elem[i];
        		table[i] = new int[frog];
        		for (int j = 0; j < frog; j++) {
        			table[i][j] = scan.nextInt();
        		}
        	}
        }
        for (int i = 1; i <= n; i++) {
        	if (elem[i] == 0) {
        		level[i] = 0;
        	} else {
        		int max = 0;
        		for (int j = 0; j < elem[i]; j++) { //poisk max yrovnia predidushego
        			if (level[bound[i][j]] > max) {
        				max = level[bound[i][j]];
        			}
        		}
        		level[i] = max + 1;
        	}
        }
        
        System.out.println(level[n]);

        for (int i = 0; i < (1 << power); i++) {
        	int naw = i;
        	for (int t = n; t >= 1; t--) {
        		if (elem[t] == 0) {
        			now[t] = naw % 2;
        			naw >>= 1;
        		} 
        	}
        	for (int t = 1; t <= n; t++) {
        		if (elem[t] != 0) {
        			int tmp = 0;
        			for (int y = 0; y < elem[t]; y++) { 
        				if (now[bound[t][y]] == 1) {
        					tmp += (1 << (elem[t] - 1 - y));	
        				}	
        			}
        			now[t] = table[t][tmp];
        		}
        	}
        	System.out.print(now[n]);
        }
    }
}