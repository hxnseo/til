#include <iostream>
using namespace std;

// cannot declare function inside the function even if it's main()
void showResults(double fDegrees, double cDegrees) {
        cout.setf(ios::fixed);
        cout.setf(ios::showpoint);
        cout.precision(1);

        cout << fDegrees
             << " degrees Fahrenheit is equivalent to\n"
             << cDegrees << " degress Celsius.\n";
    }

void icecreamDivision(int number, double totalWeight);
// Outputs instructions for dividing totalWeight ounces ~~ (Preconditions and Postconditions)

void icecreamDivision(int number, double totalWeight) {
    double portion;

    if (number == 0) {
        cout << "Cannot divide among zero customers.\n";
        return;
        // if number is 0, then the function execution ends here
        // however, return 문을 저렇게 조건문 안에 쓰는게 좋은 습관은 아님
    }

    portion = totalWeight / number;
    cout << "Each one receives "
         << portion << " ounces of ice cream." << endl;
}