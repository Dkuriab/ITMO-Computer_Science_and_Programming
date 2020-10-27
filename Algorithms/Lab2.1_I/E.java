import java.io.*;
import java.util.*;

public class E {

    public static ArrayList<HashSet<Road>> graph = new ArrayList<>();
    public static TreeMap<String, ArrayList<Integer>> repeatFamily = new TreeMap<>();
    public static HashSet<Integer> repeat = new HashSet<>();
    public static String[] g;
    public static boolean[] used;
    public static int[] lowestUp;
    public static int[] colors;
    public static int[] inTime;
    public static int curColor = 0;
    public static int timer = 0;

    public static class Road {
        public int to;
        public int number;
        public Road(int to, int number) {
            this.to = to;
            this.number = number;
        }

        @Override
        public int hashCode() {
            return to;
        }

        @Override
        public boolean equals(Object b) {
            if (this == b) {
                return true;
            }
            if (b == null) {
                return false;
            }
            return hashCode() == ((Road)b).hashCode();
        }
    }

    public static String formatRoad(int from, int to) {
        return from < to ? from + " " + to : to + " " + from;
    }

    public static void dfs(int node, int parent) {
        used[node] = true;
        inTime[node] = timer;
        lowestUp[node] = timer;
        timer++;
        for (Road r : graph.get(node)) {
            int u = r.to;
            if (used[u] && u != parent) {
                lowestUp[node] = Math.min(lowestUp[node], inTime[u]);
            }
            if (!used[u]) {
                dfs(u, node);
                lowestUp[node] = Math.min(lowestUp[node], lowestUp[u]);
            }
        }
    }

    public static void paint(int node, int color, int parent) {
        used[node] = true;
        for (Road r : graph.get(node)) {
            int u = r.to;
            int number = r.number;

            if (u != parent) {
                if (!used[u]) {
                    if (lowestUp[u] >= inTime[node]) {
                        colors[number] = ++curColor;
                        paint(u, curColor, node);
                    } else {
                        colors[number] = color;
                        paint(u, color, node);
                    }
                } else if (inTime[u] < inTime[node]) {
                    colors[number] = color;
                }

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

            for (int i = 0; i < n; i++) {
                graph.add(new HashSet<>());
            }

            used = new boolean[n];
            inTime = new int[n];
            lowestUp = new int[n];
            colors = new int[m];
            g = new String[m];


            HashMap<String, Integer> num = new HashMap<>();

            for (int i = 0; i < m; i++) {
                String road = in.readLine();
                String[] edge = road.split(" ");
                int from = Integer.parseInt(edge[0]) - 1;
                int to = Integer.parseInt(edge[1]) - 1;

                g[i] = formatRoad(from, to);
                graph.get(to).add(new Road(from, i));

                if (graph.get(from).add(new Road(to, i))) {
                    num.put(formatRoad(from, to), i);
                }
            }

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    dfs(i, -1);
                }
            }

            used = new boolean[n];

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    paint(i, curColor, -1);
                }
            }

            out.write(curColor + "");
            out.newLine();
            
            for (int i = 0; i < m; i++) {
                if (colors[i] == 0) {
                    out.write(colors[num.get(g[i])] + " ");
                } else {
                    out.write(colors[i] + " ");
                }
            }

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
