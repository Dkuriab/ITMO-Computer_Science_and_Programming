import java.util.*;
import java.lang.*;
import java.io.*;


public class F {
    
    public static int xor(int x, int y) {
        return (y + x) % 2;
    } 

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int power = (int)Math.pow(2, n);
        int[] polynom = new int[power];  //для полинома Жегалкина
        int[] input = new int[power];    //значения функции
        int[] digit = new int[power];    //набор переменных
        int[] tmp = new int[power];

        for (int i = 0; i < power; i++) {
            digit[i] = scan.nextInt();
            input[i] = scan.nextInt();
        }
        polynom[0] = input[0]; // постоение полинома Ж. методом треугольника
        boolean flag = true;

        for (int i = 1; i < power; i++) {
            if (flag) {
            for (int t = 0; t < power - i; t++) {
                    tmp[t] = xor(input[t], input[t + 1]);
                }
                polynom[i] = tmp[0];
                flag = false;
            }
            else {
                for (int t = 0; t < power - i; t++) {
                    input[t] = xor(tmp[t], tmp[t + 1]);
                }
                polynom[i] = input[0];
                flag = true;
            }
        }
        String format = "%0" + n + "d ";

        for (int i = 0; i < power; i++) {
            System.out.printf(format, digit[i]);
            System.out.print(polynom[i]);
            System.out.println();
        }
    } 
}