public class Matrix {
    
    private int N;
    private int M;
    private double[][] elems;
    private boolean transposed;

    public Matrix(int n, int m) {
        this.N = n;
        this.M = m;
        this.transposed = false;
        this.elems = new double[n][m];
    }

    public Matrix(int n, int m, double[][] elems) {
        this(n,m);
        this.elems = elems;
    }

    public int getN() {
        int n;

        if (this.transposed) {
            n = this.M;
        } else {
            n = this.N;
        }

        return n;
    }

    public int getM() {
        int m;

        if (this.transposed) {
            m = this.N;
        } else {
            m = this.M;
        }

        return m;
    }

    public double getElem(int i, int j) {
        if (this.transposed) {
            int t = i;
            i = j;
            j = t;
        }

        return this.elems[i][j];
    }

    public void setElem(int i, int j, double elem) {
        if (this.transposed) {
            int t = i;
            i = j;
            j = t;
        }

        this.elems[i][j] = elem;
    }

    public Matrix multiply(Matrix other) {
        
        assert this.getM() == other.getN();

        int outN = this.getN();
        int outM = other.getM();
        int L = this.getM();

        Matrix out = new Matrix(outN,outM);
        double elem;

        for (int i = 0 ; i < outN ; i++) {
            for (int j = 0 ; j < outM ; j++) {

                elem = 0;

                for (int k = 0 ; k < L ; k++) {
                    elem += this.getElem(i,k) * other.getElem(k,j);
                }

                out.setElem(i,j,elem);
            }
        }

        return out;
    }

    public void transpose() {
        this.transposed = !this.transposed;
    }
}
