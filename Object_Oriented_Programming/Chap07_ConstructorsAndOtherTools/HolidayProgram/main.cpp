#include <iostream>
#include <cstdlib>
#include "DayOfYear.h"
#include "Holiday.h"
using namespace std;

int main() {
    DayOfYear date1(2, 21), date2(5), date3;
    cout << "Initialized dates:\n";
    date1.output(); cout << endl;
    date2.output(); cout << endl;
    date3.output(); cout << endl;

    // Explicit Constructor Call
    date1 = DayOfYear(10, 31);
    cout << "date1 reset to the following:\n";
    date1.output(); cout << endl;
    
    Holiday h(2, 14, true);
    cout << "Testing the class Holiday.\n";
    h.output();
    return 0;
}


// Constructor Definition
DayOfYear::DayOfYear(int monthValue, int dayValue)
    : month(monthValue), day(dayValue) {
        testDate();
    }

DayOfYear::DayOfYear(int monthValue)
    : month(monthValue), day(1) {
        testDate();
    }

DayOfYear::DayOfYear()
    : month(1), day(1) { }

Holiday::Holiday(int month, int day, bool theEnforcement)
    : date(month, day), parkingEnforcement(theEnforcement) { }

Holiday::Holiday()
    : date(1,1), parkingEnforcement(false) { }


// Function Definition
void DayOfYear::testDate() {
    if ((month < 1) || (month > 12)) {
        cout << "Illegal month value!\n";
        exit(1);
    }

    if ((day < 1) || (day > 31)) {
        cout << "Illegal day value!\n";
        exit(1);
    }
}

int DayOfYear::getMonthNumber() {
    return month;
}

int DayOfYear::getDay() {
    return day;
}

void DayOfYear::input() {
    cout << "Enter the month as a number: ";
    cin >> month;
    cout << "Enter the day of the month: ";
    cin >> day;

    if ((month < 1) || (month > 12) || (day < 1) || (day > 31)) {
        cout << "Illegal date! Program aborted.\n";
        exit(1);
    }
}

void DayOfYear::output() {
    switch (month) {
        case 1: cout << "January "; break;
        case 2: cout << "Feburary "; break;
        case 3: cout << "March "; break;
        case 4: cout << "April "; break;
        case 5: cout << "May "; break;
        case 6: cout << "June "; break;
        case 7: cout << "July "; break;
        case 8: cout << "August "; break;
        case 9: cout << "September "; break;
        case 10: cout << "October "; break;
        case 11: cout << "November "; break;
        case 12: cout << "December "; break;

        default:
            cout << "Error in DayOfYear::output.";
    }

    cout << day;
}

void Holiday::output() {
    date.output();
    cout << endl;
    if (parkingEnforcement)
        cout << "Parking laws will be enforced.\n";
    else
        cout << "Parking laws will not be enforced.\n";
}