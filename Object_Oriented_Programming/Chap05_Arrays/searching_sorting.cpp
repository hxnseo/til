#include <iostream>
using namespace std;

int search(const int a[], int numberUsed, int target) {
    int index = 0;
    bool found = false;

    while ((!found) && (index < numberUsed)) {
        if (target == a[index])
            found = true;
        else
            index++;
        
        if (found)
            return index;
        else
            return -1;
    }
}

int indexOfSmallest(const int a[], int startIndex, int numberUsed) {
    int min = a[startIndex], indexOfMin = startIndex;

    for (int index = startIndex + 1; index < numberUsed; index++) {
        if (a[index] < min) {
            min = a[index];
            indexOfMin = index;
        }
    }

    return indexOfMin;
}

void selectionSort(int a[], int numberUsed) {
    int indexOfNextSmallest;

    for (int index = 0; index < numberUsed - 1; index++) {
        // Place the correct value in a[index]
        indexOfNextSmallest = indexOfSmallest(a, index, numberUsed);

        swapValues(a[index], a[indexOfNextSmallest]);
        // Original array elements. The rest of the elements are in the remaining positions
    }
}

void swapValues(int& v1, int& v2) {
    int temp;
    temp = v1;
    v1 = v2;
    v2 = temp;
}

void bubbleSort(int arr[], int length) {
    // Bubble larger number toward the right
    for (int i = length-1; i > 0; i--) {
        for (int j = 0; j < i; j++) {
            if (arr[j] > arr[j+1]) {
                // Swap the numbers
                int temp = arr[j+1];
                arr[j+1] = arr[j];
                arr[j] = temp;
            }
        }
    }
}