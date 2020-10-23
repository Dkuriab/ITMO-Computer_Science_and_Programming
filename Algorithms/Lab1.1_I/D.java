import java.util.*;

public class D {

	public static void insert(int in, int heapsize, int[] heap) {
		heap[heapsize] = in;
		siftUp(heapsize, heap);	
	}

	public static void siftUp(int heapsize, int[] heap) {
		int v = heapsize;
		while(v != 0 && heap[v] > heap[(v - 1) / 2]) {
			swap(v, (v - 1) / 2, heap);
			v = (v - 1) / 2;
		}		
	}

	public static void extract(int heapsize, int[] heap) {
		System.out.println(heap[0]);
		heapsize--;
		swap (0, heapsize, heap);
		siftDown(0, heapsize, heap);
		}

	public static void siftDown(int v, int heapsize, int[] heap) {
		while ((2 * v + 1) < heapsize) {
			if ((2 * v + 2) < heapsize && max(heap[2 * v + 1], heap[2 * v + 2]) > heap[v]) {
				if (heap[2 * v + 1] >= heap[2 * v + 2]) {
					swap(v, 2 * v + 1, heap);
					v = 2 * v + 1;
				}
				else {
					swap(v, 2 * v + 2, heap);
					v = 2 * v + 2;
				}
			}
			else if (heap[v] < heap[2 * v + 1]) {
				swap(v, 2 * v + 1, heap);
				v = 2 * v + 1;
			}
			else {
				break;
			}
		}
	}	

	public static void swap(int a, int b, int[] heap) {
		int swaper = heap[a];
		heap[a] = heap[b];
		heap[b] = swaper;
	}

	public static int max(int a, int b) {
		if (a >= b) {
			return a;
		}
		else {
			return b;
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int heapsize = 0;
		int[] commands = new int[n];
		int[] digits = new int[n];
		int[] heap = new int[n+1];

		for (int i = 0; i < n; i++) {
			commands[i] = scan.nextInt();
			if (commands[i] == 0) {
				digits[i] = scan.nextInt();
			}
		}	

		for (int i = 0; i < n; i++) {
			if (commands[i] == 0) {
				insert(digits[i], heapsize, heap);
				heapsize++;
			}
			if (commands[i] == 1) {
				extract(heapsize, heap);
				heapsize--;
			}
		}	
	}
}