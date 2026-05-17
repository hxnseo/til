#include <iostream>
using namespace std;

struct CDAccountV1
{
    double balance;
    double interestRate;
    int term;
};

// 함수 선언만 (theAccount) 일단 해둠
void getData(CDAccountV1& theAccount);

int main()
{
    CDAccountV1 account;
    
    // 사용자에게 balance, interestRate, term 입력받기
    getData(account);

    // 계산용 임시 변수임
    double rateFraction, interest;

    // 이자율 계산
    rateFraction = account.interestRate / 100.0;
    interest = account.balance*(rateFraction*(account.term/12.0));
    account.balance = account.balance + interest;
    
    // formatting numbers
    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);

    // 이자율 계산한 것 출력
    cout << "When your CD matures in "
         << account.term << " months, "
         << "it will have a balance of $"
         << account.balance << endl;

    return 0;
}

// Uses iostream
void getData(CDAccountV1& theAccount)
{
    cout << "Enter account balance: $";
    cin >> theAccount.balance;
    cout << "Enter account interest rate: ";
    cin >> theAccount.interestRate;
    cout << "Enter the number of months until maturity: ";
    cin >> theAccount.term;
}