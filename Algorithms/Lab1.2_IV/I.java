import java.util.*;
import java.io.*;

public class I {
	public static class Matrix {
		public int[][] matrix;

		public Matrix(int[][] matrix) {
			this.matrix = matrix;
		}

		public Matrix square() {
			int[][] ans = new int[4][4];

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {

					for (int k = 0; k < 4; k++) {
						ans[i][j] += this.matrix[i][k] * this.matrix[k][j];
					}
					ans[i][j] %= 2;
				}
			}
			return new Matrix(ans);
		}

		public boolean equals(Matrix a) {
			boolean ans = true;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					ans &= this.matrix[i][j] == a.matrix[i][j];
				}
			}
			return ans;
		}

		public static Matrix toMatrix(int x) {
			int[][] ans = new int[4][4];

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					ans[i][j] = x % 2;
					x = x >> 1;
				}
			}

			return new Matrix(ans);
		}

		public String toString() {
			String ans = "";
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					ans += this.matrix[i][j] + " ";
				}
				ans += "\n";
			}

			return ans;
		}
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("sqroot.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sqroot.out")));

			int[][] t = new int[4][4];
			for (int i = 0; i < 4; i++) {
				String[] row = in.readLine().split(" ");

				for (int j = 0; j < 4; j++) {
					t[i][j] = Integer.parseInt(row[j]);
				}
			}
			Matrix mat = new Matrix(t);
			boolean isIt = false;

			for (int i = 0; i < (1 << 16); i++) {
				Matrix check = Matrix.toMatrix(i);
				if (check.square().equals(mat)) {
					out.write(check.toString());
					isIt = true;
					break;
				}
			}

			if (!isIt) {
				out.write("NO SOLUTION");
			}

			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
}