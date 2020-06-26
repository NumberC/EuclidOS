package Core;

import org.ejml.equation.Equation;
import org.ejml.simple.SimpleMatrix;

public class SystemSolver {
    String eq1 = "x+y+z = 100";
    String eq2 = "x+y-z = 50";
    String eq3 = "x-y-z = 10";
    double[] eq1Pre = {1, 1, 1};
    double[] eq2Pre = {1, 1, -1};
    double[] eq3Pre = {1, -1, -1};
    double[][] totals = {{100}, {50}, {10}};

    double[] eqPre;
    double[][] rTotals;

    String eq11 = "3x-y=6";
    String eq22 = "x+y=5";
    double[] eq11Pre = {3, -1};
    double[] eq22Pre = {1, 1};
    double[][] totals2 = {{6}, {5}};

    void setTerms(int n){
        eqPre = new double[n];
        rTotals = new double[n][1];
    }

    void process2(){
        SimpleMatrix finalMatrix = new SimpleMatrix(new double[][]{eq11Pre, eq22Pre});
        SimpleMatrix resultMatrix = new SimpleMatrix(totals2);

        Equation equation = new Equation();
        equation.alias(finalMatrix, "A");
        equation.alias(resultMatrix, "B");
        equation.process("β=inv(A)*B");
        equation.lookupSimple("β").print();
    }

    void process3(){
        SimpleMatrix finalMatrix = new SimpleMatrix(new double[][]{eq1Pre, eq2Pre, eq3Pre});
        SimpleMatrix resultMatrix = new SimpleMatrix(totals);

        Equation equation = new Equation();
        equation.alias(finalMatrix, "A");
        equation.alias(resultMatrix, "B");
        equation.process("β=inv(A)*B");
        equation.lookupSimple("β").print();
    }

    void regex(){
        // a = \d[a-zA-Z]
        // b =
        // c = =\w
    }
}
