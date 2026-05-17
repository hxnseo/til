#include <iostream>
using namespace std;

class Server {
    public:
        Server(char lettername);
        static int getTurn();
        static bool stillOpen();
        void serveOne();
    
    private:
        static int turn;
        static int lastServed;
        static bool nowOpen;
        char name;
};