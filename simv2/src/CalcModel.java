import java.util.ArrayList;
import java.util.List;

class CalcModel {
    private static InputModel inputModel;
    private static  OutputModel outputModel;
    private static int M, N;

    CalcModel (InputModel inputModel2){
        inputModel = inputModel2;
        M = inputModel2.getM();
        N = inputModel2.getN();
    }

    static void setOutputModel(OutputModel outputModel2){
        outputModel= outputModel2;
    }
    OutputModel getOutputModel(){
        return outputModel;
    }

    void calculate(){
        if (inputModel.getErrorMessage() != null) {
            outputModel = new OutputModel(null, inputModel.getErrorMessage());
            return;
        }

        if (outputModel == null)
            outputModel = new OutputModel(calc(inputModel.getList(), "expression"), null);
    }
    Matrix calc(List<MyObject<Object, String>> list, String nodeName) {

        if (list == null)
            return null;


        Matrix resultMatrix = new Matrix(M, N);

        List<Matrix> matrices = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            String node = list.get(i).getString();

            switch (node){
                case "add":
                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "add"));
                    break;
                case "sub":
                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "sub"));
                    break;
                case "mul":
                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "mul"));
                    break;
                case "mulNumber":
                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "mulNumber"));
                    break;
                case "number":
                    if (matrices.size() == 0){
                        outputModel = new OutputModel(null, "Невозможно выполнить операцию mulNumber (неверный порядок аргументов (matrix, number) )");
                        return null;
                    }

                    Matrix matrix = new Matrix(N, M);
                    matrix.setElement(0, 0, (Double) list.get(i).getObject());

                    matrices.add(matrix);
                    break;
                case "power":
                    if (matrices.size() == 0){
                        outputModel = new OutputModel(null, "Невозможно выполнить операцию expontiate (неверный порядок аргументов (matrix, number) )");
                        return null;
                    }

                    Matrix matrix0 = new Matrix(M, N);
                    matrix0.setElement(0, 0, (Integer) list.get(i).getObject());
//
                    matrices.add(matrix0);
//                    resultMatrix = matrix0;
//                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "power"));
                    break;
                case "transpose":
                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "transpose"));
                    break;
                case "expontiate":
                    matrices.add(calc((List<MyObject<Object, String>>) list.get(i).getObject(), "expontiate"));
                    break;
                case "matrix":
                    matrices.add((Matrix) list.get(i).getObject());
                    break;
            }
        }
        switch (nodeName){
            case "add":
                resultMatrix = add(matrices);
                break;
            case "sub":
                resultMatrix = subtract(matrices);
                break;
            case "mul":
                resultMatrix = multiplicateAll(matrices);
                break;
            case "mulNumber":
                resultMatrix = multiplicateByNumber(matrices.get(0),matrices.get(1).getElement(0,0));
                break;
            case "number":
                Matrix matrix = new Matrix(resultMatrix.getM(), resultMatrix.getN());
                matrix.setElement(0, 0, (Double) list.get(0).getObject());
                resultMatrix = matrix;
                break;
            case "transpose":
                resultMatrix = transpose(matrices.get(0));
                break;
            case "expontiate":
                resultMatrix = expontiate(matrices.get(0), (int)matrices.get(1).getElement(0,0));
                break;
            case "expression":
                resultMatrix = matrices.get(0);
                break;
        }



        return resultMatrix;
    }
    static Matrix add(List<Matrix> matrices){
        for (int i = 0; i < matrices.size()-1; i++)
            if (matrices.get(i).getM() != matrices.get(i+1).getM() || matrices.get(i).getN() != matrices.get(i+1).getN()){
                setOutputModel(new OutputModel(null, "Сложение матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)"));
                return null;
            }

        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
        Matrix resultMatrix = new Matrix(M, N);

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {

                double sum = 0;
                for (int c = 0; c < matrices.size(); c++)
                    sum += matrices.get(c).getElement(i, j);

                resultMatrix.setElement(i, j, sum);
            }

        return resultMatrix;
    }
    static Matrix subtract(List<Matrix> matrices){
        for (int i = 0; i < matrices.size()-1; i++)
            if (matrices.get(i).getM() != matrices.get(i+1).getM() || matrices.get(i).getN() != matrices.get(i+1).getN()){
                setOutputModel(new OutputModel(null, "Вычитание матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)"));
                return null;
            }

        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
        Matrix resultMatrix = new Matrix(M, N);

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {

                double diff = matrices.get(0).getElement(i, j);
                for (int c = 1; c < matrices.size(); c++)
                    diff -= matrices.get(c).getElement(i, j);

                resultMatrix.setElement(i, j, diff);
            }

        return resultMatrix;
    }
    static Matrix multiplicate(Matrix m1, Matrix m2){
        if (m1.getN() != m2.getM()){
            setOutputModel(new OutputModel(null, "Умножение матриц невозможно (количество элементов в строке первой матрицы, должно равняться количесту элементов столбца второй"));
            return null;
        }

        int M = m1.getM(), K = m2.getN(), N = m2.getM();
        Matrix resultMatrix = new Matrix(M, K);

        double sum = 0;
        for (int i = 0; i < M; i++)
            for (int j = 0; j < K; j++) {
                for (int c = 0; c < N; c++)
                    sum += m1.getElement(i, c) * m2.getElement(c, j); //

                resultMatrix.setElement(i, j, sum);
                sum = 0;
            }

        return resultMatrix;
    }
    static Matrix multiplicateAll(List<Matrix> matrices){
        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
        Matrix resultMatrix = matrices.get(0);

        for (int i = 1; i < matrices.size(); i++)
            resultMatrix = multiplicate(resultMatrix, matrices.get(i));


        return resultMatrix;
    }
    static Matrix multiplicateByNumber(Matrix m, double number){
        int M = m.getM(), N = m.getN();

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                m.setElement(i, j, m.getElement(i, j) * number);

        return m;
    }
    static Matrix transpose(Matrix m){
        int M = m.getM(), N = m.getN();
        Matrix resultMatrix = new Matrix(N, M);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                resultMatrix.setElement(i, j, m.getElement(j, i));
        return resultMatrix;
    }
    static Matrix expontiate(Matrix m, int power){
        Matrix matrix = m;

        for (int i = 0; i < power-1; i++)
            matrix = multiplicate(matrix, m);

        return matrix;
    }
}