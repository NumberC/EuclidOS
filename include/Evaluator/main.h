#include <string>
#include <vector>
using namespace std;

enum Operation {add, subtract, multiply, divide, raise};

double advancedParser(string s);
double polynomialEvaluator(vector<double> a);
void variableEvaluator(string s);
void getPerformance(void (*function) ());
void runTests();