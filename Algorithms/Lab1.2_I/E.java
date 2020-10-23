import java.util.*;
import java.io.*;

public class E {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static node[] a;
    public static int r;

    public static matrix neutral = new matrix(1, 0, 0, 1);
    public static int k = 0;

    public static node operation(node left, node right) {
        return new node (
            operation(left.value, right.value),
            left.leftBorder,
            right.rightBoarder
        );
    }

    public static matrix operation(matrix first, matrix second) {
        return new matrix((first.a1 * second.a1 + first.a2 * second.b1) % r,
                         (first.a1 * second.a2 + first.a2 * second.b2) % r,
                         (first.b1 * second.a1 + first.b2 * second.b1) % r,
                         (first.b1 * second.a2 + first.b2 * second.b2) % r);
    }

    public static class matrix {
        public long a1;
        public long a2;
        public long b1;
        public long b2;

        public matrix(long a1, long a2, long b1, long b2) {
            this.a1 = a1;
            this.a2 = a2;
            this.b1 = b1;
            this.b2 = b2;
        }

        @Override
        public String toString() {
            return a1 + " " + a2 + "\n" + b1 + " " + b2;
        }
    }

    public static class node {
        public matrix value;
        public int leftBorder;
        public int rightBoarder;

        public node(matrix value, int leftBorder, int rightBoarder) {
            this.value = value;
            this.leftBorder = leftBorder;
            this.rightBoarder = rightBoarder;
        }
    }

    public static void build(matrix[] t) {
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


    public static matrix multiply(int position, int left, int right) {
        node current = a[position];
        matrix multiply = neutral;

        if (left <= current.leftBorder && right >= current.rightBoarder) {
            return current.value;
        }
        if (left > current.rightBoarder || right < current.leftBorder) {
            return neutral;
        }

        return operation(multiply(position * 2 + 1, left, right), multiply(position * 2 + 2, left, right)); 
    } 

    public static void set(int i, matrix x) {
        int j = k - 1 + i;
        a[j].value = x;

        while (j > 0) {
            j = (j - 1) / 2;
            a[j] = operation(a[2*j + 1], a[2*j + 2]);
        }
    }


    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(new FileInputStream("crypto.in"), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("crypto.out"), "UTF-8"));
        
        Scanner scan = new Scanner(in.readLine());
        r = scan.nextInt();
        int n = scan.nextInt();
        int m = scan.nextInt();
        

        k = 1;
        while (k < n) {
            k *= 2;
        } 
        matrix[] t = new matrix[n];

        for (int i = 0; i < n; i++) {
            scan = new Scanner(in.readLine());
            long a1 = scan.nextInt();
            long a2 = scan.nextInt();
            scan = new Scanner(in.readLine());
            long b1 = scan.nextInt();
            long b2 = scan.nextInt();
            t[i] = new matrix(a1, a2, b1, b2);
            in.readLine();
        }

        build(t);

        for (int i = 0; i < m; i++) {
            scan = new Scanner(in.readLine());
            out.write(multiply(0, scan.nextInt() - 1, scan.nextInt() - 1) + "\n\n");
            out.flush();
        }
    }
}

// E. Криптография
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводcrypto.in
// выводcrypto.out
// Задано n матриц A1, A2, ..., An размера 2 × 2. Необходимо для нескольких запросов вычислить произведение матриц Ai, Ai + 1, ..., Aj. 
// Все вычисления производятся по модулю r.

// Входные данные
// Первая строка входного файла содержит числа r (1 ≤ r ≤ 10 000), n (1 ≤ n ≤ 200 000) и m (1 ≤ m ≤ 200 000). 
// Следующие n блоков по две строки содержащие по два числа в строке — описания матриц. 
// Затем следуют m пар целых чисел от 1 до n, запросы на произведение на отрезке.

// Выходные данные
// Выведите m блоков по две строки,по два числа в каждой — произведения на отрезках. 
// Разделяйте блоки пустой строкой. Все вычисления производятся по модулю r

// Пример
// входные данные
// 3 4 4
// 0 1
// 0 0

// 2 1
// 1 2

// 0 0
// 0 2

// 1 0
// 0 2

// 1 4
// 2 3
// 1 3
// 2 2
// выходные данныеС
// 0 2
// 0 0

// 0 2
// 0 1

// 0 1
// 0 0

// 2 1
// 1 2