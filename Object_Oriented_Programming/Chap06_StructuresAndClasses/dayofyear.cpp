#include <iostream>
#include <cstdlib>
using namespace std;

class DayOfYear {
    public:
        DayOfYear(int monthValue, int dayValue);
        DayOfYear(int monthValue);
        DayOfYear();

        void input();
        void output() const;
        void set(int newMonth, int newDay);
        void set(int newMonth); // overloading

        int getMonthNumber() const;
        int getDay() const;
    
    private:
        int month;
        int day;
        void testDate(); // private's member function
};

// constructor definition
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

// accessor definition
int DayOfYear::getMonthNumber() const { return month; }
int DayOfYear::getDay() const { return day; }

// mutator definition
void DayOfYear::set(int newMonth, int newDay) {
    if ((newMonth >= 1) && (newMonth <= 12))
        month = newMonth;
    else {cout << "Illigal month!\n"; exit(1);}

    if ((newDay >= 1) && (newDay <= 31))
        day = newDay;
    else {cout << "Illigal day!\n"; exit(1);}
}

// // ── testDate (private) ───────────────────────
void DayOfYear::testDate() {
    if ((month < 1) || (month > 12)) {
        cout << "Illegal month value!\n"; exit(1);
    }
    if ((day < 1) || (day > 31)) {
        cout << "Illegal day value!\n"; exit(1);
    }
}

// ── main ─────────────────────────────────────
int main() {
    DayOfYear date1(2, 21), date2(5), date3;

    cout << "Initialized dates:\n";
    date1.output(); cout << endl;   // February 21
    date2.output(); cout << endl;   // May 1
    date3.output(); cout << endl;   // January 1

    date1 = DayOfYear(10, 31);      // 명시적 생성자 호출 (익명 객체)
    cout << "date1 reset:\n";
    date1.output(); cout << endl;   // October 31

    // private 접근 시도 → 컴파일 오류 확인용
    // date1.month = 5;  // ❌

    return 0;
}