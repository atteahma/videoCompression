public final class LinearAlgebra {
    
    // static class
    private LinearAlgebra() {}

    private static double aggregate(Matrix M, AggFunc aggFunc, double initial) {
        int n = M.getN();
        int m = M.getM();
        
        double out = initial;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                out = aggFunc.call(out,M.getElem(i,j));
            }
        }

        return out;
    }


    // axis is the axis along which to take the mean
    public static Matrix mean(Matrix M, int axis) {
        
        Matrix O;

        if (axis == -1) {
            AggFunc sumAggFunc = new Sum();
            double totalSum = aggregate(M, sumAggFunc, 1);
            int numElems = M.getN() * M.getM();
            double mean = totalSum / numElems;

            O = new Matrix(1,1);
            O.setElem(0,0,mean);
        } else {
            // refactor this to generalized methods like axis == -1 case
            int n = M.getN();
            int m = M.getM();
            double sum;

            if (axis == 0) {
                O = new Matrix(1,m);
                
            } else {
                O = new Matrix(n,1);
            }

            for ()
        }

        return O;
    }

    public static Matrix add(Matrix A, Matrix B) {
        
        assert A.getN() == B.getN();
        assert A.getM() == B.getM();

        int n = A.getN();
        int m = A.getM();

        Matrix O = new Matrix(n,m);
        double elem;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                elem = A.getElem(i,j) + B.getElem(i,j);
                O.setElem(i,j,elem);
            }
        }

        return O;
    }

    public static Matrix multiply(Matrix A, Matrix B) {
        
        assert A.getM() == B.getN();

        int outN = A.getN();
        int outM = B.getM();
        int L = A.getM();

        Matrix O = new Matrix(outN,outM);
        double elem;
        for (int i = 0 ; i < outN ; i++) {
            for (int j = 0 ; j < outM ; j++) {

                elem = 0;

                for (int k = 0 ; k < L ; k++) {
                    elem += A.getElem(i,k) * B.getElem(k,j);
                }

                O.setElem(i,j,elem);
            }
        }

        return O;
    }

    public static Matrix add(Matrix M, double c) {

        int n = M.getN();
        int m = M.getM();

        Matrix O = new Matrix(n,m);
        double elem;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                elem = M.getElem(i,j) + c;
                O.setElem(i,j,elem);
            }
        }

        return O;
    }

    public static Matrix multiply(Matrix M, double c) {
        
        int n = M.getN();
        int m = M.getM();

        Matrix O = new Matrix(n,m);
        double elem;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {

                elem = M.getElem(i,j) * c;

                O.setElem(i,j,elem);
            }
        }

        return O;
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

