#include <iostream>
using namespace std;

class CharPair {
    public:
        CharPair() { }
        CharPair(char firstValue, char secondValue)
            : first(firstValue), second(secondValue) { }
        char &operator[](int index);
    
    private:
        char first;
        char second;
};

char &CharPair::operator[](int index) {
    if (index == 1) {
        return first;
    }
    else if (index == 2) {
        return second;
    }
    else {
        cout << "Illigal index value.\n";
        exit(1);
    }
}

int main() {
    CharPair a;
    a[1] = 'A';
    a[2] = 'B';
    cout << "a[1] and a[2] are:\n";
    cout << a[1] << a[2] << endl;

    cout << "Enter two letters (with no spaces):\n";
    cin >> a[1] >> a[2];
    cout << "You entered:\n";
    cout << a[1] << a[2] << endl;

    return 0;
}