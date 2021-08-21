#include <iostream>
#include <chrono>

using namespace std;

// void getPerformance(void (*function) ()){
//     const auto now = chrono::system_clock::now().time_since_epoch(); 
//     function();
//     const auto after = chrono::system_clock::now().time_since_epoch(); 

//     cout << chrono::duration_cast<chrono::milliseconds>(after - now).count() << endl; 
// }

// void testStringParser(){advancedParser("5+6");}
// void testEvaluator(){polynomialEvaluator({5, add, 6});}

// int test(){
//     getPerformance(testStringParser);
//     getPerformance(testEvaluator);
//     cout << polynomialEvaluator({5, 1, 6}) << endl;

//     return 0;
// }
