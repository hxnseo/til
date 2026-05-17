#include <iostream>
#include "Server.h"
using namespace std;

int main() {
    Server s1('A'), s2('B');
    int number, count;

    do {
        cout << "How many in your group? ";
        cin >> number;
        cout << "Your turns are: ";
        for (count = 0; count < number; count++) {
            cout << Server::getTurn() << ' ';
        }
        cout << endl;
        s1.serveOne();
        s2.serveOne();
    } while (Server::stillOpen());

    cout << "Now closing the service.\n";

    return 0;
}