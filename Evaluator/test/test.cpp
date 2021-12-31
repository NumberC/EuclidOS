#include <iostream>
#include <chrono>

#include <Evaluator/main.h>
#include <Evaluator/Node.h>

using namespace std;

// Code here is mainly to compare performance between various methods, techniques, and algorithms

void getPerformance(void (*function) ()){
    const auto now = chrono::system_clock::now().time_since_epoch(); 
    function();
    const auto after = chrono::system_clock::now().time_since_epoch(); 

    cout << chrono::duration_cast<chrono::milliseconds>(after - now).count() << endl; 
}

void advanced(){advancedParser("((5*6)+34/6-(32+5-9/3))/6*100000");}
void subAdvanced(){advancedParser("5*6");}
void listTest(){polynomialEvaluator({5, multiply, 6});}


void runPerformanceTests(){
    getPerformance(subAdvanced);
    getPerformance(listTest);
    
    Variable x(3);
    Addition ad(5, 3);
    Equation eq = Equation({ad, x});
    std::cout << eq.evaluate() << std::endl;
}