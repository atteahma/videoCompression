public class Vector extends Matrix {

    public Vector(int n) {
        super(n,1);
    }

    public Vector(int n, double[] elems) {
        this(n);

        for (int i = 0 ; i < n ; i++) {
            this.setElem(i,0,elems[i]);
        }
    }

    public double getElem(int i) {
        return this.getElem(i,0);
    }
    
    public void setElem(int i, double elem) {
        this.setElem(i,0,elem);
    }
}
