#include <iostream>
using namespace std;

void showTheWorld(const int a[], int sizeOfa)
// Precondition: sizeOfa is the declared size of the array a.
    // All indexed variables of a have been given values.
// Postcondition: The values in a have been written to the screen.
{
    cout << "The array contains the following values:\n";
    for (int i = 0; i < sizeOfa; i++) {
        // If there's a[i]++, it will be mistake, but the compiler will not catch it unless you use the const modifier.
        cout << a[i] << " ";
        cout << endl;
    }
}