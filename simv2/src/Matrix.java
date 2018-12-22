class Matrix {
    private int n, m;
    private double matrix[][];

    Matrix(){ }

    Matrix(int m, int n){
        this.m = m;
        this.n = n;
        matrix = new double[m][n];
    }

    int getN() {
        return n;
    }

    int getM() {
        return m;
    }

    void setElement(int i, int j, double number){
        matrix[i][j] = number;
    }

    double getElement(int i, int j){
        return matrix[i][j];
    }
}