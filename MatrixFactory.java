public class MatrixFactory {
    
    // static class
    private MatrixFactory() {}

    public static Matrix Identity(int n) {
        Matrix M = new Matrix(n,n);
        for (int i = 0 ; i < n ; i++) {
            M.setElem(i,i,1);
        }
        return M;
    }

}
