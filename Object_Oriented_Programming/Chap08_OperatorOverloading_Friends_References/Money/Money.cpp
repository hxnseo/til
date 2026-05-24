#include <iostream>
#include "Money.h"
using namespace std;

// Constructor Definition
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

// Function Definition
const Money operator +(const Money &amount1, const Money &amount2) {
    int allCents1 = amount1.cents + amount1.dollars*100;
    int allCents2 = amount2.cents + amount2.dollars*100;
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
    return ((amount1.dollars == amount2.dollars) && (amount1.cents == amount2.cents));
}

const Money operator -(const Money &amount1, const Money &amount2) {
    int allCents1 = amount1.dollars * 100 + amount1.cents;
    int allCents2 = amount2.dollars * 100 + amount2.cents;

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
    return Money(-amount.dollars, -amount.cents);
}

ostream &operator << (ostream &outputStream, const Money &amount) {
    int absDollars = abs(amount.dollars); // amount가 Money class 타입 객체니까 .dollars로 그 객체 안의 달러 값만 빼오는 것
    int absCents = abs(amount.cents);
        // 부호는 맨 앞에 한 번만 붙이고, 나머지 숫자들은 절댓값으로 출력하려고 abs() 사용
    if (amount.dollars < 0 || amount.cents < 0) {
        outputStream << "$-";
    }
    else {
        outputStream << '$';
    }
    outputStream << absDollars;
    
    if (absCents >= 10) {
        outputStream << '.' << absCents;
    }
    else {
        outputStream << '.' << '0' << absCents;
    }

    return outputStream;
}

istream &operator >> (istream &inputStream, Money &amount) {
    char dollarSign;
    inputStream >> dollarSign;
    if (dollarSign != '$') {
        cout << "No dollar sign in Money input.\n";
        exit(1);
    }

    double amountAsDouble;
    inputStream >> amountAsDouble;
    amount.dollars = amount.dollarsPart(amountAsDouble);
    amount.cents = amount.centsPart(amountAsDouble);

    return inputStream;
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