public class LinearAlgebraAdv {
    
    // static class
    private LinearAlgebraAdv() {}

    /*
        axis is the side along which you want to mean over

        axis = 0 mean along lines
        | | | |
        | | | |
        | | | |
        | | | |

        axis = 1 mean along lines
        --------
        --------
        --------
        --------
    */
    public static Matrix center(Matrix M, int axis) {
        Matrix means = LinearAlgebra.mean(M,axis);
        Matrix meansNeg = LinearAlgebra.multiply(means, -1);
        Matrix centeredM = LinearAlgebra.add(M,meansNeg);

        return centeredM;
    }

    /*
        axis is the side of the matrix's variables
        that you want the covariance of

                   axis=1
                m1 m2 m3 m4
        a   n1
        x   n2
        i   n3
        s   n4
        =   n5
        0   n6
    */
    public static Matrix covariance(Matrix M, int axis) {
        //Matrix centeredM = LinearAlgebraAdv.center(M);

        Matrix transposeM = new Matrix(M);
        transposeM.transpose();

        Matrix covariance = null;
        if (axis == 0) {
            covariance = LinearAlgebra.multiply(M,transposeM);
        } else if (axis == 1) {
            covariance = LinearAlgebra.multiply(transposeM,M);
        }

        return covariance;
    }

    // using modified gramm schmidt (oldie but a goodie)
    public static Matrix[] qrDecomposition(Matrix M) {

        int n = M.getN();
        int m = M.getM();

        assert n >= m;

        double s;
        double qTemp;
        double rTemp;
        double mTemp;
        Matrix Q = new Matrix(n,m);
        Matrix R = new Matrix(m,m);

        for (int k = 0 ; k < m ; k++) {

            s = 0;
            for (int j = 0 ; j < n ; j++) {
                s = s + M.getElem(j,k);
            }
            
            rTemp = Math.sqrt(s);
            R.setElem(k,k,rTemp);

            for (int j = 0 ; j < n ; j++) {
                qTemp = M.getElem(j,k) / R.getElem(k,k);
                Q.setElem(j,k,qTemp);
            }

            for (int i = k+1 ; i < n ; i++) {

                s = 0;
                for (int j = 0 ; j < m ; j++) {
                    s = s + M.getElem(j,i) * Q.getElem(j,k);
                }

                R.setElem(k,i,s);

                for (int j = 0 ; j < m ; j++) {
                    mTemp = M.getElem(j,i) - R.getElem(k,i) * Q.getElem(j,k);
                    M.setElem(j,i,mTemp);
                }
            }
        }

        Matrix[] QR = {Q,R};

        return QR;
    }

    // only symmetric matrices (for now)
    public static Matrix[] eigenDecomposition(Matrix M) {
        
        // check M is symmetric
        Matrix transposeM = new Matrix(M);
        transposeM.transpose();
        assert M.getN() == M.getM();
        assert LinearAlgebra.equal(M,transposeM);
        
        // initialize iterative matrices
        int L = M.getN();
        Matrix eigenvalues = new Matrix(M);
        Matrix eigenvectors = MatrixFactory.Identity(L);
        Matrix[] QR;
        Matrix Q;
        Matrix R;
        double tol = 0.0001;

        // iterate
        while ( !LinearAlgebra.isDiagonal(eigenvalues, tol) ) {
            QR = LinearAlgebraAdv.qrDecomposition(eigenvalues);
            Q = QR[0];
            R = QR[1];
            eigenvectors = LinearAlgebra.multiply(eigenvectors, Q);
            eigenvalues = LinearAlgebra.multiply(R, Q);
        }

        Matrix[] eigenDecomp = {eigenvalues,eigenvectors};

        return eigenDecomp;
    }

    public static Matrix pca(Matrix M) {
        
        Matrix centeredM = LinearAlgebraAdv.center(M,1);
        Matrix covariance = LinearAlgebraAdv.covariance(centeredM,1);

        Matrix[] eigenDecomp = LinearAlgebraAdv.eigenDecomposition(covariance);
        Matrix eigenvalues = eigenDecomp[0];
        Matrix eigenvectors = eigenDecomp[1];

        return null;
    }

}
