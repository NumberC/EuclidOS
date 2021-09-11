#include <iostream>
#include <chrono>

#include <Evaluator/main.h>

using namespace std;

void getPerformance(void (*function) ()){
    const auto now = chrono::system_clock::now().time_since_epoch(); 
    function();
    const auto after = chrono::system_clock::now().time_since_epoch(); 

    cout << chrono::duration_cast<chrono::milliseconds>(after - now).count() << endl; 
}

void advanced(){advancedParser("((5*6)+34/6-(32+5-9/3))/6*100000");}
void subAdvanced(){advancedParser("5*6");}
void listTest(){polynomialEvaluator({5, multiply, 6});}


void runTests(){
    getPerformance(subAdvanced);
    getPerformance(listTest);
}