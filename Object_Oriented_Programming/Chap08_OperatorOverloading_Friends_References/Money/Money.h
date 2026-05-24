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
        void output() const;
        void input();

        friend const Money operator +(const Money &amount1, const Money &amount2);
        friend const Money operator -(const Money &amount1, const Money &amount2);
        friend bool operator ==(const Money &amount1, const Money &amount2);
        friend const Money operator -(const Money &amount);
        friend ostream &operator << (ostream &outputStream, const Money &amount);
        friend istream &operator >> (istream &inputStream, Money &amount);

    private:
        int dollars;
        int cents;
        int dollarsPart(double amount) const;
        int centsPart(double amount) const;
        int round(double number) const;
};