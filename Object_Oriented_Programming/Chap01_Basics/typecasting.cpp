#include <iostream>
using namespace std;

int main() {
    int intVar1 = 9;
    int intVar2 = 2;
    
    cout << (static_cast<double> (intVar1) / intVar2) << endl;
        // 변수 하나를 먼저 형변환하자

    double myDouble = 9.3;
    int myInt = (int) myDouble;
}