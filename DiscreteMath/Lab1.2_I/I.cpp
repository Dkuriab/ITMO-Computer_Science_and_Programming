#include <iostream>
#include <bits/stdc++.h>

using namespace std;
    map<int, set<pair<int, int>>> rg;
    bool used[50005];
    bool usefull[50005];
    int graph[50005][27];

    void fromEnd(int cur) {
        usefull[cur] = true;
        set<pair<int, int>> d = rg[cur];
        if (d.empty()) {
            return;
        }
        for (pair<int, int> pair : d) {
            int j = pair.first;
            if (!usefull[j] && used[j]) {
                fromEnd(j);
            }
        }
    }

    void dfs(int cur) {
        used[cur] = true;
        for (int i = 0; i < 27; i++) {
            if (!used[graph[cur][i]] && graph[cur][i] != 0) {
                dfs(graph[cur][i]);
            }
        }
    }

    int main() {
        std::ios_base::sync_with_stdio(false);
        ifstream in ("fastminimization.in");
        ofstream out ("fastminimization.out");

        int n; // number of points
        in >> n;
        int m; // number of roads
        in >> m;
        int k; // number of ends
        in >> k;

        bool ends[50005] = {false};

        for (int i = 0; i < k; i++) {
            int e;
            in >> e;
            ends[e] = true;
        }
        for (int i = 0; i < m; i++) {
            int from;
            in >> from;
            int to;
            in >> to;
            char c;
            in >> c;
            graph[from][c - 97] = to;

            rg[to].insert(make_pair(from, c - 97));
        }

        dfs(1);
        for (int i = 1; i <= n; i++) {
            if (ends[i] && used[i]) {
                fromEnd(i);
            }
        }
        set<int> endsSet;
        set<int> otherSet;
        int classes[n + 1];
        for (int i = 1; i <= n; i++) {
            if (usefull[i]) {
                if (ends[i]) {
                    endsSet.insert(i);
                    classes[i] = 0;
                } else {
                    otherSet.insert(i);
                    classes[i] = 1;
                }
            }
        }

        vector<set<int>> P;
        P.push_back(endsSet);
        P.push_back(otherSet);

        queue<pair<int, int>> Queue;
        for (int symbol = 0; symbol < 27; symbol++) {
            Queue.push(make_pair(0, symbol));
            Queue.push(make_pair(1, symbol));
        }

        // Hopkroft...
        while (!Queue.empty()) {
            map<int, vector<int>> involved;
            pair<int, int> cur = Queue.front();
            Queue.pop();

            for (int q : P[cur.first]) {
                set<pair<int, int>> d = rg[q];
                if (!d.empty()) {
                    for (pair<int, int> cur_pair : d) {

                        int r = cur_pair.first;
                        if (usefull[r] && (cur_pair.second == cur.second)) {
                            int i = classes[r];
                            involved[i].push_back(r);
                        }
                    }
                }
            }
            for (const auto& i : involved) {
                if (i.second.size() < P[i.first].size()) {
                    int j = P.size();
                    set<int> wayToJ;
                    P.push_back(wayToJ);

                    for (int r : i.second) {
                        P[i.first].erase(r);
                        P[j].insert(r);
                    }

                    if (wayToJ.size() > P[i.first].size()) {
                        swap(P[i.first], P[j]);
                    }
                    for (int r : P[j]) {
                        classes[r] = j;
                    }
                    for (int c = 0; c < 27; c++) {
                        Queue.push(make_pair(j, c));
                    }
                }
            }
        }
        set<int> endsMin;
        // reNumbering
        int firstIn = classes[1];
        for (int i = 1; i <= n; i++) {
            if (usefull[i]) {
                if (classes[i] == 0) {
                    classes[i] = firstIn;
                } else if (classes[i] == firstIn) {
                    classes[i] = 0;
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (ends[i] && usefull[i]) {
                endsMin.insert(classes[i] + 1);
            }
        }
        string ans;
        int counterRoads = 0;
        int counterPoints = 0;
        int counterEnds = endsMin.size();

        for (const auto& ith : P) {
            if (!ith.empty()) {
                counterPoints++;

                int fromI = *ith.begin();

                for (int symbol = 0; symbol < 27; symbol++) {
                    int to = graph[fromI][symbol];
                    if (to != 0 && usefull[to]) {
                        counterRoads++;
                        ans += to_string(classes[fromI] + 1);
                        ans += " ";
                        ans += to_string(classes[to] + 1);
                        ans += " ";
                        ans.push_back((char)(symbol + 97));
                        ans += "\n";
                    }
                }
            }
        }
        out << counterPoints <<  " " <<  counterRoads <<  " " << counterEnds << endl;
        for (auto i : endsMin) {
            out << i << " ";
        }
        out << endl;
        out << ans;
    }