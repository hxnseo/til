#include <iostream>
using namespace std;

// Driver Example
int main() {
    double diameter, price;
    char ans;

    do {
        cout << "Enter diameter and price:\n";
        cin >> diameter >> price;
        cout << "unit Price is $"
             << unitPrice(diameter, price) << endl;
        
        cout << "Test again? (y/n)";
        cin >> ans;
        cout << endl;
    } while (ans == 'y' || ans == 'Y');

    return 0;
}

double unitPrice(int diameter, double price) {
    const double PI = 3.14159;
    double radius, area;

    radius = diameter / static_cast<double>(2);
    area = PI * radius * radius;
    return (price/area);
}

// Stub Example (if the function body has not yet been implemented)
    // A stub. The final function definition must still be written.
double unitPrice(int diameter, double price) {
    return (9.99);
    // Not actually correct but good enough for a stub
}