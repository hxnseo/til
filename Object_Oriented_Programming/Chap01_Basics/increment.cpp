#include <iostream>
using namespace std;

int main() {
    int n = 2;
    int valueProduced = 2 * (n++);
    cout << valueProduced << "\n";
    cout << n << "\n";    

    int m = 5;
    int n = 2;
    int valueProduced = (m--) * (++n);
        // 4 * 3 = 12
        // m = 5? n = 3
}