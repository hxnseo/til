#include <iostream>
#include "Money.h"
using namespace std;

// Function Declaration (Global function)
const Money operator +(const Money &ammount1, const Money &amount2);
const Money operator -(const Money &amount1, const Money &amount2);
bool operator ==(const Money &amount1, const Money &amount2);
const Money operator -(const Money &amount);

// Function Definition
const Money operator +(const Money &amount1, const Money &amount2) {
    int allCents1 = amount1.getDollars() * 100 + amount1.getCents();
    int allCents2 = amount2.getDollars() * 100 + amount2.getCents();

    int totalAllCents = allCents1 + allCents2;
    int absAllCents = abs(totalAllCents);
    int finalDollars = absAllCents / 100;
    int finalCents = absAllCents % 100;

    if (totalAllCents < 0) {
        finalDollars = -finalDollars;
        finalCents = -finalCents;
    }

    return Money(finalDollars, finalCents);
}

bool operator ==(const Money &amount1, const Money &amount2) {
    return (amount1.getDollars() == amount2.getDollars()) && (amount1.getCents() == amount2.getCents());
}

const Money operator -(const Money &amount1, const Money &amount2) {
    int allCents1 = amount1.getDollars() * 100 + amount1.getCents();
    int allCents2 = amount2.getDollars() * 100 + amount2.getCents();

    int totalAllCents = allCents1 - allCents2;
    int absAllCents = abs(totalAllCents);
    int finalDollars = absAllCents / 100;
    int finalCents = absAllCents % 100;

    if (totalAllCents < 0) {
        finalDollars = -finalDollars;
        finalCents = -finalCents;
    }

    return Money(finalDollars, finalCents);
}

const Money operator -(const Money &amount) {
    return Money(-amount.getDollars(), -amount.getCents());
}

int main() {
    Money yourAmount, myAmount(10, 9);
    cout << "Enter an amount of money: ";
    yourAmount.input();
    cout << "Your amount is: ";
    yourAmount.output();
    cout << endl;

    cout << "My amount is: ";
    myAmount.output();
    cout << endl;

    if (yourAmount == myAmount) {
        cout << "We have the same amounts.\n";
    }
    else {
        cout << "One of us is richer.\n";
    }

    Money ourAmount = yourAmount + myAmount;
    yourAmount.output(); cout << " + "; myAmount.output();
    cout << " equals "; ourAmount.output(); cout << endl;

    Money diffAmount = yourAmount - myAmount;
    yourAmount.output(); cout << " - "; myAmount.output();
    cout << " equals "; diffAmount.output(); cout << endl;

    return 0;
}