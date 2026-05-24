#include <iostream>
using namespace std;

class IntPair {
    public:
        IntPair(int firstValue, int secondValue);
        
        IntPair operator ++(); // Prefix version
        IntPair operator ++(int); // Postfix version
        
        void setFirst(int newValue);
        void setSecond(int newValue);
        int getFirst() const;
        int getSecond() const;
    
    private:
        int first;
        int second;
};

IntPair IntPair::operator ++() {
    first++;
    second++;

    return IntPair(first, second);
}

IntPair IntPair::operator ++(int ignoreMe) {
    int temp1 = first;
    int temp2 = second;

    first++;
    second++;

    return IntPair(temp1, temp2);
}