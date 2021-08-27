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