import java.util.*;
import java.io.*;

public class A {
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("huffman.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("huffman.out")));

            int n = Integer.parseInt(in.readLine());
            Scanner scan = new Scanner (in.readLine());
            int[] a = new int[n];
            int[] b = new int[n];
            int answer = 0;
            int i = 0;
            int j = 0;

            for (int s = 0; s < n; s++) {
                a[s] = scan.nextInt();
            }

            for (int s = 0; s < n; s++) {
                b[s] = 1000000001;
            }
            for (int s = 0; s < n - 1; s++) {
                if (i < (n - 1)) {
                    if ((a[i] + a[i + 1]) <= (a[i] + b[j]) && (a[i] + a[i + 1]) <= (b[j] + b[j + 1])) {
                        b[s] = a[i] + a[i + 1];
                        answer += b[s];
                        i++;
                        i++;
                    } else if ((a[i] + b[j]) <= (a[i] + a[i + 1]) && (a[i] + b[j]) <= (b[j] + b[j + 1])) {
                        b[s] = a[i++] + b[j++];
                        answer += b[s];
                    } else if ((b[j] + b[j + 1]) <= (a[i] + a[i + 1]) && (b[j] + b[j + 1]) <= (a[i] + b[j])) {
                        b[s] = b[j] + b[j + 1];
                        answer += b[s];
                        j++;
                        j++;
                    }
                }
                else if (i == (n - 1)) {
                    if (a[i] + b[j] <= b[j] + b[j + 1]) {
                        b[s] = a[i] + b[j];
                        i++;
                        j++;
                    } else {
                        b[s] = b[j] + b[j + 1];
                        j += 2;
                    }
                    answer += b[s];
                } else {
                    b[s] = b[j] + b[j + 1];
                    j += 2;
                    answer += b[s];
                }
            }
           
            out.write(answer + "");
            out.close();
            in.close();
        } catch (IOException e) {
            System.err.println(e);
        } 
    }
}