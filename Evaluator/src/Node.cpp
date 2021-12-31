#include <Evaluator/Node.h>
#include <math.h>
#include <iostream>

double EquationNode::evaluate(){return 1;}
double EquationNode::evaluateWithValue(double x){
    return evaluate();
}

Variable::Variable(){
}

// TODO: why do I need this?
Variable::Variable(int x){
    value = x;
}

void Variable::changeValue(double x){
    value = x;
}

double Variable::evaluate(){
    std::cout << "Var Value: " << value << std::endl;
    return value;
}

double Variable::evaluateWithValue(double x){
    return x;
}

Equation::Equation(vector<EquationNode> components){
    this->components = components;
}

Equation::Equation(){}

double Equation::evaluate(){
    double result = 0;
    for(EquationNode eq : components){
        result += eq.evaluate();
    }
    return result;
}

Addition::Addition(double a, double b){
    this->a = a;
    this->b = b;
}
double Addition::evaluate(){return a+b;}

Subtraction::Subtraction(double a, double b){
    this->a = a;
    this->b = b;
}
double Subtraction::evaluate(){return a-b;}

Multiplication::Multiplication(double a, double b){
    this->a = a;
    this->b = b;
}
double Multiplication::evaluate(){return a*b;}

Division::Division(double a, double b){
    this->a = a;
    this->b = b;
}
//TODO: how to handle this
double Division::evaluate(){
    if(b != 0) return a/b;
    else return -1;
}

Power::Power(double b, double e){
    this->b = b;
    this->e = e;
}

double Power::evaluate(){return pow(b, e);}

Root::Root(double radicand, double index){
    this->radicand = radicand;
    this->index = index;
}

double Root::evaluate(){
    if(index == 0) return -1;
    return pow(radicand, 1/index);
}

Log::Log(double base, double a){
    this->base = base;
    this->a = a;
}

double Log::evaluate(){
    if(base <= 0 || base == 1) return -1; //TODO: what if base is 1 and a is 1
    return log(a)/log(base);
}

Sigma::Sigma(double start, double n, Variable x, Equation e){
    this->start = start;
    this->n = n;
    this->e = e;
    this->x = x;
}

double Sigma::evaluate(){
    double sum = 0;
    double originalValue = x.evaluate();
    for(int i = start; i <= n; i++){
        x.changeValue(i);
        x.evaluate();
        sum += e.evaluate();
        std::cout << "Eq Value: " << e.evaluate() << std::endl;
    }
    x.changeValue(originalValue);
    return sum;
}