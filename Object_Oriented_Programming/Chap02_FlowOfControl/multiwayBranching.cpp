#include <string>
#include <iostream>

using namespace std;

int temperature;
string day;

const string WEEKEND = "SUNDAY";

int main() {
    
    cout << "What day is it?: ";
    cin >> day;

    cout << "Enter the temperature on today: ";
    cin >> temperature;

    if ((temperature < -10) && (day == WEEKEND)) {
    cout << "Stay home.";
}
else if (temperature < -10) {
    cout << "Stay home, but call work.";
}
else if (temperature <= 0) {
    cout << "Dress warm.";
}
else {
    cout << "Work hard and play hard.";
}

}