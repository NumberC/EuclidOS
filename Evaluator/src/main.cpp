#include <iostream>
#include <vector>
#include <math.h>

#include <chrono>

#include <Evaluator/main.h>
#include <Evaluator/Node.h>

using namespace std;

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

void test(){
    Addition ad(5, 6);
    Multiplication a(60, 3);
    Multiplication b(a.evaluate(), 60);
    Multiplication c(17, 60);
    Equation eq = Equation({&b, &c});
    eq.evaluate();
}

int main(){
    Variable x(3);
    Addition ad(3, 7);
    Equation e = Equation({x});
    Sigma sig(1, 10, x, e);
    cout << sig.evaluate() << endl;
    
    //runPerformanceTests();
    return 0;
}