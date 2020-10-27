import java.util.*;
import java.io.*;

public class G {

    public static ArrayList<HashSet<Integer>> graph = new ArrayList<>();
    public static ArrayList<HashSet<Integer>> graphTr = new ArrayList<>();
    // public static ArrayList<HashSet<Integer>> graphCond = new ArrayList<>();
    public static LinkedList<Integer> queue = new LinkedList<>();
    public static boolean[] used;
    public static int n;
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

    public static int non(int node) {
        return (node + n) % (2 * n);
    }

    public static void main(String[] args) {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String[] data = in.readLine().split(" ");

            n = Integer.parseInt(data[0]);
            int m = Integer.parseInt(data[1]);
            components = new int[2 * n];
            used = new boolean[2 * n];

            for (int i = 0; i < 2 * n; i++) {
                graph.add(new HashSet<>());
                graphTr.add(new HashSet<>());
            }

            HashMap<Integer, String> names = new HashMap<>();
            HashMap<String, Integer> numbers = new HashMap<>();

            for (int i = 0; i < n; i++) {
                String name = in.readLine();
                names.put(i, name);
                numbers.put(name, i);
            }       

            for (int u = 0; u < m; u++) {
                String[] rule = in.readLine().split(" ");

                int i = numbers.get(rule[0].substring(1));
                if (rule[0].charAt(0) == '-') {
                    i = non(i);
                }

                int j = numbers.get(rule[2].substring(1));
                if (rule[2].charAt(0) == '-') {
                    j = non(j);
                }

                graph.get(i).add(j);
                graph.get(non(j)).add(non(i));

                graphTr.get(j).add(i);
                graphTr.get(non(i)).add(non(j));
            }

            for (int i = 0; i < 2 * n; i++) {
                if (!used[i]) {
                    dfs(i);
                }
            }

            used = new boolean[2 * n];
            Collections.reverse(queue);
            for (int i : queue) {
                if (!used[i]) {
                    dfsTr(i);
                    curComponent++;
                }
            }

            boolean flag = true;

            for (int i = 0; i < 2 * n; i++) {
                if (components[i] == components[non(i)]) {
                    flag = false;
                }
            }
            HashSet<String> answer = new HashSet<>();

            if (flag) {
                for (int i = 0; i < n; i++) {
                    // System.out.println(names.get(i) + " in : " + components[i] + " non in: " + components[non(i)]);
                    if (components[i] > components[non(i)]) {
                        answer.add(names.get(i));
                    }     
                }

                out.write(answer.size() + "");
                out.newLine();
                for (String i : answer) {
                    out.write(i);
                    out.newLine();
                }
            }
            else {
                out.write("-1");
            }
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}