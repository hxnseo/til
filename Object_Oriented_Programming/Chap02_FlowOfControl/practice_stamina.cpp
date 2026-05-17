#include <iostream>
using namespace std;

int main() {
    int stamina = 100;
    int enteringGame;

    do {
        cout << "광산에 입장하여 채굴을 시작하시겠습니까? (1: 예, 0: 아니오) ";
        cin >> enteringGame;

        if (enteringGame == 0)
        {
            cout << "게임을 종료합니다.\n";
            return 0;
        }

        while (stamina > 0) {
        for (int i = 1; i < 6; i++) {
            cout << "Dig ";
        }
            stamina = stamina - 30;
            cout << "\nCurrent Stamina: "
                 << stamina << endl;        
        }
        cout << "체력이 소진되어 광산을 나갑니다.\n";
        enteringGame = 0;
    } while (enteringGame != 0);

    return 0;
}