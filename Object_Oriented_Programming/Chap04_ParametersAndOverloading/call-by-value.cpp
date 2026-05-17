// Law office billing program
#include <iostream>
using namespace std;

// Dollers per quarter hour
const double RATE = 150.00;

// Returns the charges for hoursWorked hours and minutesWorked minutes of legal services
double fee(int hoursWorked, int minutesWorked);

// implementation
int main() {
    int hours, minutes;
    double bill;

    cout << "Welcome to the law office of\n"
         << "Dewey, Cheatham, and Howe.\n"
         << "The law office with a heart.\n"
         << "Enter the hours and minutes of your consultation: ";
    cin >> hours >> minutes;

    bill = fee(hours, minutes);

    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);
    cout << "For " << hours << " hours and " << minutes << " minutes, your bill is $" << bill << endl;

    return 0;
}

// function definition
double fee(int hoursWorked, int minutesWorked) {
    int quarterHours;

    minutesWorked = hoursWorked*60 + minutesWorked;
    quarterHours = minutesWorked / 15;
    return (quarterHours*RATE);
}