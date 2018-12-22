import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int M, N;

//    private static boolean setNumberOfElements(NodeList nodeList) {
//        for (int i = 0; i < nodeList.getLength(); i++){
//            Node node = nodeList.item(i);
//
//            if (node.getNodeType() != Node.TEXT_NODE)
//                if (node.getNodeName().equals("matrix")) {
//                    Matrix matrix = parseMatrix(node.getChildNodes());
//                    M = matrix.getM();
//                    N = matrix.getN();
//                    return true;
//                }
//                else{
//                    if (setNumberOfElements(node.getChildNodes()))
//                        return true;
//                    else
//                        return false;
//                }
//        }
//        return true;
//    }
//    static Matrix parseMatrix(NodeList nodelist){
//        int calcM = 0, calcN = 0;
//        int[] td = new int[10];
//        double[][] matrix = new double [10][10];
//
//
//        for (int i = 0; i < nodelist.getLength(); i++){
//            Node node = nodelist.item(i);
//
//            if (node.getNodeType() != Node.TEXT_NODE && node.getNodeName().equals("row")){
//                calcM++;
//                NodeList tdList = node.getChildNodes();
//
//                for (int j = 0; j < tdList.getLength(); j++){
//                    Node tdNode = tdList.item(j);
//
//                    if (tdNode.getNodeType() != Node.TEXT_NODE && tdNode.getNodeName().equals("column")) {
//                        td[calcM - 1]++;
//                        try {
//                            matrix[calcM-1][td[calcM - 1]-1] = Double.parseDouble(tdNode.getTextContent().replace(',', '.'));
//                        }
//                        catch(NumberFormatException e){
//                            printMatrix(null, "Неверный формат элемента при задании матрицы");
//                            System.exit(1);
//                        }
//                    }
//
//                }
//            }
//        }
//
//        boolean check = true;
//        if (calcM > 1)
//            for (int i = 0; i < 1; i++)
//                if (td[i] != td[i + 1])
//                    check = false;
//
//        if (!check) {
//            printMatrix(null, "Матрица введена некорректно (число элементов в строке не совпадает)");
//            System.exit(1);
//        }
//
//        calcN = td[0];
//        Matrix resultMatrix = new Matrix(calcM, calcN);
//        for (int i = 0; i < calcM; i++)
//            for (int j = 0; j < calcN; j++)
//                resultMatrix.setElement(i, j, matrix[i][j]);
//
//        return resultMatrix;
//    }
//    static boolean checkCorrect(Node node){
//        boolean isCorrect = false;
//
//        NodeList nodelist = node.getChildNodes();
//        List<String> tags = new ArrayList<>();
//
//        for (int i = 0; i < nodelist.getLength(); i++){
//            Node tag = nodelist.item(i);
//
//            if (tag.getNodeType() != Node.TEXT_NODE)
//                tags.add(tag.getNodeName());
//        }
//
//        boolean isMatrixFirst = tags.get(0).equals("matrix"), isMatrixSecond;
//
//        for (int i = 1; i < tags.size(); i++){
//            isMatrixSecond = tags.get(i).equals("matrix");
//            if (!isMatrixFirst && !isMatrixSecond) {
//                printMatrix(null, "Ошибка при парсинге: Операции в корневом элементе не могут находится рядом");
//                System.exit(1);
//            }
//            else if (isMatrixFirst || isMatrixSecond){
//                printMatrix(null, "Ошибка при парсинге: В корневом элементе не должно быть матриц");
//                System.exit(1);
//            }
//
//            isMatrixFirst = isMatrixSecond;
//        }
//
//
//        return isCorrect;
//    }
//    static Matrix calc(Node node){
//        if (node.getNodeName().equals("expression")) // Проверка на правильность написания выражения
//            checkCorrect(node);
//
//        NodeList nodelist = node.getChildNodes();
//
//        boolean onlyMatrices = true;
//
//        List<Node> tags = new ArrayList<>();
//
//        for (int i = 0; i < nodelist.getLength(); i++){
//            Node tag = nodelist.item(i);
//
//            if (tag.getNodeType() != Node.TEXT_NODE)
//                tags.add(tag);
//            else
//                continue;
//
//            if (!tag.getNodeName().equals("matrix"))
//                onlyMatrices = false;
//        }
//
//        Matrix resultMatrix = new Matrix(M, N);
//
//        if (onlyMatrices) {
//            List<Matrix> matrices = new ArrayList<>();
//
//            for (Node tag : tags)
//                matrices.add(parseMatrix(tag.getChildNodes()));
//
//            switch (node.getNodeName()){
//                case "add":
//                    if (matrices.size() < 2) {
//                        printMatrix(null, "Недостаточно аргументов для сложения матриц");
//                        System.exit(1);
//                    }
//                    else
//                        resultMatrix = add(matrices);
//
//                    break;
//
//                case "sub":
//                    if (matrices.size() < 2) {
//                        printMatrix(null, "Недостаточно аргументов для сложения матриц");
//                        System.exit(1);
//                    }
//                    else
//                        resultMatrix = subtract(matrices);
//
//                    break;
//
//                case "mul":
//                    if (matrices.size() < 2) {
//                        printMatrix(null, "Недостаточно аргументов для сложения матриц");
//                        System.exit(1);
//                    }
//                    else
//                        resultMatrix = multiplicateAll(matrices);
//
//                    break;
//
//                case "mulNumber":
//                    if (matrices.size() != 2){
//                        printMatrix(null, "Для операции mulNumber должно быть только 2 аргумента (matrix и number)");
//                        System.exit(1);
//                    }
//
//                    break;
//                case "number":
//                    Matrix matrix = new Matrix(resultMatrix.getM(), resultMatrix.getN());
//                    double number = Double.parseDouble(node.getTextContent().replace(',', '.'));
//                    matrix.setElement(0, 0, number);
//
//                    resultMatrix = matrix;
//                    break;
//
//                case "transpose":
//                    resultMatrix = transpose(matrices.get(0));
//                    break;
//
//                case "power":
//                    Matrix matrix0 = new Matrix(resultMatrix.getM(), resultMatrix.getN());
//                    int power = 1;
//                    try{
//                        power = Integer.parseInt(node.getTextContent());
//                        if (power < 2 || power > 100)
//                            throw new NumberFormatException();
//                    }
//                    catch (NumberFormatException e){
//                        printMatrix(null, "Неверный формат степени (2 < power < 100)");
//                        System.exit(1);
//                    }
//
//                    matrix0.setElement(0, 0, power);
//
//                    resultMatrix = matrix0;
//                    break;
//                case "expontiate":
//                    if (matrices.size() != 2){
//                        printMatrix(null, "Для операции expontiate должно быть только 2 аргумента (matrix и number)");
//                        System.exit(1);
//                    }
//
//                    break;
//            }
//        }
//        else {
//            List<Matrix> matrices = new ArrayList<>();
//
//            for (int i = 0; i < tags.size(); i++) {
//                Node tag = tags.get(i);
//
//                switch (tag.getNodeName()) {
//                    case "add":
//                        matrices.add(calc(tag));
//                        break;
//
//                    case "sub":
//                        matrices.add(calc(tag));
//                        break;
//
//                    case "mul":
//                        matrices.add(calc(tag));
//                        break;
//
//                    case "mulNumber":
//                        matrices.add(calc(tag));
//                        break;
//                    case "number":
//                        if (matrices.size() == 0){
//                            printMatrix(null, "Невозможно выполнить операцию mulNumber (неверный порядок аргументов (matrix, number) )");
//                            System.exit(1);
//                        }
//
//                        matrices.add(calc(tag));
//                        break;
//                    case "power":
//                        if (matrices.size() == 0){
//                            printMatrix(null, "Невозможно выполнить операцию expontiate (неверный порядок аргументов (matrix, number) )");
//                            System.exit(1);
//                        }
//
//                        matrices.add(calc(tag));
//                        break;
//
//                    case "transpose":
//                        matrices.add(calc(tag));
//                        break;
//
//                    case "expontiate":
//                        matrices.add(calc(tag));
//                        break;
//
//                    case "matrix":
//                        matrices.add(parseMatrix(tag.getChildNodes()));
//                        break;
//
//                    default:
//                        printMatrix(null, "Ошибка парсинга. Проверьте введенные данные");
//                        System.exit(2);
//                }
//                if (matrices.size() == tags.size()){
//
//                    switch (node.getNodeName()){
//                        case "add":
//                            resultMatrix = add(matrices);
//                            break;
//
//                        case "sub":
//                            resultMatrix = subtract(matrices);
//                            break;
//
//                        case "mul":
//                            resultMatrix = multiplicateAll(matrices);
//                            break;
//
//                        case "transpose":
//                            if (matrices.size() != 1) {
//                                printMatrix(null, "Для операции transpose должен быть только 1 аргумент");
//                                System.exit(1);
//                            }
//
//                            resultMatrix = transpose(matrices.get(0));
//                            break;
//
//                        case "mulNumber":
//                            if (matrices.size() != 2){
//                                printMatrix(null, "Для операции mulNumber должно быть только 2 аргумента (матрица и число)");
//                                System.exit(1);
//                            }
//
//                            resultMatrix = multiplicateByNumber(matrices.get(0), matrices.get(1).getElement(0,0));
//                            break;
//
//                        case "expontiate":
//                            if (matrices.size() != 2){
//                                printMatrix(null, "Для операции expontiate должно быть только 2 аргумента (матрица и число)");
//                                System.exit(1);
//                            }
//
//                            if (matrices.get(0).getN() != matrices.get(0).getM()){
//                                String message = "Для операции expontiate в качестве аргумента должна подаваться квадратная матрица";
//                                printMatrix(null, message);
//                                System.exit(1);
//                            }
//
//                            resultMatrix = expontiate(matrices.get(0), (int)matrices.get(1).getElement(0,0));
//
//                            break;
//                        case "expression" :
//                            return matrices.get(0);
//                    }
//                }
//            }
//        }
//
//        return resultMatrix;
//    }
//    static Matrix parsingAndCalc() {
//        Matrix resultMatrix = null;
//        try {
//            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document document = documentBuilder.parse("input.xml");
//
//            Node root = document.getDocumentElement();
//            setNumberOfElements(root.getChildNodes());
//
//            resultMatrix = calc(root);
//
//        } catch (ParserConfigurationException | IOException ex) {
//            ex.printStackTrace(System.out);
//        }
//        catch (SAXException e) {
//            printMatrix(null, "Ошибка при парсинге xml файла");
//        }
//
//        if (resultMatrix == null)
//            return new Matrix();
//        else
//            return resultMatrix;
//    }
//    static Matrix add(List<Matrix> matrices){
//        for (int i = 0; i < matrices.size()-1; i++)
//            if (matrices.get(i).getM() != matrices.get(i+1).getM() || matrices.get(i).getN() != matrices.get(i+1).getN()){
//                printMatrix(null, "Сложение матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)");
//                System.exit(1);
//            }
//
//        int M = matrices.get(0).getM(), N = matrices.get(0).getN();
//        Matrix resultMatrix = new Matrix(M, N);
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
//        return resultMatrix;
//    }
//    static Matrix subtract(List<Matrix> matrices){
//        for (int i = 0; i < matrices.size()-1; i++)
//            if (matrices.get(i).getM() != matrices.get(i+1).getM() || matrices.get(i).getN() != matrices.get(i+1).getN()){
//                printMatrix(null, "Вычитание матриц невозможно (количество элементов в строках матрицы или в столбцах матриц не равно)");
//                System.exit(1);
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
//            printMatrix(null, "Умножение матриц невозможно (количество элементов в строке первой матрицы, должно равняться количесту элементов столбца второй");
//            System.exit(1);
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
//    static Matrix expontiate(Matrix m, int power){  // Сделать проверку на равенство N и M перед вызовом данного метода
//        Matrix matrix = m;
//
//        for (int i = 0; i < power-1; i++)
//            matrix = multiplicate(matrix, m);
//
//        return matrix;
//    }
//    static void printMatrix(Matrix m, String message){
//        try (FileWriter fileWriter = new FileWriter("output.xhtml")){
//
//            if (message.length() == 0) {
//                fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
//                        "<head>\n" +
//                        "    <title>MathML</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
//                        "        <mrow>\n" +
//                        "            <mi>RESULT</mi>\n" +
//                        "            <mo>=</mo>\n" +
//                        "            <mfenced open=\"[\" close=\"]\"><mtable>\n");
//
//                for (int i = 0; i < m.getM(); i++) {
//                    fileWriter.write("<mtr>\n");
//                    for (int j = 0; j < m.getN(); j++) {
//                        fileWriter.write("<mtd>" + String.format("%.4f", m.getElement(i, j)) + "</mtd>\n");
//                    }
//                    fileWriter.write("</mtr>\n");
//                }
//
//                fileWriter.write("</mtable>\n" +
//                        "            </mfenced>\n" +
//                        "        </mrow>\n" +
//                        "    </math>\n" +
//                        "</body>\n" +
//                        "</html>");
//                System.out.println("Результат успешно записан в файл!");
//            }
//            else{
//                fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
//                        "<head>\n" +
//                        "    <title>MathML</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
//                        "        <mrow>\n" +
//                        "            <mi>RESULT</mi>\n" +
//                        "            <mo>=ERROR (" + message + ")</mo>\n" +
//                        "        </mrow>\n" +
//                        "    </math>\n" +
//                        "</body>\n" +
//                        "</html>");
//            }
//
//        }
//        catch (IOException e){
//            System.out.println("Запись в файл не удалась.");
//            System.exit(1);
//        }
//    }

//    static List<MyObject<Object, String>> getNode(Node node){
//        NodeList nodeList = node.getChildNodes();
//
//        List<MyObject<Object, String>> nestedList = new ArrayList<>();
//        for (int i = 0; i < nodeList.getLength(); i++){
//            Node tag = nodeList.item(i);
//
//            if (tag.getNodeType() != Node.TEXT_NODE) {
//                switch (tag.getNodeName()) {
//                    case "add":
//                        nestedList.add(new MyObject<>(getNode(tag), "add"));
//                        break;
//                    case "sub":
//                        nestedList.add(new MyObject<>(getNode(tag), "sub"));
//                        break;
//                    case "mul":
//                        nestedList.add(new MyObject<>(getNode(tag), "mul"));
//                        break;
//                    case "transpose":
//                        nestedList.add(new MyObject<>(getNode(tag), "transpose"));
//                        break;
//                    case "mulNumber":
//                        nestedList.add(new MyObject<>(getNode(tag), "mulNumber"));
//                        break;
//                    case "expontiate":
//                        nestedList.add(new MyObject<>(getNode(tag), "expontiate"));
//                        break;
//                    case "number":
//                        try {
//                            nestedList.add(new MyObject<>(Double.parseDouble(tag.getTextContent().replace(',', '.')), "number"));
//                        }
//                        catch(NumberFormatException e){
//                            printMatrix(null, "Неверный формат степени матрицы");
//                            System.exit(1);
//                        }
//                        break;
//                    case "power":
//                        try {
//                            nestedList.add(new MyObject<>(Integer.parseInt(tag.getTextContent()), "power"));
//                        }
//                        catch(NumberFormatException e){
//                            printMatrix(null, "Неверный формат степени матрицы");
//                            System.exit(1);
//                        }
//
//                        break;
//                    case "matrix":
//                        nestedList.add(new MyObject<>(parseMatrix(tag.getChildNodes()), "matrix"));
//                        break;
//                    default:
//                        System.out.println();
//                        printMatrix(null, "Ошибка при парсинге файла input.xml");
//                        System.exit(1);
//                }
//            }
//        }
//
//        return nestedList;
//    }
//    static InputModel getInputModel(){
//        InputModel inputModel = new InputModel();
//
//        try {
//            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document document = documentBuilder.parse("input.xml");
//
//            Node root = document.getDocumentElement();
//
//            inputModel.list = getNode(root);
//
////            System.out.println(inputModel);
//
////            System.out.println(inputModel.list.get(0));
//
//
////            setNumberOfElements(root.getChildNodes());
//
//
//        } catch (ParserConfigurationException | IOException ex) {
//            ex.printStackTrace(System.out);
//        }
//        catch (SAXException e) {
//            printMatrix(null, "Ошибка при парсинге xml файла");
//        }
//
//        return inputModel;
//    }

    public static void main(String[] args) {
        // input - xml, output - MathML
        // операции: +, -, *a (умножение на число), * (умножение матриц), транспонирование, возведение в степень

        // Парсим в InputModel, получаем лист листов, в качестве объектов у нас любые комбинации типа данных Матрица и/или Операция

        InputModel inputModel = new InputModel();
        inputModel.parse();

        CalcModel calcModel = new CalcModel(inputModel);
        calcModel.calculate();

        OutputModel outputModel = calcModel.getOutputModel();
        outputModel.print();
    }
}