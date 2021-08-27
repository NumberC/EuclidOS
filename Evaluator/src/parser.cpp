#include <string>
#include <iostream>

#include <Evaluator/main.h>

using namespace std;

void variableEvaluator(string s){
    for(double i = -10; i <= 10; i+=0.1){
        string adjustedString = s;
        while(adjustedString.find("x") != string::npos){ 
            int index = adjustedString.find("x");
            adjustedString.replace(index, 1, "(" + to_string(i) + ")");

            advancedParser(adjustedString);
        }
    }
}

void variableEvaluator(){
    for(double i = -10; i <= 10; i+=0.1){
        //polynomialEvaluator({5, add, i, subtract, 4});
        //cout << polynomialEvaluator({5, add, i, subtract, 4}) << endl;
    }
}

int mainTest(){
    return 0;
}