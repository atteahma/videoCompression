public final class LinearAlgebra {
    
    // static class
    private LinearAlgebra() {}

    private static double aggregate(Matrix mat, AggFunc aggFunc, double initial) {
        int N = mat.getN();
        int M = mat.getM();
        
        double out = initial;

        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < M ; j++) {
                out = aggFunc.call(out,mat.getElem(i,j));
            }
        }

        return out;
    }

    public static double mean(Matrix mat) {
        AggFunc sumAggFunc = new Sum();
        double totalSum = aggregate(mat, sumAggFunc, 1);
        int numElems = mat.getN() * mat.getM();

        return totalSum / numElems;        
    }

}

interface AggFunc {
    public double call(double curr, double elem);
}

class Sum implements AggFunc {
    public double call(double curr, double elem) {
        return curr + elem;
    }
}

