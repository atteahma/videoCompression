public class LinearAlgebraAdv {
    
    // static class
    private LinearAlgebraAdv() {}

    public static Matrix center(Matrix M) {
        Matrix means = LinearAlgebra.mean(M,0);
        Matrix meansNeg = LinearAlgebra.multiply(means, -1);
        Matrix centeredM = LinearAlgebra.add(M,meansNeg);

        return centeredM;
    }

    public static Matrix covariance(Matrix M) {
        //Matrix centeredM = LinearAlgebraAdv.center(M);

        Matrix transposeM = new Matrix(M);
        transposeM.transpose();
        Matrix covariance = LinearAlgebra.multiply(transposeM, M);

        return covariance;
    }

    public static Matrix[] eigenDecomposition(Matrix M) {
        // todo
        Matrix eigenvalues = null;
        Matrix eigenvectors = null;
        Matrix[] eigenDecomp = {eigenvalues,eigenvectors};
        return eigenDecomp;
    }

    public static Matrix pca(Matrix M) {
        
        Matrix centeredM = LinearAlgebraAdv.center(M);
        Matrix covariance = LinearAlgebraAdv.covariance(centeredM);
        Matrix[] eigenDecomp = LinearAlgebraAdv.eigenDecomposition(covariance);
        Matrix eigenvalues = eigenDecomp[0];
        Matrix eigenvectors = eigenDecomp[1];
    }

}
