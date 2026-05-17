#include <iostream>
#include <cstdlib>
#include <cmath>
using namespace std;

class Money {
    public:
        Money();
        Money(double amount);
        Money(int theDollars, int theCents);
        Money(int theDollars);
        double getAmount() const;
        int getDollars() const;
        int getCents() const;
        void input();
        void output() const;
    
    private:
        int dollars;
        int cents;
        int dollarsPart(double amount) const;
        int centsPart(double amount) const;
        int round(double number) const;
};