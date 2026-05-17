#include <iostream>
using namespace std;

int main() {
    const int CURRENT_YEAR = 2026;
    int birthyear;
    int age;

    cout << "Enter the year you were born.";
    cin >> birthyear;

    age = (CURRENT_YEAR - birthyear) + 1;
    cout << "Your age is " << age << endl;

    return 0;
}