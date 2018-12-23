package models;

import objects.*;

import java.util.List;
import java.util.Optional;

public class CalcModel{
    private Expression expression;
    private MatrixInfo matrixInfo;
    private Matrix result;

    public CalcModel(Expression expression, MatrixInfo matrixInfo){
        this.expression = expression;
        this.matrixInfo = matrixInfo;
    }

    public void calculate() throws ParseException {
        result = (Matrix) getNode(expression);
    }

    Operand getNode(Expression expression) throws ParseException {
        Matrix resultMatrix = null;

        Optional<Expression> subExpression;
        do{
            Operation operation = expression.getOperation();
            List<Operand> operands = expression.getOperands();
            subExpression = Optional.ofNullable(expression.getSubExpression());

            if (subExpression.isPresent()){
                operands.add(getNode(subExpression.get()));
            } else {
                switch (operation.getName()){
                    case "add":

                        break;
                    case "sub":

                        break;
                    case "mul":

                        break;
                    case "transpose":

                        break;
                    case "mulNumber":

                        break;
                    case "expontiate":

                        break;

                    default:
                        throw new ParseException("Неопознанный тег");
                }
            }


        }while(subExpression.isPresent());

        return resultMatrix;
    }


    static Matrix add(List<Matrix> matrices){
//        for (int i = 0; i < matrices.size()-1; i++)
//            if (matrices.get(i).getM() != matrices.get(i+1).getM() || matrices.get(i).getN() != matrices.get(i+1).getN()){
////              setOutputModel(new OutputModel(null, "Сложение матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)"));
//                return null;
//            }
//
        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
        Matrix resultMatrix = new Matrix(M, N);
//
//        for (int i = 0; i < M; i++)
//            for (int j = 0; j < N; j++) {
//
//                double sum = 0;
//                for (int c = 0; c < matrices.size(); c++)
//                    sum += matrices.get(c).getElement(i, j);
//
//                resultMatrix.setElement(i, j, sum);
//            }
//
        return resultMatrix;
    }
//    static Matrix subtract(List<Matrix> matrices){
//        for (int i = 0; i < matrices.size()-1; i++)
//            if (matrices.get(i).getM() != matrices.get(i+1).getM() || matrices.get(i).getN() != matrices.get(i+1).getN()){
////                setOutputModel(new OutputModel(null, "Вычитание матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)"));
//                return null;
//            }
//
//        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
//        Matrix resultMatrix = new Matrix(M, N);
//
//        for (int i = 0; i < M; i++)
//            for (int j = 0; j < N; j++) {
//
//                double diff = matrices.get(0).getElement(i, j);
//                for (int c = 1; c < matrices.size(); c++)
//                    diff -= matrices.get(c).getElement(i, j);
//
//                resultMatrix.setElement(i, j, diff);
//            }
//
//        return resultMatrix;
//    }
//    static Matrix multiplicate(Matrix m1, Matrix m2){
//        if (m1.getN() != m2.getM()){
////            setOutputModel(new OutputModel(null, "Умножение матриц невозможно (количество элементов в строке первой матрицы, должно равняться количесту элементов столбца второй"));
//            return null;
//        }
//
//        int M = m1.getM(), K = m2.getN(), N = m2.getM();
//        Matrix resultMatrix = new Matrix(M, K);
//
//        double sum = 0;
//        for (int i = 0; i < M; i++)
//            for (int j = 0; j < K; j++) {
//                for (int c = 0; c < N; c++)
//                    sum += m1.getElement(i, c) * m2.getElement(c, j); //
//
//                resultMatrix.setElement(i, j, sum);
//                sum = 0;
//            }
//
//        return resultMatrix;
//    }
//    static Matrix multiplicateAll(List<Matrix> matrices){
//        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
//        Matrix resultMatrix = matrices.get(0);
//
//        for (int i = 1; i < matrices.size(); i++)
//            resultMatrix = multiplicate(resultMatrix, matrices.get(i));
//
//
//        return resultMatrix;
//    }
//    static Matrix multiplicateByNumber(Matrix m, double number){
//        int M = m.getM(), N = m.getN();
//
//        for (int i = 0; i < M; i++)
//            for (int j = 0; j < N; j++)
//                m.setElement(i, j, m.getElement(i, j) * number);
//
//        return m;
//    }
//    static Matrix transpose(Matrix m){
//        int M = m.getM(), N = m.getN();
//        Matrix resultMatrix = new Matrix(N, M);
//
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < M; j++)
//                resultMatrix.setElement(i, j, m.getElement(j, i));
//        return resultMatrix;
//    }
//    static Matrix expontiate(Matrix m, int power){
//        Matrix matrix = m;
//
//        for (int i = 0; i < power-1; i++)
//            matrix = multiplicate(matrix, m);
//
//        return matrix;
//    }
}