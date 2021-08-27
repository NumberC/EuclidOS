#include <iostream>
#include <vector>
#include <math.h>

#include <string>
#include <regex>

#include <chrono>

#include <Evaluator/main.h>

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

int getOperationFromChar(char c){
	switch(c){
		case '+':
			return add;
		case '-':
			return subtract;
		case '*':
			return multiply;
		case '/':
			return divide;
		case '^':
			return raise;
	}
	
	return -1;
}

void simpleParser(string& s, smatch m) {
	int split_on = m.position();
	int operation = getOperationFromChar(m[3].str()[0]);

	double firstNumber = atof(m[1].str().c_str());
	double secondNumber = atof(m[4].str().c_str());
	
	double num = polynomialEvaluator({firstNumber, operation*1.0, secondNumber});
    
	s = s.substr(0, split_on) + to_string(num) + s.substr(split_on + m.length());
}

double advancedParser(string s){
	regex parantheses = regex("\\(([^()]+)\\)");
    regex multDivide = regex("([0-9]\\d*(\\.\\d+)?)(\\*|\\/)([0-9]\\d*(\\.\\d+)?)");
    regex addSubtract = regex("([0-9]\\d*(\\.\\d+)?)(\\+|\\-)([0-9]\\d*(\\.\\d+)?)");
	smatch m;

	while(regex_search(s, m, parantheses)){
		int split_on = m.position();

		s = s.substr(0, split_on) + to_string(advancedParser(m[1].str())) +
		s.substr(split_on + m.length());
	}
	while(regex_search(s, m, multDivide)) simpleParser(s, m);
	while(regex_search(s, m, addSubtract)) simpleParser(s, m);

    return atof(s.c_str());
}

void advanced(){advancedParser("((5*6)+34/6-(32+5-9/3))/6*100000");}

int main(){
    vector<double> a {8, divide, 7, add, 5, subtract, 4};
    cout << polynomialEvaluator(a) << endl;
	cout << advancedParser("((5*6)+34/6-(32+5-9/3))/6*100000") << endl;

    for(int i = 0; i < 1; i++){
        string test2;
        cin >> test2;
        cout << advancedParser(test2) << endl;
    }
    return 0;
}