package models;

import objects.*;
import objects.Number;

import java.util.ArrayList;
import java.util.List;

public class CalcModel {
    private Expression expression;
    private Matrix result;

    public CalcModel(Expression expression) {
        this.expression = expression;
    }

    public void calculate() throws ParseException {
        result = (Matrix) getNode(expression);
    }

    Operand getNode(Expression expression) throws ParseException {
        Matrix resultMatrix;

        Expression subExpression;

        Operation operation = expression.getOperation();
        List<Operand> operands = expression.getOperands();
        subExpression = expression.getSubExpression();


        if (subExpression != null) {
            operands.add(getNode(subExpression));

            resultMatrix = getResult(operation, operands);
        } else
            resultMatrix = getResult(operation, operands);

        return resultMatrix;
    }

    private Matrix getResult(Operation operation, List<Operand> operands) throws ParseException {
        Matrix resultMatrix = null;

        try {
            switch (operation.getName()) {
                case "add":
                    resultMatrix = add((List<Matrix>) (List<?>) operands);

                    break;
                case "sub":
                    resultMatrix = subtract((List<Matrix>) (List<?>) operands);

                    break;
                case "mul":
                    resultMatrix = multiplicateAll((List<Matrix>) (List<?>) operands);

                    break;
                case "transpose":
                    resultMatrix = transpose((Matrix) operands.get(0));
                    break;
                case "mulNumber":
                    if (operands.get(0) instanceof Number)
                        resultMatrix = multiplicateByNumber((Matrix) operands.get(1), (Number) operands.get(0));
                    else
                        resultMatrix = multiplicateByNumber((Matrix) operands.get(0), (Number) operands.get(1));

                    break;
                case "expontiate":
                    if (operands.get(0) instanceof Power)
                        resultMatrix = expontiate((Matrix) operands.get(1), (Power) operands.get(0));
                    else
                        resultMatrix = expontiate((Matrix) operands.get(0), (Power) operands.get(1));

                    break;

                default:
                    throw new ParseException("Неопознанный тег");
            }
        } catch (ClassCastException e) {
            throw new ParseException(e.getMessage());
        }

        return resultMatrix;
    }

    private Matrix add(List<Matrix> matrices) throws ParseException {
        for (int j = 0; j < matrices.size() - 1; j++)
            if (matrices.get(j).getM() != matrices.get(j + 1).getM() || matrices.get(j).getN() != matrices.get(j + 1).getN())
                throw new ParseException("Сложение матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)");

        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
        Matrix resultMatrix = new Matrix(M, N);

        if (matrices.get(0).getElement(0, 0) instanceof Number) {
            for (int j = 0; j < M; j++)
                for (int k = 0; k < N; k++) {

                    double sum = 0;
                    for (Matrix<Number> matrice : matrices)
                        sum += matrice.getElement(j, k).getNumber();

                    resultMatrix.setElement(j, k, new Number(sum));
                }
        } else {
            List<Matrix> subMatrices = new ArrayList<>();

            for (int j = 0; j < M; j++)
                for (int k = 0; k < N; k++) {

                    for (Matrix<Matrix> matrice : matrices)
                        subMatrices.add(matrice.getElement(j, k));

                    resultMatrix.setElement(j, k, add(subMatrices));
                }
        }
        return resultMatrix;
    }

    private Matrix subtract(List<Matrix> matrices) throws ParseException {
        for (int j = 0; j < matrices.size() - 1; j++)
            if (matrices.get(j).getM() != matrices.get(j + 1).getM() || matrices.get(j).getN() != matrices.get(j + 1).getN())
                throw new ParseException("Вычитание матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)");

        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
        Matrix resultMatrix = new Matrix(M, N);

        if (matrices.get(0).getElement(0, 0) instanceof Number) {
            for (int j = 0; j < M; j++)
                for (int k = 0; k < N; k++) {

                    double diff = 0;
                    for (Matrix<Number> matrice : matrices)
                        diff -= matrice.getElement(j, k).getNumber();

                    resultMatrix.setElement(j, k, new Number(diff));
                }
        } else {
            List<Matrix> subMatrices = new ArrayList<>();

            for (int j = 0; j < M; j++)
                for (int k = 0; k < N; k++) {

                    for (Matrix<Matrix> matrice : matrices)
                        subMatrices.add(matrice.getElement(j, k));

                    resultMatrix.setElement(j, k, subtract(subMatrices));

                }
        }
        return resultMatrix;
    }

    private Matrix multiplicate(Matrix m1, Matrix m2) throws ParseException {
        if (m1.getN() != m2.getM())
            throw new ParseException("Умножение матриц невозможно (количество элементов в строке первой матрицы, должно равняться количесту элементов столбца второй");

        int M = m1.getM(), K = m2.getN(), N = m2.getM();
        Matrix resultMatrix = new Matrix(M, K);

        if (m1.getElement(0, 0) instanceof Number) {

            Matrix<Number> m11 = (Matrix<Number>) m1;
            Matrix<Number> m22 = (Matrix<Number>) m2;

            double sum = 0;
            for (int i = 0; i < M; i++)
                for (int j = 0; j < K; j++) {
                    for (int c = 0; c < N; c++)
                        sum += (Double) m11.getElement(i, c).getNumber() * (Double) m22.getElement(c, j).getNumber();

                    resultMatrix.setElement(i, j, new Number(sum));
                    sum = 0;
                }
        } else {
            for (int i = 0; i < M; i++) {
                List<Matrix> matrices = new ArrayList<>();

                for (int j = 0; j < K; j++) {
                    for (int c = 0; c < N; c++)
                        matrices.add(multiplicate((Matrix) m1.getElement(i, c), (Matrix) m2.getElement(c, j)));

                    Matrix matrix = add(matrices);

                    resultMatrix.setElement(i, j, matrix);
                }
            }
        }

        return resultMatrix;
    }

    private Matrix multiplicateAll(List<Matrix> operands) throws ParseException {
        int M = operands.get(0).getM(), N = operands.get(0).getN();
        Matrix resultMatrix = operands.get(0);

        for (int i = 1; i < operands.size(); i++)
            resultMatrix = multiplicate(resultMatrix, operands.get(i));

        return resultMatrix;
    }

    private Matrix multiplicateByNumber(Matrix matrix, Number number) {
        int M = matrix.getM(), N = matrix.getN();
        Matrix resultMatrix = new Matrix(M, N);

        if (matrix.getElement(0, 0) instanceof Matrix) {
            for (int i = 0; i < M; i++)
                for (int j = 0; j < N; j++)
                    resultMatrix.setElement(i, j, multiplicateByNumber((Matrix) matrix.getElement(i, j), number));
        } else {
            Matrix<Number> matrix1 = (Matrix<Number>) matrix;

            for (int i = 0; i < M; i++)
                for (int j = 0; j < N; j++)
                    resultMatrix.setElement(i, j, new Number(matrix1.getElement(i, j).getNumber() * number.getNumber()));
        }

        return resultMatrix;
    }

    private Matrix expontiate(Matrix matrix, Power power) throws ParseException {
        Matrix matrix1 = matrix;

        for (int i = 0; i < power.getPower() - 1; i++)
            matrix = multiplicate(matrix1, matrix);


        return matrix;
    }

    private Matrix transpose(Matrix matrix) {
        int M = matrix.getM(), N = matrix.getN();
        Matrix resultMatrix = new Matrix(M, N);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                resultMatrix.setElement(i, j, matrix.getElement(j, i));

        return resultMatrix;
    }

    public Matrix getResult() {
        return result;
    }
}