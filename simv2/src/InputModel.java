import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class InputModel {
    private static List<MyObject<Object, String>> list;
    private static String ERROR_MESSAGE;
    private static int M, N;

    int getM(){
        return M;
    }
    int getN(){
        return N;
    }

    String getErrorMessage() {
        return ERROR_MESSAGE;
    }
    List<MyObject<Object, String>> getList(){
        return list;
    }

    void parse() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("input.xml");

            Node root = document.getDocumentElement();

            list = getNode(root);

            checkCorrect(root);

            setNumberOfElements(root.getChildNodes());

        } catch (ParserConfigurationException | IOException ex) {
            ex.printStackTrace(System.out);
            ERROR_MESSAGE = "Ошибка при парсинге файла input.xml";
        } catch (SAXException e) {
            ERROR_MESSAGE = "Ошибка при парсинге файла input.xml";
        }
    }
    private boolean checkCorrect(Node root) {
        if (!checkCorrectRoot(root))
            return false;

        check(list);
        return true;
    }
    boolean checkCorrectRoot(Node node) {
        boolean isCorrect = true;

        NodeList nodelist = node.getChildNodes();
        List<String> tags = new ArrayList<>();

        for (int i = 0; i < nodelist.getLength(); i++) {
            Node tag = nodelist.item(i);

            if (tag.getNodeType() != Node.TEXT_NODE)
                tags.add(tag.getNodeName());
        }

        boolean isMatrixFirst = tags.get(0).equals("matrix"), isMatrixSecond;

        for (int i = 1; i < tags.size(); i++) {
            isMatrixSecond = tags.get(i).equals("matrix");
            if (!isMatrixFirst && !isMatrixSecond) {
                ERROR_MESSAGE = "Ошибка при парсинге: Операции в корневом элементе не могут находится рядом";
                return false;
            } else if (isMatrixFirst || isMatrixSecond) {
                ERROR_MESSAGE = "Ошибка при парсинге: В корневом элементе не должно быть матриц";
                return false;
            }

            isMatrixFirst = isMatrixSecond;
        }


        return isCorrect;
    }
    private static Matrix parseMatrix(NodeList nodelist) {
        int calcM = 0, calcN = 0;
        int[] td = new int[10];
        double[][] matrix = new double[10][10];


        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);

            if (node.getNodeType() != Node.TEXT_NODE && node.getNodeName().equals("row")) {
                calcM++;
                NodeList tdList = node.getChildNodes();

                for (int j = 0; j < tdList.getLength(); j++) {
                    Node tdNode = tdList.item(j);

                    if (tdNode.getNodeType() != Node.TEXT_NODE && tdNode.getNodeName().equals("column")) {
                        td[calcM - 1]++;
                        try {
                            matrix[calcM - 1][td[calcM - 1] - 1] = Double.parseDouble(tdNode.getTextContent().replace(',', '.'));
                        } catch (NumberFormatException e) {
//                            printMatrix(null, "Неверный формат элемента при задании матрицы");
                            System.exit(1);
                        }
                    }

                }
            }
        }

        boolean check = true;
        if (calcM > 1)
            for (int i = 0; i < 1; i++)
                if (td[i] != td[i + 1])
                    check = false;

        if (!check) {
//            printMatrix(null, "Матрица введена некорректно (число элементов в строке не совпадает)");
            System.exit(1);
        }

        calcN = td[0];
        Matrix resultMatrix = new Matrix(calcM, calcN);
        for (int i = 0; i < calcM; i++)
            for (int j = 0; j < calcN; j++)
                resultMatrix.setElement(i, j, matrix[i][j]);

        return resultMatrix;
    }
    private static boolean setNumberOfElements(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.TEXT_NODE)
                if (node.getNodeName().equals("matrix")) {
                    Matrix matrix = parseMatrix(node.getChildNodes());
                    M = matrix.getM();
                    N = matrix.getN();
                    return true;
                }
                else{
                    if (setNumberOfElements(node.getChildNodes()))
                        return true;
                    else
                        return false;
                }
        }
        return true;
    }
    private static List<MyObject<Object, String>> getNode(Node node) {
        NodeList nodeList = node.getChildNodes();

        List<MyObject<Object, String>> nestedList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tag = nodeList.item(i);

            if (tag.getNodeType() != Node.TEXT_NODE) {
                switch (tag.getNodeName()) {
                    case "add":
                        nestedList.add(new MyObject<>(getNode(tag), "add"));
                        break;
                    case "sub":
                        nestedList.add(new MyObject<>(getNode(tag), "sub"));
                        break;
                    case "mul":
                        nestedList.add(new MyObject<>(getNode(tag), "mul"));
                        break;
                    case "transpose":
                        nestedList.add(new MyObject<>(getNode(tag), "transpose"));
                        break;
                    case "mulNumber":
                        nestedList.add(new MyObject<>(getNode(tag), "mulNumber"));
                        break;
                    case "expontiate":
                        nestedList.add(new MyObject<>(getNode(tag), "expontiate"));
                        break;
                    case "number":
                        try {
                            nestedList.add(new MyObject<>(Double.parseDouble(tag.getTextContent().replace(',', '.')), "number"));
                        } catch (NumberFormatException e) {
                            ERROR_MESSAGE = "Неверный формат множителя";
                            return null;
                        }
                        break;
                    case "power":
                        try {
                            int power = Integer.parseInt(tag.getTextContent());
                            if (power < 2 || power > 100)
                                throw new NumberFormatException();
                            nestedList.add(new MyObject<>(power, "power"));
                        } catch (NumberFormatException e) {
                            ERROR_MESSAGE = "Неверный формат степени матрицы (100 > power > 2)";
                            return null;
                        }

                        break;
                    case "matrix":
                        nestedList.add(new MyObject<>(parseMatrix(tag.getChildNodes()), "matrix"));
                        break;
                    default:
                        ERROR_MESSAGE = "Ошибка при парсинге файла input.xml";
                        return null;
                }
            }
        }

        return nestedList;
    }
    private static void check(List<MyObject<Object, String>> list){

        boolean onlyMatrices = true;
        int numberOfMatrices = 0, counter = 0;
        ArrayList<Integer> mas = new ArrayList<Integer>();

        for (int i = 0; i < list.size(); i++) {
            MyObject<Object, String> aList = list.get(i);
            String str = aList.getString();

            if (!str.equals("matrix")) {
                onlyMatrices = false;
                mas.add(i);

            } else
                numberOfMatrices++;

            counter = 0;
        }

        if (onlyMatrices) {
            switch (list.get(0).getString()){
                case "add":
                    if (numberOfMatrices < 2) {
                        ERROR_MESSAGE = "Недостаточно аргументов для сложения матриц";
                        return;
                    }

                    break;

                case "sub":
                    if (numberOfMatrices < 2) {
                        ERROR_MESSAGE = "Недостаточно аргументов для вычитания матриц";
                        return;
                    }

                    break;

                case "mul":
                    if (numberOfMatrices < 2) {
                        ERROR_MESSAGE = "Недостаточно аргументов для умножения матриц";
                        return;
                    }
                    break;

                case "mulNumber":
                    if (numberOfMatrices != 2){
                        ERROR_MESSAGE = "Для операции mulNumber должно быть только 2 аргумента (matrix и number)";
                        return;
                    }

                    break;

                case "expontiate":
                    if (numberOfMatrices != 2){
                        ERROR_MESSAGE = "Для операции expontiate должно быть только 2 аргумента (matrix и number)";
                        return;
                    }

                    break;
            }
        }
        else {
            for (int i = 0; i < mas.size(); i++) {


//                Node tag = tags.get(i);
//                ArrayList<MyObject<Object, String>> nestedList = list.get();

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
            }
        }

    }
}