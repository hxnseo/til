#include <iostream>
using namespace std;

enum class Menu {STEW = 1, BREAD = 2, SALAD = 3, BACON = 4};

int main() {
    int number;

    cout << "Select the menu number[1: STEW, 2: BREAD, 3: SALAD, 4: BACON] ";
    cin >> number;

    switch (number) {
        case static_cast<int>(Menu::STEW):
        cout << "Here's the magic stew that can be a dinosaur :D";
        break;

        case static_cast<int>(Menu::BREAD):
        cout << "The hotest bread is coming!";
        break;

        case static_cast<int>(Menu::SALAD):
        cout << "Do you really want salad for sure?";
        break;

        case static_cast<int>(Menu::BACON):
        cout << "Bacon must be the best choice.";
        break;

        default:
        cout << "There's no menu like that. Sorry.";
    }

    return 0;
}