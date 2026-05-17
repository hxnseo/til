#include <iostream>
#include "Money.h"
using namespace std;

Money::Money()
    : dollars(0), cents(0) { }

Money::Money(double amount)
    : dollars(dollarsPart(amount)), cents(centsPart(amount)) { }

Money::Money(int theDollars)
    : dollars(theDollars), cents(0) { }

Money::Money(int theDollars, int theCents) {
    if ((theDollars < 0 && theCents > 0) ||
        (theDollars > 0 && theCents < 0)) {
            cout << "Inconsistent money data.\n";
            exit(1);
        }

        dollars = theDollars;
        cents = theCents;
}

double Money::getAmount() const {
    return (dollars + cents*0.01);
}

int Money::getDollars() const {
    return dollars;
}

int Money::getCents() const {
    return cents;
}

void Money::input() {
    char dollarSign;
    cin >> dollarSign;
    if (dollarSign != '$') {
        cout << "No dollar sign in Money input.\n";
        exit(1);
    }

    double amountAsDouble;
    cin >> amountAsDouble;
    dollars = dollarsPart(amountAsDouble);
    cents = centsPart(amountAsDouble);
}

void Money::output() const {
    int absDollars = abs(dollars);
    int absCents = abs(cents);
    
    if (dollars < 0 || cents > 0)
        // accounts for dollars == 0 or cents == 0
        cout << "$-";
    else
        cout << '$';
    cout << absDollars;

    if (absCents >= 10)
        cout << '.' << absCents;
    else
        cout << '.' << '0' << absCents;
}

int Money::dollarsPart(double amount) const {
    return static_cast<int>(amount);
}

int Money::centsPart(double amount) const {
    double doubleCents = amount*100;
    int intCents = (round(fabs(doubleCents))) % 100;
    
    // %(modular operator) can misbehave on negatives
    if (amount < 0)
        intCents = -intCents;
    return intCents;
}

int Money::round(double number) const {
    return static_cast<int>(floor(number+0.5));
}