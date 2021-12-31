#include <vector>

using namespace std;

#ifndef Node_H
#define Node_H

class EquationNode{
    public:
        virtual double evaluate();
        virtual double evaluateWithValue(double x);
        virtual ~EquationNode(){};
};

class Variable : public EquationNode{
    public:
        double value;
        double evaluate();
        void changeValue(double x);
        double evaluateWithValue(double x);
        Variable();
        Variable(int x);
};

class Equation {
    private:
        vector<EquationNode> components;
    public:
        double evaluate();
        Equation(vector<EquationNode> components);
        Equation();
};

class Addition : public EquationNode{
    private:
        double a;
        double b;
    public:
        double evaluate();
        Addition(double a, double b);
        
};

class Subtraction : public EquationNode{
    private:
        double a;
        double b;
    public:
        double evaluate();
        Subtraction(double a, double b);
        
};

class Multiplication : public EquationNode{
    private:
        double a;
        double b;
    public: 
        double evaluate();
        Multiplication(double a, double b);
};

class Division : public EquationNode{
    private:
        double a;
        double b;
    public: 
        double evaluate();
        Division(double a, double b);
};

class Power : public EquationNode{
    private:
        double b;
        double e;
    public:
        double evaluate();
        Power(double b, double e);
};

class Root : public EquationNode {
    private:
        double radicand;
        double index;
    public:
        double evaluate();
        Root(double radicand, double index);
};

class Log : public EquationNode {
    private:
        double base;
        double a;
    public:
        double evaluate();
        Log(double base, double a);
};

// TODO: experimental
class Sigma : public EquationNode{
    private:
        double start;
        double n;
        Variable x;
        Equation e;
    public:
        double evaluate();
        Sigma(double start, double n, Variable x, Equation e);
};
#endif