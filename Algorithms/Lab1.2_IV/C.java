import java.util.*;
import java.io.*;

public class C {

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("printing.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("printing.out")));

			int n = Integer.parseInt(in.readLine().split(" ")[0]);
			int[] a = new int[7];
			int[] batch = new int[7];

			for (int i = 0; i < 7; i++) {
				a[i] = Integer.parseInt(in.readLine().split(" ")[0]);
				batch[i] = (int)Math.pow(10, i);
			}

			int ans = 0;
			while (n > 0) {
				int bestChoose = 0;
				double bestPricePerSheet = Integer.MAX_VALUE;
				
				for (int i = 0; i < 7; i++) {
					int num = n >= batch[i] ? batch[i] : n;

					double price = a[i] / (num + 0.0);

					if (price < bestPricePerSheet) {
						bestChoose = i;
						bestPricePerSheet = price;
					} 
				}

				n -= batch[bestChoose];
				ans += a[bestChoose];

				int count = n / batch[bestChoose];

				n -= count * batch[bestChoose];
				ans += count * a[bestChoose];

			}

			out.write(ans + "");
			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}