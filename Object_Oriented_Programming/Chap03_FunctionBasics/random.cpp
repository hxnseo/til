#include <cstdlib>
#include <iostream>
using namespace std;

int main() {
    int i;
    srand(99);
    
    for (i = 0; i < 10; i++) {
        cout << (rand() % 11) << endl;
    }

    for (i = 0; i < 10; i++) {
        cout << (rand() % 11) << endl;
    }
 }