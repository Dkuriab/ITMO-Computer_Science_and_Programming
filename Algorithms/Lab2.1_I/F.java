import java.io.*;
import java.util.*;

public class F {

    public static ArrayList<HashSet<Integer>> graph = new ArrayList<>();
    public static ArrayList<HashSet<Integer>> graphTr = new ArrayList<>();
    public static ArrayList<HashSet<Integer>> graphCond = new ArrayList<>();
    public static LinkedList<Integer> queue = new LinkedList<>();
    public static boolean[] used;
    public static int[] components;
    public static int curComponent = 0;

    public static void dfs(int node) {
        used[node] = true;
        for (int i : graph.get(node)) {
            if (!used[i]) {
                dfs(i);
            }
        }
        queue.add(node);
    }

    public static void dfsTr(int node) {
        used[node] = true;
        components[node] = curComponent;
        for (int i : graphTr.get(node)) {
            if (!used[i]) {
                dfsTr(i);
            }
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String[] data = in.readLine().split(" ");

            int n = Integer.parseInt(data[0]);
            int m = Integer.parseInt(data[1]);
            components = new int[n];
            used = new boolean[n];

            for (int i = 0; i < n; i++) {
                graph.add(new HashSet<>());
                graphTr.add(new HashSet<>());
            }

            for (int i = 0; i < m; i++) {

                String[] edge = in.readLine().split(" ");
                int from = Integer.parseInt(edge[0]) - 1;
                int to = Integer.parseInt(edge[1]) - 1;

                graph.get(from).add(to);
                graphTr.get(to).add(from);
            }

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    dfs(i);
                }
            }

            used = new boolean[n];
            Collections.reverse(queue);
            for (int i : queue) {
                if (!used[i]) {
                    dfsTr(i);
                    curComponent++;
                }
            }

            for (int i = 0; i < curComponent; i++) {
                graphCond.add(new HashSet<Integer>());
            }

            int answer = 0;
            for (int i = 0; i < n; i++) {
                int from = components[i];
                for (int j : graph.get(i)) {
                    int to = components[j];
                    if (to != from && graphCond.get(from).add(to)) {
                        answer++;
                    }
                }
            }

            out.write(answer + "");
            out.newLine();

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
