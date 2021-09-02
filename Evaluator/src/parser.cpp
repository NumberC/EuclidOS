#include <string>
#include <iostream>
#include <regex>

#include <Evaluator/main.h>

using namespace std;

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