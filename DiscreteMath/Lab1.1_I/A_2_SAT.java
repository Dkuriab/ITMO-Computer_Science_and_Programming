import java.util.*;

public class A_2_SAT {
    public static boolean[][] graf;
    public static boolean[] use;
    public static int[] components;
    public static int component;    
    public static int xes;
    public static ArrayList<Integer> nodes = new ArrayList<>(); 

    public static void dfs(int node) { //обход графа в глубину
        use[node] = true;
        for (int i = 1; i <= xes; i++) {
            if (!use[i] && graf[node][i]) {
                dfs(i);
            }
        }
        nodes.add(node);
    }

    public static void dfsTr(int node) { // обход транспонированного графа в глубину
        if (components[node] == 0) {
            components[node] = component;
            for (int i = 1; i <= xes; i++) {
                if (components[i] == 0 && graf[i][node]) {
                    dfsTr(i);
                }
            }
        }
    }    

    public static int non(int ij) { // отрицание переменной
        if (ij > xes / 2) {
            return ij - (xes / 2);
        }
        else {
            return ij + (xes / 2);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        xes = 2 * scan.nextInt();
        int m = scan.nextInt();
        
        use = new boolean[xes + 1];
        graf = new boolean[xes + 1][xes + 1];
        component = 1;
        components = new int[xes + 1];        

        for (int r = 0; r < m; r++) {
            int i = scan.nextInt();
            if (i < 0) {
                i = -i + xes / 2;
            }
            int j = scan.nextInt();
            if (j < 0) {
                j = -j + xes / 2;
            }
            graf[non(i)][j] = true;
            graf[non(j)][i] = true;
        }

        for (int i = 1; i <= xes; i++) {
            if (!use[i]) {
                dfs(i);       
            }
        }

        for (int i = nodes.size() - 1; i >= 0; i--) {
            dfsTr(nodes.get(i));
            component++;
        }

        boolean flag = false; 
        for (int i = 1; i <= xes / 2; i++) {
            if (components[i] == components[non(i)]) {
                flag = true;
            }
        }

        if (flag) {
            System.out.println("Yes");
        }
        else {
            System.out.println("No");
        }

        component = 0;
        xes = 0;
    }
}