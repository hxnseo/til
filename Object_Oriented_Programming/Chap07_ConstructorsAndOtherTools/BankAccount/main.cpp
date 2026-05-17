#include <iostream>
#include <cmath>
#include <cstdlib>
#include "BankAccount.h"
using namespace std;

int main() {
    BankAccount account1(1345.52, 2.3), account2;
    welcome(account1);

    cout << "account1 initialized as follows:\n";
    account1.output();
    cout << "account2 initialized as follows:\n";
    account2.output();

    account1 = BankAccount(999, 99, 5.5);
    cout << "account1 reset to the following:\n";
    account1.output();

    cout << "Enter new data for account2:\n";
    account2.input();
    cout << "account2 reset to the following:\n";
    account2.output();
    account2.update();
    cout << "In one year account2 will grow to:\n";
    account2.output();

    if (isLarger(account1, account2))
        cout << "account1 is larger.\n";
    else
        cout << "account2 is at least as large as account1.\n";
    
    return 0;
}