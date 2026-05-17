#include <iostream>
using namespace std;

int main() {
    int grade[NUMBER_STUDENTS][NUMBER_QUIZES];
    double stAve[NUMBER_STUDENTS];
    double quizAve[NUMBER_QUIZES];

    computeStAve(grade, stAve);
    computeQuizAve(grade, quizAve);
    display(grade, stAve, quizAve);
    
    return 0;
}

// Function Definition
void computeStAve(const int grade[][NUMBER_QUIZES], double stAve[]) {
    for (int stNum = 1; stNum <= NUMBER_STUDENTS; stNum++) {
        // Process one stNum:
        double sum = 0;
        for (int quizNum = 1; quizNum <= NUMBER_QUIZES; quizNum++) {
            // sum contains the sum of the grades for student number(stNum)
            sum = sum + grade[stNum-1][quizNum-1];
        }

        // Average for student stNum is the value of stAve[stNum-1]
        stAve[stNum-1] = sum / NUMBER_QUIZES;
    }