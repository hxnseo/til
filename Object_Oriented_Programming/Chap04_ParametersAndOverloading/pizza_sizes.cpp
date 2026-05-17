// Determines which of two pizza sizes is the best buy.
    // 굳이 이렇게 번거롭게 선언-변수설정 반복해야하나?

#include <iostream>
using namespace std;

// Function declaration
    // Returns the price per square inch of a pizza
    // Precondition: the diameter parameter is the diameter of the pizza in inches. 
        // the price parameter is the price of the pizza
void getData(int& smallDiameter, double& priceSmall,
            int& largeDiameter, double& priceLarge);

void giveResults(int smallDiameter, double priceSmall,
                int largeDiameter, double priceLarge);

    // overloading
double unitPrice(int diameter, double price);
    // Returns the price per square inch of a round pizza
double unitPrice(int length, int width, double price);
    // Returns the price per squate inch of a ractangular pizza


// Implementation
int main() {
    int diameterSmall, diameterLarge;
    double priceSmall, priceLarge;

    getData(diameterSmall, priceSmall, diameterLarge, priceLarge);
    giveResults(diameterSmall, priceSmall, diameterLarge, priceLarge);

    return 0;
}


// Function Definition
void getData(int& smallDiameter, double& priceSmall,
            int& largeDiameter, double& priceLarge) {
                cout << "Welcome to the Pizza Consumers Union.\n";
                
                cout << "Enter diameter of a small pizza (in inches): ";
                cin >> smallDiameter;
                cout << "Enter the price of a small pizza: $";
                cin >> priceSmall;

                cout << "Enter diameter of a large pizza (in inches): ";
                cin >> largeDiameter;
                cout << "Enter the price of a large pizza: $";
                cin >> priceLarge;
            }

void giveResults(int smallDiameter, double priceSmall,
                int largeDiameter, double priceLarge)                
{
    double unitPriceSmall, unitPriceLarge;

    // One function called within another function
    unitPriceSmall = unitPrice(smallDiameter, priceSmall);
    unitPriceLarge = unitPrice(largeDiameter, priceLarge);

    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);

    cout << "Small pizza:\n"
         << "Diameter = " << smallDiameter << " inches\n"
         << "Price = $" << priceSmall << ", per square inch = $" << unitPriceSmall << endl;
    cout << "Large pizza:\n"
         << "Diameter = " << largeDiameter << " inches\n"
         << "Price = $" << priceLarge << ", per square inch = $" << unitPriceLarge << endl;
    
    if (unitPriceLarge < unitPriceSmall)
        cout << "The large one is the better buy.\n";
    else
        cout << "The small one is the better buy.\n";
    cout << "Buon Appetito!\n";
}

double unitPrice(int diameter, double price) {
    const double PI = 3.14159;
    double radius, area;

    radius = diameter / static_cast<double>(2);
    area = PI * radius * radius;
    return (price/area);
}

double unitPrice(int length, int width, double price) {
    double area = length * width;
    return (price/area);
}