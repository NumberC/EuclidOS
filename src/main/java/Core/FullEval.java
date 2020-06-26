package Core;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import org.ejml.equation.Equation;
import org.ejml.simple.SimpleMatrix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullEval {
    VariableOrganizer varOrganizer = new VariableOrganizer();
    Matrix m = new Matrix();
    SystemConfigurations configs = new SystemConfigurations();

    Operator factorial = new Operator("!", 1, true,Operator.PRECEDENCE_POWER + 1) {
        @Override
        public double apply(double... args) {
            if(args[0] < 0){
                throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
            } else if(args[0] > 1){
                double total = 1;
                for(int i = 1; i <= args[0]; i++){
                    total *= i;
                }
                return total;
            } else{
                return 1;
            }
        }
    };

    public String evaluate(String eq){
        if(eq.contains("->")){
            return storeVars(eq)+"";
        }

        if(eq.contains("<-U/D")){
            eq = eq.replaceAll("<-U/D", "");
        }

        //TODO Matrix and Function don't work; Ex. [A]*FACT(5) = Error
        eq = varOrganizer.useData(eq);
        print(eq);
        //eq = specialMath(eq);

        eq = parenthesisFix(eq);
        if(eq.contains("[")){
            return matrixEval(eq)+"";
        } else{
            return normalEval(eq)+"";
        }
    }

    public Double graphEval(String eq, double x){
        eq = eq.replaceAll("y=", "");
        return evalVar(eq, x);
    }

    String specialMath(String eq){
        //Symbols
        eq = eq.replaceAll("√", "SQRT");
        eq = eq.replaceAll("π", "(PI)"); //TODO: matrix if π*[P]*[I]
        eq = eq.replaceAll("℮", "(E)");

        //Operations
        eq = eq.replaceAll("\\|(.*?)\\|", "(ABS($1))");
        eq = eq.replaceAll("((\\(.*?\\))|(\\w+))!", "(FACT($1))"); // (((\(.*?)?(\w|\)))*)!
        eq = eq.replaceAll("\\[(.*?)]\\^\\(?-1\\)?", "(inv([$1]))");

        String sigmaRegex = "SUM\\(\\s*?(\\(?.*?\\)?),\\s*?(\\(?.*?\\)?),\\s*?\"(.*?)\"\\)";
        Pattern sigmaPattern = Pattern.compile(sigmaRegex);
        Matcher sigmaMatcher = sigmaPattern.matcher(eq);

        //SUM(1, 10, "X")
        while(sigmaMatcher.find()){
            double start = Double.parseDouble(sigmaMatcher.group(1).replaceAll("(\\(|\\))", ""));
            double end = Double.parseDouble(sigmaMatcher.group(2).replaceAll("(\\(|\\))", ""));
            String formula = sigmaMatcher.group(3);
            eq = eq.replace(sigmaMatcher.group()+"", sigma((int) Math.round(start), (int) Math.round(end), formula)+"");
        }

        //print(eq);
        return eq;
    }

    Double sigma(int start, int end, String eq){
        print(eq);
        double total = 0;
        for(int i = start; i <= end; i++){
            String tempEq = eq.replaceAll("X", "("+ i + ")");
            try{
                total += Double.parseDouble(evaluate(tempEq));
            } catch(Exception e){
                return null;
            }
        }
        return total;
    }

    public SimpleMatrix matrixEval(String eq){
        eq = checkForMatrix(eq);
        Equation ejmlEq = new Equation();
        for(char i : eq.toCharArray()){
            if(Character.isUpperCase(i)){
                ejmlEq.alias(new SimpleMatrix(m.getMatrix(i+"")), i+"");
            }
        }
        try {
            ejmlEq.process("β="+eq);
            ejmlEq.lookupSimple("β").print();
            return ejmlEq.lookupSimple("β");
        } catch(Exception e){
            print("Error");
            return null;
        }
    }

    String parenthesisFix(String eq){
        int openPara = 0;
        int closePara = 0;
        for(char i : eq.toCharArray()){
            if(i == '('){
                openPara++;
            } else if(i == ')'){
                closePara++;
            }
        }
        if(openPara > closePara){
            eq = eq + ")";
        }
        return eq;
    }

    public Double normalEval(String eq){
        //eq = eq.toLowerCase();
        Expression e = new ExpressionBuilder(eq)
                .operator(factorial)
                .build();
        return e.evaluate();
    }

    Double evalVar(String eq, Double x){
        try{
            Expression e = new ExpressionBuilder(eq).variable("x").build().setVariable("x", x);
            return e.evaluate();
        } catch(Exception e){
            return null;
        }
    }

    String checkForMatrix(String eq){
        eq = eq.replaceAll("\\[", "");
        eq = eq.replaceAll("]", "");

        return eq;
    }

    Double storeVars(String eq){
        String[] storeArray = eq.split("->");
        Double val = normalEval(storeArray[0]);
        if(val == null){
            return null;
        }
        varOrganizer.store(storeArray[1], val);
        return val;
    }

    //TODO simplify 1/9
    public String toFraction(double num){
        //0.866666
        String numS = Double.toString(num);
        String[] numParts = numS.split("\\.");
        String unitPart = numParts[0];
        String fracPart = numParts[1];

        int lengthOfFrac= fracPart.length();

        // fractionRepeating(lengthOfFrac, fracPart, false)

        double numerator = Double.parseDouble(fracPart);
        double denominator = Math.pow(10, fracPart.length());

        String fraction = (denominator*Double.parseDouble(unitPart) + numerator) + "/" + denominator;
        print(fraction);
        print("error3");
        print("WOAH!");
        print(numerator);
        print(denominator);
        return fractionSimplify(fraction);
    }

    double fractionRepeating(String fracPart, boolean isDenom){
        //(90*num)/90
        //5.09090909 -> 5+ 9/99 -> 5 + 1/11

        int longestSame = 0;
        int currentSame = 0;
        int lengthOfFrac = fracPart.length();
        double numerator = Double.parseDouble(fracPart);
        double denominator = Math.pow(10, fracPart.length());
        Character previous = null;
        for(int i = 0; i < lengthOfFrac; i++){
            if(previous == null){
                previous = fracPart.toCharArray()[i];
                currentSame++;
            } else if(fracPart.toCharArray()[i] == previous){
                currentSame++;
            } else{
                longestSame = currentSame;
                currentSame = 0;
            }
        }
        longestSame = Math.max(currentSame, longestSame);

        return isDenom ? denominator:numerator;
    }

    String fractionSimplify(String frac){
        String[] fracParts = frac.split("/");
        double numerator = Double.parseDouble(fracParts[0]);
        double denominator = Double.parseDouble(fracParts[1]);
        double lowest = Math.min(numerator, denominator);
        String simplifiedFrac = frac;
        //If lowest is 0?
        for(int i = 1; i <= lowest; i++){
            if(numerator % i == 0 && denominator % i == 0){
                simplifiedFrac = numerator/i + "/" + denominator/i;
            }
        }
        return simplifiedFrac;
    }

    static void print(Object p){
        System.out.println(p);
    }
}
