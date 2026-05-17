#include <iostream>
#include "DayOfYear.h"

class Holiday {
    public:
        Holiday(); // Initializes to Jan 1 with no parking enforcement
        Holiday(int month, int day, bool theEnforcement);
        void output();
    
    private:
        DayOfYear date;
        bool parkingEnforcement; // Ture if enforced
};