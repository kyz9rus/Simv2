package objects;

public class MatrixInfo {
    private int nestingLevel;
    private int[] M;
    private int[] N;

    public MatrixInfo (int nestingLevel, int[] M, int[] N){
        this.nestingLevel = nestingLevel;
        this.M = M;
        this.N = N;
    }

}
