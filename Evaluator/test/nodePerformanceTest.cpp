#include <Evaluator/Node.h>
#include <Evaluator/main.h>

// Code here is mainly to compare performance between various methods, techniques, and algorithms

// 5*6-4/9*11+33
void advancedNodeMaths(){
    Multiplication multA = Multiplication(5, 6);
    Division divA = Division(4, 9);
    Multiplication multB = Multiplication(divA.evaluate(), 11);
    Subtraction sub1 = Subtraction(multA.evaluate(), multB.evaluate());
    Addition add1 = Addition(sub1.evaluate(), 33);
}

void parseMaths(){
    advancedParser("5*6-4/9*11+33");
}

void listMaths(){
    polynomialEvaluator({5, multiply, 6, subtract, 4, divide, 9, multiply, 11, add, 33});
}