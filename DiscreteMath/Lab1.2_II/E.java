import java.util.*;
import java.io.*;
 
public class E {
 
    public static class ChainPair {
        public int left;
        public int right;
 
        public ChainPair(int left, int right) {
            this.left = left;
            this.right = right;
        }
 
        public String toString() {
            return "chain: " +left + " -> " + right;
        }
 
        public boolean isSelfish() {
            return left == right;
        }
    }
 
    //little letters
    public static boolean isTerm(int symbol) {
        return (symbol >= 97  && symbol <= 122) || symbol == 0;
    }
 
    //starst since 'A' + 100 
    public static boolean isNonterm(int symbol) {
        return symbol >= 165;
    }
 
    public static boolean isBig(int symbol) {
        return symbol >= 65 && symbol <= 90;
    }
 
    public static void main(String[] args) {
        try {
 
// input 
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("cf.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cf.out")));
         
            String[] parametrs = in.readLine().split(" ");
            int n = Integer.parseInt(parametrs[0]); // number of productions
            int S = parametrs[1].charAt(0) + 100; // start symbol
 
            HashSet<String> checkProd = new HashSet<>();
            TreeMap<Integer, ArrayList<ArrayList<Integer>>> productions = new TreeMap<>();
 
            for (int i = 0; i < n; i++) {
                String curInfo = in.readLine();
                if (checkProd.add(curInfo)) {
 
                    String[] production = curInfo.split(" ->");
                    int from = (int)production[0].charAt(0) + 100;
 
                    ArrayList<Integer> curR = new ArrayList<>();
 
                    if (production.length == 1) {
                        curR.add(0);
                    } else {
                        for (int j = 1; j < production[1].length(); j++) {
                            int c = production[1].charAt(j);
                            c = isBig(c) ? c + 100 : c;
                            curR.add((int)c);
                        }
                    }
 
                    // productions.computeIfPresent(from, (k,v)->v+1);
 
                    ArrayList<ArrayList<Integer>> variable = productions.getOrDefault(from, new ArrayList<ArrayList<Integer>>());
                    variable.add(curR);
                    productions.put(from, variable);
                }
            }
            String word = in.readLine();
// input
 
// deleting long productions
            TreeMap<Integer, ArrayList<ArrayList<Integer>>> shortProductions = new TreeMap<>();
            int lastNonterm = 301;
 
            for (int key : productions.keySet()) {
                for (ArrayList<Integer> list : productions.get(key)) {
                    if (list.size() <= 2) {
 
                        ArrayList<ArrayList<Integer>> variable = shortProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
                        variable.add(list);
                        shortProductions.put(key, variable);
                         
                    } else {
                        Object[] rightPart = list.toArray();
                        int size = rightPart.length;
                        int curNonT = key;
 
                        for (int i = 0; i <= size - 3; i++) {
                            ArrayList<ArrayList<Integer>> variable = shortProductions.getOrDefault(curNonT, new ArrayList<ArrayList<Integer>>());
                            ArrayList<Integer> newProd = new ArrayList<Integer>(List.of((int)rightPart[i], ++lastNonterm));
 
                            variable.add(newProd);
                            shortProductions.put(curNonT, variable);
                            curNonT = lastNonterm;
                        }
 
                        ArrayList<ArrayList<Integer>> variable = shortProductions.getOrDefault(curNonT, new ArrayList<ArrayList<Integer>>());
 
                        variable.add(new ArrayList<Integer>(List.of((int)rightPart[size - 2], (int)rightPart[size - 1])));
                        shortProductions.put(curNonT, variable);
 
                    }
                }
            }
            lastNonterm++;
// deleting long productions
 
// deleteing epsilon productions
            TreeMap<Integer, ArrayList<ArrayList<Integer>>> nonEpsShortProductions = new TreeMap<>();
             
            HashSet<Integer> epsGenerating = new HashSet<Integer>();
            boolean changes = true;
            while (changes) {
                int before = epsGenerating.size();
                for (int key : shortProductions.keySet()) {
                    for (ArrayList<Integer> list : shortProductions.get(key)) {
                        boolean isEpsGen = true;
                        for (int k : list) {
                            if (isTerm(k) || !epsGenerating.contains(k)) {
                                isEpsGen = false;
                            }
                            if (k == 0) {
                                isEpsGen = true;
                            }
                        }
                        if (isEpsGen) {
                            epsGenerating.add(key);
                        }
                    }
                }
                if (epsGenerating.size() == before) {
                    changes = false;
                }
            }
 
            for (int key : shortProductions.keySet()) {
                for (ArrayList<Integer> list : shortProductions.get(key)) {
                    boolean containsEpsNT = false;
                    boolean isEps = false;
                    int count = 0;
 
                    for (int k : list) {
                        if (epsGenerating.contains(k)) {
                            containsEpsNT = true;
                            count++;
                        }
                        if (k == 0) {
                            isEps = true;
                        }
                    }
 
                    ArrayList<ArrayList<Integer>> variable = nonEpsShortProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
 
                    if (!isEps) {
                        variable.add(list);
                     
                        Object[] listArr = list.toArray();
 
                        if (count == 1 && list.size() == 2) {
 
                            if (epsGenerating.contains((int)listArr[0])) {
                                variable.add(new ArrayList<Integer>(List.of((int)listArr[0])));
                            } else {
                                variable.add(new ArrayList<Integer>(List.of((int)listArr[1])));
                            }
 
                        } else if (count == 2) {
                            variable.add(new ArrayList<Integer>(List.of((int)listArr[0])));
                            variable.add(new ArrayList<Integer>(List.of((int)listArr[1])));
                        }
                        nonEpsShortProductions.put(key, variable);
                    }
                }
            }
// deleteing epsilon productions
 
            if (epsGenerating.contains(S)) {
                ArrayList<ArrayList<Integer>> variable = nonEpsShortProductions.getOrDefault(lastNonterm, new ArrayList<ArrayList<Integer>>());
 
                variable.add(new ArrayList<Integer>(List.of(0)));
                variable.add(new ArrayList<Integer>(List.of(S)));
                nonEpsShortProductions.put(lastNonterm, variable);
                S = lastNonterm++;
            }
 
// delete chains
            TreeMap<Integer, ArrayList<ArrayList<Integer>>> nonChainNonEpsShortProductions = new TreeMap<>();
 
            HashSet<Integer> usedNT = new HashSet<>();
            HashSet<String> checkChains = new HashSet<>();
            ArrayList<ChainPair> chainPairs = new ArrayList<>();
 
            for (int key : nonEpsShortProductions.keySet()) {
                usedNT.add(key);
 
                for (ArrayList<Integer> list : nonEpsShortProductions.get(key)) {
                    for (int k : list) {
                        if (isNonterm(k)) {
                            usedNT.add(k);
                        }
                    }
                }
            }
 
            for (int nt : usedNT) {
                checkChains.add(nt + " -> " + nt);
                chainPairs.add(new ChainPair(nt, nt));
            }
 
 
            changes = true;
 
            while (changes) {
                int before = checkChains.size();
 
                for (int key : nonEpsShortProductions.keySet()) {
                    for (ArrayList<Integer> list : nonEpsShortProductions.get(key)) {
                        boolean isChain = false;
                        int next = 0;
 
                        for (int k : list) {
                            if (isNonterm(k) && list.size() == 1) {
                                isChain = true;
                                next = k;
                            }
                        }
                         
                        if (isChain && key != next) {
 
                            ArrayList<ChainPair> newPairs = new ArrayList<>();
                            for (ChainPair cur : chainPairs) {
                                if (cur.right == key) {
                                    if (checkChains.add(key + " -> " + next)) {
                                        newPairs.add(new ChainPair(key, next));
                                    }
                                }
                            }
                            chainPairs.addAll(newPairs);
 
                        }
                    }
                }
 
                if (checkChains.size() == before) {
                    changes = false;
                }
            }
 
            changes = true;
 
            while (changes) {
                int before = chainPairs.size();
                Object[] pairs = chainPairs.toArray();
                for (Object aa : pairs) {
                    ChainPair a = (ChainPair) aa;
                    for (Object ba : pairs) {
                        ChainPair b = (ChainPair) ba;
                        if (a.right == b.left) {
                            if (checkChains.add(a.left + " -> " + b.right)) {
                                chainPairs.add(new ChainPair(a.left, b.right));
                            }
                        }
                    }   
                }
 
                if (chainPairs.size() == before) {
                    changes = false;
                }
            }
 
            for (ChainPair l : chainPairs) {
                // System.out.println(l.toString() + " " + nonEpsShortProductions.containsKey(l.right));
                if (nonEpsShortProductions.containsKey(l.right)) {
                    for (ArrayList<Integer> list : nonEpsShortProductions.get(l.right)) {
                        boolean isChain = false;
 
                        for (int k : list) {
                            if (isNonterm(k) && list.size() == 1) {
                                isChain = true;
                            }
                        }
 
                        if (!isChain) {
                            ArrayList<ArrayList<Integer>> variable = nonChainNonEpsShortProductions.getOrDefault(l.left, new ArrayList<ArrayList<Integer>>());
                            variable.add(list);
                            nonChainNonEpsShortProductions.put(l.left, variable);
                        }
                    }
                }
            }
// delete chains
 
// removing useless
            TreeMap<Integer, List<Set<Integer>>> productionsU = new TreeMap<>();
            TreeMap<Integer, List<Set<Integer>>> productionsOnlyGen = new TreeMap<>();
            HashSet<Integer> generating = new HashSet<Integer>();
            HashSet<Integer> allNT = new HashSet<Integer>();
 
            for (int key : nonChainNonEpsShortProductions.keySet()) {
                allNT.add(key);
                for (ArrayList<Integer> list : nonChainNonEpsShortProductions.get(key)) {
                    boolean onlyTerm = true;
                    HashSet<Integer> newRight = new HashSet<Integer>();
 
                    for (int k : list) {
                        newRight.add(k);
 
                        if (isNonterm(k)) {
                            allNT.add(k);
                            onlyTerm = false;
                        }
                    }
 
                    if (onlyTerm) {
                        generating.add(key);
                        List<Set<Integer>> curGen = productionsOnlyGen.getOrDefault(key, new ArrayList<Set<Integer>>());
                        curGen.add(newRight);
                        productionsOnlyGen.put(key, curGen);
                    } else {
                        List<Set<Integer>> cur = productionsU.getOrDefault(key, new ArrayList<Set<Integer>>());
                        cur.add(newRight);
                        productionsU.put(key, cur);
                    }
                     
                }
            }
 
 
                             
            changes = true; // finding generating
            while (changes) {
                int before = generating.size();
 
                for (int key : productionsU.keySet()) {
                    for (Set<Integer> cir : productionsU.get(key)) {
 
                        boolean isGen = true;
                         
                        for (int f : cir) {
                            if (isNonterm(f) && !generating.contains(f)) {
                                isGen = false;
                            }
                        }
 
                        if (isGen) {
                            generating.add(key);
 
                            List<Set<Integer>> curGen = productionsOnlyGen.getOrDefault(key, new ArrayList<Set<Integer>>());
                            curGen.add(cir);
                            productionsOnlyGen.put(key, curGen);
 
                        }
                    }
                }
 
                if (generating.size() == before) {
                    changes = false;
                }
            } // finding generating
     
            boolean isS = false;
            for (int key : productionsOnlyGen.keySet()) {
                for (Set<Integer> curSet : productionsOnlyGen.get(key)) {
                    if (key == S) {
                        isS = true;
                    }
 
                    for (int v : curSet) {
                        if (v == S) {
                            isS = true;
                        }
                    }
                }
            }
 
     
 
            changes = true; // finding achivable
            HashSet<Integer> achivable = new HashSet<Integer>(Set.of((int)S));
 
            while (changes) {
                int before = achivable.size();
 
                for (int key : productionsOnlyGen.keySet()) {
                    if (achivable.contains(key)) {
                        for (Set<Integer> cir : productionsOnlyGen.get(key)) {
                            achivable.addAll(cir);  
                        }
                    }
                }   
 
                if (achivable.size() == before) {
                    changes = false;
                }
            } // finding achivable
     
 
            // System.out.println(allNT);
 
            allNT.removeAll(achivable);
 
            if (!isS) {
                allNT.add((int)S);
            }
 
            // System.out.println(allNT);
 
            TreeMap<Integer, ArrayList<ArrayList<Integer>>> usefullNonChainNonEpsShortProductions = new TreeMap<>();
 
            for (int key : nonChainNonEpsShortProductions.keySet()) {
                if (!allNT.contains(key)) {
 
                    for (ArrayList<Integer> list : nonChainNonEpsShortProductions.get(key)) {
                        boolean isUsefullProd = true;
                        for (int k : list) {
                            if (allNT.contains(k)) {
                                isUsefullProd = false;
                            }
                        }
 
                        if (isUsefullProd) {
                            ArrayList<ArrayList<Integer>> variable = usefullNonChainNonEpsShortProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
 
                            variable.add(list);
                            usefullNonChainNonEpsShortProductions.put(key, variable);
                        }
                    }
                }
            }
// removing useless
 
// removing B -> Aa and B -> hc
 
        TreeMap<Integer, ArrayList<ArrayList<Integer>>> finishProductions = new TreeMap<>();
 
        for (int key : nonChainNonEpsShortProductions.keySet()) {
            for (ArrayList<Integer> list : nonChainNonEpsShortProductions.get(key)) {
                if (list.size() == 2) {
                    Object[] listArr = list.toArray();
 
                    // A -> ab
                    if (isTerm((int)listArr[0]) && isTerm((int)listArr[1])) {
                        int firstNT = lastNonterm++;
                        int secondNT = lastNonterm++;
 
                        ArrayList<ArrayList<Integer>> variable1 = finishProductions.getOrDefault(firstNT, new ArrayList<ArrayList<Integer>>());
                        variable1.add(new ArrayList<Integer>(List.of((int)listArr[0])));
                        finishProductions.put(firstNT, variable1);
 
                        ArrayList<ArrayList<Integer>> variable2 = finishProductions.getOrDefault(secondNT, new ArrayList<ArrayList<Integer>>());
                        variable2.add(new ArrayList<Integer>(List.of((int)listArr[1])));
                        finishProductions.put(secondNT, variable2);
 
                        ArrayList<ArrayList<Integer>> variable = finishProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
                        variable.add(new ArrayList<Integer>(List.of(firstNT, secondNT)));
                        finishProductions.put(key, variable);
 
                    } else if (isTerm((int)listArr[0]) && isNonterm((int)listArr[1])) {
                        int firstNT = lastNonterm++;
 
                        ArrayList<ArrayList<Integer>> variable1 = finishProductions.getOrDefault(firstNT, new ArrayList<ArrayList<Integer>>());
                        variable1.add(new ArrayList<Integer>(List.of((int)listArr[0])));
                        finishProductions.put(firstNT, variable1);
 
 
                        ArrayList<ArrayList<Integer>> variable = finishProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
                        variable.add(new ArrayList<Integer>(List.of(firstNT, (int)listArr[1])));
                        finishProductions.put(key, variable);
 
                    } else if (isNonterm((int)listArr[0]) && isTerm((int)listArr[1])) {
                        int secondNT = lastNonterm++;
 
                        ArrayList<ArrayList<Integer>> variable2 = finishProductions.getOrDefault(secondNT, new ArrayList<ArrayList<Integer>>());
                        variable2.add(new ArrayList<Integer>(List.of((int)listArr[1])));
                        finishProductions.put(secondNT, variable2);
 
                        ArrayList<ArrayList<Integer>> variable = finishProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
                        variable.add(new ArrayList<Integer>(List.of((int)listArr[0], secondNT)));
                        finishProductions.put(key, variable);
                    } else {
                        ArrayList<ArrayList<Integer>> variable = finishProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
                        variable.add(list);
                        finishProductions.put(key, variable);
                    }
 
 
                } else {
                    ArrayList<ArrayList<Integer>> variable = finishProductions.getOrDefault(key, new ArrayList<ArrayList<Integer>>());
                    variable.add(list);
                    finishProductions.put(key, variable);
                }
            }
        }
// removing B -> Aa and B -> hc
 
            boolean[][][] dp = new boolean[lastNonterm + 1][word.length()][word.length()];
 
            for (int key : finishProductions.keySet()) {
                for (ArrayList<Integer> list : finishProductions.get(key)) {
                    if (list.size() == 1) {
                        int sym = (int)list.toArray()[0];
 
                        for (int i = 0; i < word.length(); i++) {
                            if ((int)word.charAt(i) == sym) {
                                dp[key][i][i] = true;
                            }
                        }
                    }
                }
            }
 
            for (int i = word.length() - 1; i >= 0; i--) {
                for (int j = i + 1; j < word.length(); j++) {
                    for (int key : finishProductions.keySet()) {
                        //dp[key][i][j]
                        for (ArrayList<Integer> list : finishProductions.get(key)) {
                            if (list.size() == 2) {
                                Object[] prod = list.toArray();
                                int A = (int)prod[0];
                                int B = (int)prod[1];
 
                                for (int k = i; k < j; k++) {
                                    dp[key][i][j] = dp[key][i][j] || (dp[A][i][k] && dp[B][k + 1][j]);
                                }
                            }
                        }
                    }
                }
            }
 
            if (dp[S][0][word.length() - 1]) {
                out.write("yes");
            } else {
                out.write("no");
            }
 
            out.close();
            in.close();
 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
// Задача E. КС-грамматика
// Имя входного файла: cf.in
// Имя выходного файла: cf.out
// Дана КС-грамматика, задающая язык L. Дано слово w. Определить, принадлежит ли слово w
// языку L.
// Формат входного файла
// В первой строке задано число продукций n и стартовый символ. Следующие n строк содержат
// продукции вида Ai → Ni
// , где Ai — нетерминалы, а Ni — строка из терминалов и нетерминалов. В
// следующей строке задано слово w (1 6 n 6 50, 0 6 |Ni
// | 6 5, 1 6 |w| 6 100).
// Каждый нетерминальный символ представлен в виде большой буквы латинского алфавита, а
// терминальный — маленькой. Все слова состоят только из маленьких букв латинского алфавита.
// Строка Ni может быть пустой.
// Формат выходного файла
// Для каждого wi выведите «yes», если слово принадлежит языку, или «no» в противном случае.
// Пример
// cf.in cf.out
// 2 S
// S -> aA
// A -> b
// ab
// yes
}