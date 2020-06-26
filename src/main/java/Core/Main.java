package Core;

public class Main {
    public static void main(String[] args){
        //TODO: find roots other than square roots
        // implement log base anything
        // style everything if possible
        // a^2b is a^(2b) instead of (a^2)b

        //{History, Mode, GraphSettings} history stuff

        FullEval eval = new FullEval();
        print(eval.evaluate("x+56/2"));

//        EquationSolver eqSolver = new EquationSolver();
//        long start = System.currentTimeMillis();
//        eqSolver.solve("x^3-x=-3");
//        long end = System.currentTimeMillis();
//        print((end-start) + "ms");

        /*
        GraphSolver solver = new GraphSolver();
        long start = System.currentTimeMillis();
        Double[][] results = solver.solveDouble("x^2+y^2=5"); //x^2+y^2=9"
        long end = System.currentTimeMillis();
        print((end-start) + "ms");

        print("------------------------------");
        for(Double[] coords : results){
            print(coords[0]);
            print(coords[1]);
        }*/
    }

    static void print(Object p){ System.out.println(p); }
}
