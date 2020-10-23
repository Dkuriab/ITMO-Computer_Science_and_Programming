
#include <iostream>

using namespace std;

void merge(double a[], int b[], const int l, int m, const int r) {
	int i = l;
	int j = m;
	int t = 0;
	double c[r - l];
	int cc[r - l];
	while (i < m || j < r) {
		if (i < m && (j == r || a[i] > a[j])) {
			c[t] = a[i];
			cc[t] = b[i];
			i++;
			t++;
		}
		else {
			c[t] = a[j];
			cc[t] = b[j];
			t++;
			j++;
		}
	}
	for (int i = 0; i < t; i++) {
		b[i] = cc[i];
		a[i] = c[i];
	
	}
}

void mergesort(double *a, int *b, int l, int r) {
	if (r - l <= 1) {
		return;
	}
	int m = (l + r) / 2;
	mergesort(a, b, l, m);
	mergesort(a, b, m, r);
	merge(a, b, l, m, r);
}

bool check(double s, int *v, int *w, int n, int k) {
	double raz[1e7];
	int num[1e7];
	for (int i = 0; i < n; i++) {
		raz[i] = v[i] - s * w[i];
		num[i] = i;
	}

	mergesort(raz, num, 0, n);

	double sum = 0;

	for (int i = 0; i < k; i++) {
		sum += raz[i];
	}

	return (sum >= 0);
}

void result(double s, int *v, int *w, int n, int k) {
	double raz[1e7];
	int num[1e7];

	for (int i = 0; i < n; i++) {
		raz[i] = v[i] - s * w[i];
		num[i] = i + 1;
	}
	mergesort(raz, num, 0, n);

	for (int i = 0; i < k; i++) {
		cout << num[i] << endl;
	}
}
int main() {
	int n;
	cin >> n;
	int k;
	cin >> k;
	double l = 0;
	double r = 1e7;

	double raz[1e7];
	int v[1e7];
	int w[1e7];

	for (int i = 0; i < n; i++) {
		cin >> v[i];
		cin >> w[i];
	}

	for (int i = 0; i < 100; i++) {
		if (check((r + l) / 2, v, w, n, k)) {
			l = (r + l) / 2;
		}
		else {
			r = (r + l) / 2;
		}
	}

result(r, v, w, n, k);
}
