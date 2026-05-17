int hrs;
double grossPay;
double rate = 3.3;

int main() {
    if (hrs > 40)
    {
        grossPay = rate * 40 + 1.5 * rate * (hrs - 40);
    }
    
    else
    {
        grossPay = rate * hrs;
    }
}