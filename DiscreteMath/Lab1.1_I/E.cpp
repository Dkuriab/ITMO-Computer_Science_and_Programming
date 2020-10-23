#include <iostream>

using namespace std;

int main() {
    int n = 0;
    cin >> n;

    int elem [n + 1];		//element 0 ili ne 0
    int bound [n + 1][33];	//c chem sviazan
    int table [n + 1][33];	//tabliza istinnpsti
    int level [n + 1];		//uroven elementa
    int now [n + 1];
    int power = 0;						//kol-vo peremennix
    int frog = 0;						//tmp

    for (int i = 1; i <= n; i++) {
        cin >> elem[i];
        if (elem[i] == 0) {
            power++;
        } else {
            for (int j = 0; j < elem[i]; j++) {
                cin >> bound[i][j];
            }
            frog = 1 << elem[i];
            for (int j = 0; j < frog; j++) {
                cin >> table[i][j];
            }
        }
    }
    for (int i = 1; i <= n; i++) {
        if (elem[i] == 0) {
            level[i] = 0;
        } else {
            int max = 0;
            for (int j = 0; j < elem[i]; j++) { //poisk max yrovnia predidushego
                if (level[bound[i][j]] > max) {
                    max = level[bound[i][j]];
                }
            }
            level[i] = max + 1;
        }
    }

    cout << level[n] << endl;

    for (int i = 0; i < (1 << power); i++) {
        int naw = i;
        for (int t = n; t >= 1; t--) {
            if (elem[t] == 0) {
                now[t] = naw % 2;
                naw >>= 1;
            }
        }
        for (int t = 1; t <= n; t++) {
            if (elem[t] != 0) {
                int tmp = 0;
                for (int y = 0; y < elem[t]; y++) {
                    if (now[bound[t][y]] == 1) {
                        tmp += (1 << (elem[t] - 1 - y));
                    }
                }
                now[t] = table[t][tmp];
            }
        }
        cout << now[n];
    }
}
