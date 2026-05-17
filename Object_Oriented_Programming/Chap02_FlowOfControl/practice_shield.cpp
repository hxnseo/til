#include <iostream>
using namespace std;

int main() {
    int current_shield;
    int warpSpeed = 0;

    cout << "Enter the shield energy (0~100): ";
    cin >> current_shield;

    if (current_shield >= 80) {
        cout << "Status: Safe\n";
    } else if (current_shield >= 50) {
        cout << "Status: Alert\n";
    } else if (current_shield >= 20) {
        cout << "Status: Warning\n";
    } else {
        cout << "Status: Critical\n";
    }

    warpSpeed = (current_shield <= 10)? 9:1;

    cout << "Set Warp Speed: " << warpSpeed;

    return 0;
}