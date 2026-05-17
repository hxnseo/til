#pragma once
#include <iostream>
#include <cstdlib>
using namespace std;

class DayOfYear {
    public:
    // Constructor Declaration
        DayOfYear(int monthValue, int dayValue);

        DayOfYear(int monthValue);

        DayOfYear();

        void input();
        void output();
        int getMonthNumber();
        int getDay();

    private:
        int month;
        int day;
        void testDate();
};