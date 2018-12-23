package objects;

public class Matrix<T> extends Operand {
    private int m, n;
    private T matrix[][];

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        matrix = (T[][]) new Object[m][n];
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public void setElement(int i, int j, T element) {
        matrix[i][j] = element;
    }

    public T getElement(int i, int j) { return matrix[i][j]; }
}