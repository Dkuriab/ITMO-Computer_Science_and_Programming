import java.util.*;
import java.io.*;

public class K {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static node[] a;
    public static int r;
    public static int n;

    public static int neutral = 0;
    public static int k = 1;

    public static node operation(node left, node right) {
        return new node (
            operation(left.value, right.value),
            left.leftBorder,
            right.rightBorder
        );
    }

    public static int operation(int left, int right) {
        return left + right;
    }

    public static int minimum(int a, int b) {
        return a < b ? a : b;
    }

    public static class node {
        public int value;
        public int leftBorder;
        public int rightBorder;

        public node(int value, int leftBorder, int rightBorder) {
            this.value = value;
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        public boolean isLeaf() {
            return leftBorder == rightBorder;
        }
    }

    public static void build(int[] t) {
        int n = t.length;
        while (k < n) {
            k *= 2;
        } 
        a = new node[2*k - 1];

        for (int i = 0; i < n; i++) {
            a[k - 1 + i] = new node(t[i], i, i);
        }
        for (int i = n; i < k; i++) {
            a[k - 1 + i] = new node(neutral, i, i);
        }
        for (int i = k - 2; i >= 0; i--) {
            a[i] = operation(a[2*i + 1], a[2*i + 2]);
        }
    }

    public static int lefter(int position) {
        if (a[position].value == 0) {
            return Integer.MAX_VALUE;
        }

        while (!a[position].isLeaf()) {
            if (a[position * 2 + 1].value  > 0) {
                position = position * 2 + 1;
            } else {
                position = position * 2 + 2;
            }
        }

        return position;
    }

    public static int contain(int position, int l, int r) {

        if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
            return lefter(position);
        }
        if (l > a[position].rightBorder || r < a[position].leftBorder) {
            return Integer.MAX_VALUE;
        }
        return minimum(contain(position * 2 + 1, l, r), contain(position * 2 + 2, l, r));
    } 

    public static int enter(int x) {
        if (a[k - 1 + x].value == 1) {
            set(x, 0);
            return x + 1;
        }

        int res = minimum(contain(0, 0, x - 1), contain(0, x + 1, n -1)) - k + 1;
        int priority = contain(0, x + 1, n - 1);

        if (priority != Integer.MAX_VALUE) { 
            res =  priority - k + 1;   
        }
        set(res, 0);
        return res + 1;
        
    }


    public static void set(int i, int x) {
        int j = k - 1 + i;
        a[j].value = x;

        while (j > 0) {
            j = (j - 1) / 2;
            a[j] = operation(a[2*j + 1], a[2*j + 2]);
        }
    }


    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(new FileInputStream("parking.in"), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("parking.out"), "UTF-8"));
        
        Parser scan = new Parser(in);

        n = scan.nextInt();
        int m = scan.nextInt();

        int[] t = new int[n];
        for (int i = 0; i < n; i++ ) {
            t[i] = 1;
        }

        build(t);

        for (int i = 0; i < m; i++) {
            scan.nextLine();
            if (scan.next().equals("enter")) {
                out.write(enter(scan.nextInt() - 1) + "\n");
                out.flush();
            } else {
                set(scan.nextInt() - 1, 1);
            }
        }
    }

// K. Парковка
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводparking.in
// выводparking.out
// Hа кольцевой парковке есть n мест пронумерованых от 1 до n. Есть два вида событий прибытие машину на парковку и отъезд машины с парковки. Если машина приезжает на парковку, а её место занято, то она едет далее по кругу и встаёт на первое свободное место.

// Входные данные
// В первой строке входного файла находится два числа n и m — размер парковки и количество запросов(1 ≤ n, m ≤ 100000). В следующих m строках находятся события. Каждая из этих строк имеет следующий вид:

// enter x — приехала машина, которая хочет встать на место x. Для каждой такой команды выведите какое место займёт эта машина.
// exit x — уехала машина занимавшая место x. Гарантируется, что на этом месте была машина.
// Выходные данные
// Выведите последовательно результаты выполнения всех операций enter.

// Пример
// входные данные
// 3 5
// enter 1
// enter 1
// exit 1
// enter 2
// enter 2
// выходные данные
// 1
// 2
// 3
// 1
    public static class Parser {
        private String line;
        private BufferedReader re;
        private int point = 0;
         
        
        public Parser(BufferedReader in) throws IOException {
            re = in;
            line = re.readLine();      
        }
        
        public String nextLine() throws IOException {
            try {
                line = re.readLine();
                if (line != null) {
                    line += " ";
                    point = 0;    
                }
                return line;
            } catch (IOException e) {
                throw new IOException ("At nextLine " + e.getMessage());
            }
        }
        
        public String getLine() {
            return line;
        }  

        public boolean hasNextInt() {
            if (line == null || line.isEmpty()) {
                return false;
            }

            nextPoint();
            return point != line.length();   
        }    

        public int nextInt() throws NoSuchElementException {
            if (!hasNextInt()) {
                throw new NoSuchElementException("Can't research next int ");
            }
            nextPoint();
            int start = point;
            while (point < line.length() && !Character.isWhitespace(line.charAt(point))) {
                point++;
            }
            return Integer.parseInt(line.substring(start, point));
        }
        
        public boolean hasNextWord() {
            if (line == null) {
                return false;
            }
            nextPointW();
            if (point == line.length()) {
                return false;
            } 
            return true;
        }
        
        public String next() throws NoSuchElementException {
            if (!hasNextWord()) {
                throw new NoSuchElementException ("Can't research next word ");
            }
            nextPointW();
            int start = point;
            while ((point < line.length()) && isPartOfWord(line.charAt(point))) {
                point++;
            }
            String word = line.substring(start, point).toLowerCase();  
            return word;
        }

        public void close() throws IOException {
            re.close();
        }

        public void nextPoint() {
            while (point < line.length() && Character.isWhitespace(line.charAt(point))) {
                point++;          
            }
        }

        public void nextPointW() {
            while (point < line.length() && !isPartOfWord(line.charAt(point))) {
                point++;
            }
        }

        private static boolean isPartOfWord(char p) {
            return Character.isLetter(p) || Character.getType(p) == Character.DASH_PUNCTUATION || p == '\'';
        }    
    }   
}