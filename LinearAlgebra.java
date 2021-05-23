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
            // trash repeating code
            int n = M.getN();
            int m = M.getM();
            int l;
            double total;

            if (axis == 0) {
                O = new Matrix(1,m);
                
                for (int k = 0 ; k < m ; k++) {

                    for (int i = 0 ; i < n ; i++) {
                        total += M.getElem(i,k);
                    }
                    O.setElem(0,k,total/n);
                }

            } else {
                O = new Matrix(n,1);
                
                for (int k = 0 ; k < n ; k++) {

                    for (int i = 0 ; i < m ; i++) {
                        total += M.getElem(k,i);
                    }
                    O.setElem(k,0,total/m);
                }
            }
        }

        return O;
    }

    public static boolean equal(Matrix A, Matrix B) {
        
        if (A.getN() != B.getN()) {
            return false;
        }

        if (A.getM() != B.getM()) {
            return false;
        }

        int n = A.getN();
        int m = A.getM();

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                if (A.getElem(i,j) != B.getElem(i,j)) {
                    return false;
                }
            }
        }

        return true;
    }

    // A must be a matrix, B can be a row vector, column vector,
    // or matrix of same shape
    public static Matrix add(Matrix A, Matrix B) {
        
        boolean rowVector = false;
        boolean colVector = false;

        if (A.getN() != B.getN()) {
            if (B.getN() == 1) {
                // we can project the row vector B
                rowVector = true;
            } else {
                // cannot do anything
                return null;
            }
        }
        if (A.getM() != B.getM()) {
            if (B.getM() == 1) {
                // we can project the col vector B
                colVector = true;
            } else {
                // cannot do anything
                return null;
            }
        }

        assert !(rowVector && colVector);

        int n = A.getN();
        int m = A.getM();

        Matrix O = new Matrix(n,m);
        double elem;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {

                if (rowVector) {
                    elem = A.getElem(i,j) + B.getElem(0,j);
                } else if (colVector) {
                    elem = A.getElem(i,j) + B.getElem(i,0);
                } else {
                    elem = A.getElem(i,j) + B.getElem(i,j);
                }

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

    public static boolean isDiagonal(Matrix M, double tol) {

        assert tol >= 0;

        int n = M.getN();
        int m = M.getM();

        double elem;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                if (i == j) {
                    continue;
                }

                elem = M.getElem(i,j);
                if (elem > tol) {
                    return false;
                }
            }
        }

        return true;
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
