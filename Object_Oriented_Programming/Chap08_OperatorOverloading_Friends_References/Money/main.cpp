#include <iostream>
#include "Money.h"
using namespace std;

int main() {
    Money yourAmount, myAmount(10, 9);
    cout << "Enter an amount of money: ";
    cin >> yourAmount;
    cout << "Your amount is: " << yourAmount << endl;
    
    cout << "My amount is: " << myAmount << endl;

    if (yourAmount == myAmount) {
        cout << "We have the same amounts.\n";
    }
    else {
        cout << "One of us is richer.\n";
    }

    Money ourAmount = yourAmount + myAmount;
    cout << yourAmount << " + " << myAmount << " equals " << ourAmount << endl;

    Money diffAmount = yourAmount - myAmount;
    cout << yourAmount << " - " << myAmount << " equals " << diffAmount << endl;

    return 0;
}