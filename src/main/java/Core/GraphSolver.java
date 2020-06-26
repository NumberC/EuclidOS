package Core;

public class GraphSolver {
    FullEval eval = new FullEval();
    String eq;
    String totalStr;
    EquationSolver equationSolver = new EquationSolver();

    Double[][] solveDouble(String inEq){
        String[] eqParts = inEq.split("=");
        eq = eqParts[0];
        totalStr = eqParts[1];

        Double[][] results = new Double[40][2];
        int index = 0;

        for(double y = -10; y < 10; y += 0.5){
            String newXEq = eq.replaceAll("y", "(" + y + ")");
            String newXTotal = totalStr.replaceAll("y", "(" + y + ")");
            double resolve = equationSolver.solve(newXEq + "=" + newXTotal);

            //-10+10 = 0; (-10+10)*2
            print(resolve + ", " + y);
            results[index][0] = resolve;
            results[index][1] = y;
            index++;

//            for(double y = -10; y < 10; y += 0.1){
//                String newYEq = newXEq.replaceAll("y", "(" + y + ")");
//                String newYTotal = newXTotal.replaceAll("y", "(" + y + ")");
//
//            }
        }
        return results;
    }
    
    public double limitedSolve(String inEq, double y){
        String[] eqParts = inEq.split("=");
        eq = eqParts[0];
        totalStr = eqParts[1];

        String newXEq = eq.replaceAll("y", "(" + y + ")");
        String newXTotal = totalStr.replaceAll("y", "(" + y + ")");
        return equationSolver.solve(newXEq + "=" + newXTotal);
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
