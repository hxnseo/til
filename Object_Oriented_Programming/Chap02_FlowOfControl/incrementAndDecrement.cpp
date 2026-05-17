#include <iostream>
using namespace std;

int main() {
    int numberOfItems, count, caloriesForItem, totalCalories;

    cout << "How many items did you eat today? ";
    cin >> numberOfItems;

    totalCalories = 0;
    count = 1;

    cout << "Enter the number of calories in each of the "
         << numberOfItems
         << " items eaten: ";

    while (count++ <= numberOfItems) {
        cin >> caloriesForItem;
        totalCalories += caloriesForItem;
    }

    cout << "Total calories eaten today: " << totalCalories << " kcal.";

    return 0;
}