package objects;

import java.util.Objects;
import java.util.function.Supplier;

public class Matrix<T> extends Operand{
    private int m, n;
    private T matrix[][];

    private final Supplier<? extends T> ctor = null;

    public Matrix(int m, int n){
        this.m = m;
        this.n = n;
        matrix = (T[][]) ctor.get();
    }

    public Matrix(Supplier<? extends T> ctor) {
        ctor = Objects.requireNonNull(ctor);
    }

    int getN() {
        return n;
    }

    int getM() {
        return m;
    }

    void setElement(int i, int j, T element){
        matrix[i][j] = element;
    }

    T getElement(int i, int j){
        return matrix[i][j];
    }
}