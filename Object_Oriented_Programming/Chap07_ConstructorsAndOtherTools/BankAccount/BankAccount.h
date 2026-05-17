#include <iostream>
#include <cmath>
#include <cstdlib>
using namespace std;

class BankAccount {
    public:
        // For a negative balance both dollars and cents must be negative.
        BankAccount(double balance, double rate);
        BankAccount(int dollars, int cents, double rate);
        BankAccount(int dollars, double rate);
        BankAccount();

        // Postcondition: Interest is compounded yearly
        void update();
        void input();
        void output() const;
        
        // inline member functions
        double getBalance() const {
            return (accountDollars + accountCents*0.01);
        }
        int getDollars() const { return accountDollars; }
        int getCents() const { return accountCents; }
        double getRate() const { return rate; }

        // Checks that arguments are both nonnegative or both nonpositive
        void setBalance(double balance);
        void setBalance(int dollars, int cents);

        // Abort program if newRate is negative
        void setRate(double newRate);

    private:
        int accountDollars;
        int accountCents;
        double rate;

        // inline member functions
        int dollarsPart(double amount) const {
            return static_cast<int>(amount);
        }
        
        int centsPart(double amount) const;
        
        int round(double number) const {
            return static_cast<int>(floor(number+0.5));
        }

        double fraction(double percent) const {
            return (percent/100.0);
        }
};

bool isLarger(const BankAccount &account1, const BankAccount &account2);
void welcome(const BankAccount &yourAccount);