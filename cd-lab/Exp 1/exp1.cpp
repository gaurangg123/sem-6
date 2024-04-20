#include <iostream>
using namespace std;

int main()
{
    char op;
    float num1, num2;

    cout << "enter the operator\n(+ - * /)" << endl;
    cin >> op;
    cout << "enter 1st number" << endl;
    cin >> num1;
    cout << "enter 2nd number" << endl;
    cin >> num2;

    switch (op)
    {
    case '+':
        cout << "The Result is " << num1 + num2;
        break;

    case '-':
        cout << "The Result is " << num1 - num2;
        break;

    case '*':
        cout << "The Result is " << num1 * num2;
        break;

    case '/':
        cout << "The Result is " << num1 / num2;
        break;
    }
    return 0;
}