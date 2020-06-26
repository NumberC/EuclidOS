package Core;

public class EquationSolver {
    FullEval eval = new FullEval();
    String eq;
    String totalStr;

    //TODO: lower limit check
    // fix a lot
    // not working for:
    // 3.14=9x and 9x-34=9210 and x^3-x=-3

    public double solve(String inEq){
        String[] eqParts = inEq.split("=");
        eq = eqParts[0];
        totalStr = eqParts[1];

        double total;
        double lowerLim = -10;
        double upperLim = upperLimitCheck(eq, totalStr, 1000);
        double x = lowerLim;

        for(int i = 0; i < 100; i++){
            String newEq = eq.replaceAll("x", "(" + x + ")");
            double newTotal = eval.normalEval(newEq);
            total = eval.normalEval(totalStr.replaceAll("x", "(" + x +")"));

            if(newTotal == total){
                System.out.println(x);
                print("EUREKA!");
                break;
            } else if(newTotal > total){
                print("GREATER: " + newTotal + " WITH: " + x);
                if(eval.normalEval(eq.replaceAll("x", "(" + (x-.01) + ")")) > newTotal){
                    lowerLim = x;
                } else{
                    upperLim = x;
                }
            } else if (newTotal < total) {
                print("LOWER: " + newTotal + " WITH: " + x);
                if(eval.normalEval(eq.replaceAll("x", "(" + (x-.01) + ")")) > newTotal){
                    upperLim = x;
                } else{
                    lowerLim = x;
                }
            }

            print("L: " + lowerLim);
            print("U: " + upperLim);

            x = (upperLim-lowerLim)/2+lowerLim;
            print(newTotal);
            print(x);
        }
        return x;
    }

    double upperLimitCheck(String eq, String answer, double upperLimit){
        double newTotal = eval.normalEval(eq.replaceAll("x", "(" + upperLimit + ")"));
        if(newTotal > eval.normalEval(answer.replaceAll("x", "(" + upperLimit + ")"))){
            return upperLimit;
        } else{
            upperLimit *= 10;
            return upperLimitCheck(eq, answer, upperLimit);
        }
    }

    void print(Object p){
        System.out.println(p);
    }
}
