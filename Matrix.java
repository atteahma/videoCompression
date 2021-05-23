public class Matrix {
    
    private int N;
    private int M;
    private double[] elems;
    private boolean transposed;

    public Matrix(int n, int m) {
        this.N = n;
        this.M = m;
        this.transposed = false;
        this.elems = new double[n * m];
    }

    public Matrix(int n, int m, double[] elems) {
        this(n,m);

        assert elems.length == n*m;

        this.elems = elems;
    }

    public Matrix(int n, int m, double[][] elems) {
        this(n,m);

        assert elems.length == n;
        for (int i = 0 ; i < elems.length ; i++) {
            assert elems[i].length == m;
        }

        for (int i = 0 ; i < elems.length ; i++) {
            for (int j = 0 ; j < elems[i].length ; j++) {
                this.elems[n * i + j] = elems[i][j];
            }
        }
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

        return this.elems[this.getN() * i + j];
    }

    public void setElem(int i, int j, double elem) {
        if (this.transposed) {
            int t = i;
            i = j;
            j = t;
        }

        this.elems[this.getN() * i + j] = elem;
    }

    public void transpose() {
        this.transposed = !this.transposed;
    }

    public void flatten() {
        int numElems = this.getN() * this.getM();
        this.N = 1;
        this.M = numElems;
        this.transposed = false;
    }
}
