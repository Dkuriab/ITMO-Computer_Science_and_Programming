import java.io.*;
import java.util.*;
 
public class I {
    public static boolean[] used;
    public static boolean[] usefull;
    public static HashMap<Integer, TreeSet<Pair>> rg;
 
    public static void fromEnd(int cur, int n) {
        System.out.println(cur + " frE");
        usefull[cur] = true;
        TreeSet<Pair> d = rg.get(cur);
        if (d == null) {
            return;
        }
        for (Pair pair : d) {
            int j = pair.getNum();
            if (!usefull[j] && used[j]) {
                fromEnd(j, n);
            }
        }
    }
 
 
    public static void dfs(int cur, int[][] graph) {
        System.out.println(cur + " dfs");
        used[cur] = true;
        for (int i = 0; i < 27; i++) {
            if (!used[graph[cur][i]] && graph[cur][i] != 0) {
                dfs(graph[cur][i], graph);
            }
        }
    }
 
    public static class Pair implements Comparable<Pair> {
        public int num;
        public int symbol;
 
        public Pair(int num, int symbol) {
            this.num = num;
            this.symbol = symbol;
        }
 
        public int getNum() {
            return num;
        }
 
        public int getSym() {
            return symbol;
        }
 
        public int compareTo(Pair b) {
            if (num == b.num && symbol == b.symbol) {
                return 0;
            }
            return 1;
        }
    }
 
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("fastminimization.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("fastminimization.out")));
 
            String[] parametrs = in.readLine().split(" ");
            int n = Integer.parseInt(parametrs[0]); // number of points
            int m = Integer.parseInt(parametrs[1]); // number of roads
            int k = Integer.parseInt(parametrs[2]); // number of ends
 
            int[][] graph = new int[n + 1][27];
            boolean[] ends = new boolean[n + 1];
 
            used = new boolean[n + 1];
            usefull = new boolean[n + 1];
 
            rg = new HashMap<>();
 
            String[] endsString = in.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                ends[Integer.parseInt(endsString[i])] = true;
            }
 
            for (int i = 0; i < m; i++) {
                String[] cur = in.readLine().split(" ");
                int from = Integer.parseInt(cur[0]);
                int to = Integer.parseInt(cur[1]);
                char c = cur[2].charAt(0);
 
                graph[from][c - 97] = to;
 
 
                TreeSet<Pair> set = rg.get(to);
                if (set == null) {
                    set = new TreeSet<>();
                }
                set.add(new Pair(from, c - 97));
 
                rg.put(to, set);
            }
 
            dfs(1, graph);
 
            for (int i = 1; i <= n; i++) {
                if (ends[i] && used[i]) {
                    fromEnd(i, n);
                }
            }
             
            TreeSet<Integer> endsSet = new TreeSet<>();
            TreeSet<Integer> otherSet = new TreeSet<>();
 
            int[] classes = new int[n + 1];
             
            for (int i = 1; i <= n; i++) {
                if (usefull[i]) {    
                    if (ends[i]) {
                        endsSet.add(i);
                        classes[i] = 0;
                    } else {
                        otherSet.add(i);
                        classes[i] = 1;
                    }
                }
            }

            System.out.println("ends: " + endsSet);
            System.out.println("starts" + otherSet);
 
            ArrayList<TreeSet<Integer>> P = new ArrayList<>();
            P.add(endsSet);
            P.add(otherSet);
             
            ArrayDeque<Pair> Queue = new ArrayDeque<>();
 
            for (int symbol = 0; symbol < 27; symbol++) {
                Queue.push(new Pair(0, symbol));
                Queue.push(new Pair(1, symbol));
            }
 
            // Hopkroft...
            HashMap<Integer, ArrayList<Integer>> involved = new HashMap<>();
            while (!Queue.isEmpty()) {
                Pair cur = Queue.poll();
 
                involved.clear();
 
                for (int q : P.get(cur.getNum())) {
                    TreeSet<Pair> d = rg.get(q);
                    if (d != null) {
                        for (Pair pair : d) {
                            int r = pair.getNum();
                            if (usefull[r] && pair.getSym() == cur.getSym()) {
                                int i = classes[r];
 
                                ArrayList<Integer> u = involved.get(i);
                                if (u == null) {
                                    u = new ArrayList<Integer>();
                                }
                                u.add(r);
                                involved.put(i, u);                           
                            }
                        } 
                    }  
                }
                for (int i : involved.keySet()) {
                    if (involved.get(i) != null && involved.get(i).size() < P.get(i).size()) {
                        int j = P.size();

                        // System.out.println(P.get(i));
 
                        TreeSet<Integer> wayToJ = new TreeSet<>();
                        TreeSet<Integer> u = P.get(i);
 
                        for (int r : involved.get(i)) {
                            u.remove(r);
                            wayToJ.add(r);
                        }
 
                        P.set(i, u);
                        P.add(wayToJ);
 
                        if (P.get(j).size() > P.get(i).size()) {
                            TreeSet<Integer> fromJ = P.get(j);
                            P.set(j, P.get(i));
                            P.set(i, fromJ);

                            // System.out.println(fromJ);
                        }
                        for (int r : P.get(j)) {
                            classes[r] = j;
                        }
                        for (int c = 0; c < 27; c++) {
                            Queue.add(new Pair(j, c));
                        }
                    }
                }
            }
 
            TreeSet<Integer> endsMin = new TreeSet<>();
            // reNumbering
            int firstIn = classes[1];
            for (int i = 1; i <= n; i++) {
                if (usefull[i]) {    
                    if (classes[i] == 0) {
                        classes[i] = firstIn;
                    } else if (classes[i] == firstIn) {
                        classes[i] = 0;
                    }
                }
            }
 
 
 
            for (int i = 1; i <= n; i++) {
                if (ends[i] && usefull[i]) {
                    endsMin.add(classes[i] + 1);
                }
            }
 
            String ans = "";
 
            int counterRoads = 0;
            int counterPoints = 0;
            int counterEnds = endsMin.size();
 
            for (int i = 0; i < P.size(); i++) {
                TreeSet<Integer> ith = P.get(i);
 
                if (ith != null && ith.size() != 0) {
                    counterPoints++;
 
                    Object[] ia = ith.toArray();
                    int fromI = (int)ith.toArray()[0];
 
                    for (int symbol = 0; symbol < 27; symbol++) {
                        int to = graph[fromI][symbol];
 
                        if (to != 0 && usefull[to]) {
                            counterRoads++;
                            ans += (classes[fromI] + 1) + " " + (classes[to] + 1) + " " + (char)(symbol + 97) + "\n";
                        }
                    }
                }
            }
 
            out.write(counterPoints + " " + counterRoads + " " + counterEnds);
            out.newLine();
            for (int i : endsMin) {
                out.write(i + " ");
            }
            out.newLine();
            out.write(ans + "");
 
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}