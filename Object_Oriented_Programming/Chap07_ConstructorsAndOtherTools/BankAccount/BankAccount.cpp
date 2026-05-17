#include <iostream>
#include <cmath>
#include <cstdlib>
#include "BankAccount.h"
using namespace std;

// Constructor Definition
BankAccount::BankAccount(double balance, double rate)
    : accountDollars(dollarsPart(balance)), accountCents(centsPart(balance)) {
        setRate(rate);
    }

BankAccount::BankAccount(int dollars, int cents, double rate) {
    setBalance(dollars, cents);
    setRate(rate);
}

BankAccount::BankAccount(int dollars, double rate)
    : accountDollars(dollars), accountCents(0) {
        setRate(rate);
}

BankAccount::BankAccount()
    : accountDollars(0), accountCents(0), rate(0.0) { }


// Function Definition
void BankAccount::update() {
    double balance = accountDollars + accountCents*0.01;
    balance = balance + fraction(rate)*balance;
    accountDollars = dollarsPart(balance);
    accountCents = centsPart(balance);
}

void BankAccount::input() {
    double balanceAsDouble;
    cout << "Enter account balance $";
    cin >> balanceAsDouble;
    accountDollars = dollarsPart(balanceAsDouble);
    accountCents = centsPart(balanceAsDouble);
    cout << "Enter interest rate (with NO percent sign): ";
    cin >> rate;
    setRate(rate);
}

void BankAccount::output() const {
    int absDollars = abs(accountDollars);
    int absCents = abs(accountCents);
    cout << "Account balance: $";

    if (accountDollars < 0)
        cout << "-";
    cout << absDollars;

    if (absCents >= 10)
        cout << "." << absCents << endl;
    else
        cout << "." << '0' << absCents << endl;

    cout << "Rate: " << rate << "%\n";
}

void BankAccount::setBalance(double balance) {
    accountDollars = dollarsPart(balance);
    accountCents = centsPart(balance);
}

void BankAccount::setBalance(int dollars, int cents) {
    if ((dollars < 0 && cents > 0) || (dollars > 0 && cents < 0)) {
        cout << "Inconsistent account data.\n";
        exit(1);
    }
    accountDollars = dollars;
    accountCents = cents;
}

void BankAccount::setRate(double newRate) {
    if (newRate >= 0.0)
        rate = newRate;
    else {
        cout << "Cannot have a negative insterest rate.\n";
        exit(1);
    }
}


// Function Definition - private
int BankAccount::centsPart(double amount) const {
    double doubleCents = amount*100;
    int intCents = (round(fabs(doubleCents))) % 100;
    
    // %(modular operator) can misbehave on negatives
    if (amount < 0)
        intCents = -intCents;
    return intCents;
}

// Function Definition - nonclass member
bool isLarger(const BankAccount &account1, const BankAccount &account2) {
    return account1.getBalance() > account2.getBalance();
}

void welcome(const BankAccount &yourAccount) {
    cout << "Welcome to our bank.\n"
         << "The status of your account is:\n";
    yourAccount.output();
}