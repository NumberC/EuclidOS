#include <iostream>
#include <vector>
#include <math.h>

#include <string>
#include <regex>

using namespace std;

enum Operation {add, subtract, multiply, divide, raise};

double binomialEvaluator(double a, Operation o, double b){
    //TODO: use smart pointers
    if(&a == nullptr || &o == nullptr || &b == nullptr) return NULL;
    double value = 0;

    switch(o){
        case add:
            return a+b;
            break;
        case subtract:
            return a-b;
            break;
        case multiply:
            return a*b;
            break;
        case divide:
            return a/b;
            break;
        case raise:
            return pow(a, b);
            break;
        default:
            return NULL;
    }
}

// breaks up polynomial into binomials and evaluates them using binomialEvaluator
double polynomialEvaluator(vector<double> a){
    double currentValue = a.at(0);
    Operation currentOperation = static_cast<Operation>(a.at(1));

    for(int i = 2; i < a.size(); i+=2){
        currentValue = binomialEvaluator(currentValue, currentOperation, a.at(i));

        if(i+1 < a.size())
            currentOperation = static_cast<Operation>(a.at(i+1));
    }

    return currentValue;
}

// \(([^()]+)\)
int main(){
    vector<double> a {8, divide, 7, add, 5, subtract, 4};
    cout << polynomialEvaluator(a) << endl;
    return 0;
}