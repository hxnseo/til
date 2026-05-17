#include <iostream>
using namespace std;

int numberOfGames = 3;
double price = 3.85;
int tax = 5;

int main() {
    cout << numberOfGames << " games played." << endl;

    cout << "Total cost: $" << (price + tax);
    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);
}