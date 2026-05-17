#include <iostream>
using namespace std;

// this is function declaration; also called the function prototype
double totalCost(int numberParameter, double priceParameter);
    // computes the total cost, including 5% sales tax

// function head and body(which includes definition)
double totalCost(int numberParameter, double priceParameter) {
    const double TAXRATE = 0.05;
    double subtotal;

    subtotal = priceParameter * numberParameter;
    return (subtotal + subtotal*TAXRATE);
}

int main() {
    double price, bill;
    int number;

    cout << "Enter the number of items purchased: ";
    cin >> number;
    cout << "Enter the price per item $";
    cin >> price;

    // function call
    bill = totalCost(number, price);

    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);
    cout << number << " items at "
         << "$" << price << " each.\n"
         << "Final bill, including tax, is $" << bill
         << endl;
    
    return 0;
}