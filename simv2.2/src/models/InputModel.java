package models;

import objects.*;
import objects.Number;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputModel {
    private Errors errors = new Errors();
    private Expression expression;
    private Resource resource;

    public InputModel(Resource resource) {
        this.resource = resource;
    }

    public Expression getExpression() {
        return expression;
    }

    public void parse() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(resource.getFile().getName());

            expression = new Expression();

            Node root = document.getDocumentElement();

            expression = getExpressionFromFile(root);
//            list = getNode(root);
//
//            checkCorrect(root);
//
//            setNumberOfElements(root.getChildNodes());

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace(System.out);
            errors.addError("Ошибка при парсинге файла input.xml");
        }
    }

    private boolean hasOperations = false;

    private List<Operand> getListOfOperands(Node node) {
        NodeList nodeList = node.getChildNodes();
        List<Operand> operands = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);

            if (subNode.getNodeType() != Node.TEXT_NODE) {
                switch (subNode.getNodeName()) {
                    case "add":
                        hasOperations = true;
                        break;
                    case "sub":
                        hasOperations = true;
                        break;
                    case "mul":
                        hasOperations = true;
                        break;
                    case "transpose":
                        hasOperations = true;
                        break;
                    case "mulNumber":
                        hasOperations = true;
                        break;
                    case "expontiate":
                        hasOperations = true;
                        break;
                    case "matrix":
                        Operand matrix = getElement(subNode.getChildNodes());

                        operands.add(matrix);

//                        Operand operand = new Matrix();
                        break;
                    case "number":
                        break;
                    case "power":
                        break;

                }
            }

        }

        return operands;
    }

    private Expression getExpressionFromFile(Node node) {
        Expression expression = new Expression();

        NodeList nodeList = node.getChildNodes();

//        List<Operand> nestedList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);

            if (subNode.getNodeType() != Node.TEXT_NODE) {
                switch (subNode.getNodeName()) {
                    case "add":
                        expression.setOperation(new Operation("add"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations)
                            expression.setSubExpression(null);
                        else
                            expression.setSubExpression(getExpressionFromFile(subNode));

                        break;
                    case "sub":
                        break;
                    case "mul":
                        break;
                    case "transpose":
                        expression.setOperation(new Operation("transpose"));

                        expression.setOperands(getListOfOperands(subNode));
                        break;
                    case "mulNumber":
                        break;
                    case "expontiate":
                        break;
                    case "number":
                        if (subNode.getNodeName().equals("expression")) {
                            errors.addError("Неверное выражение");
                            return null;
                        }

                        break;
                    case "power":
                        if (subNode.getNodeName().equals("expression")) {
                            errors.addError("Неверное выражение");
                            return null;
                        }
                        break;
                    case "matrix":
                        if (subNode.getNodeName().equals("expression")) {
                            errors.addError("Неверное выражение");
                            return null;
                        }
                        break;
                    default:
                        return null;
                }
            }
        }
        return expression;
    }

    Operand getElement(NodeList nodeList) {
        int calcM = 0, calcN = 0;
        int[] td = new int[MatrixService.MAX_SIZE];
        Operand[][] matrix = new Operand[MatrixService.MAX_SIZE][MatrixService.MAX_SIZE];

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.TEXT_NODE && node.getNodeName().equals("row")) {
                calcM++;
                NodeList tdList = node.getChildNodes();

                for (int j = 0; j < tdList.getLength(); j++) {
                    Node tdNode = tdList.item(j);

                    if (tdNode.getNodeType() != Node.TEXT_NODE && tdNode.getNodeName().equals("column")) {
                        td[calcM - 1]++;
                        try {
                            String temp = tdNode.getTextContent();      // "1"

                            if (temp.contains("\n")) {
                                Operand element = getElement(tdNode.getChildNodes());
                            } else
                                matrix[calcM - 1][td[calcM - 1] - 1] = new Number(Double.parseDouble(tdNode.getTextContent().replace(',', '.')));


                        } catch (NumberFormatException e) {
//                            printMatrix(null, "Неверный формат элемента при задании матрицы");
                            System.exit(1);
                        }
                    }

                }
            }
        }


        return new Operand();
    }


//    private static Matrix parseMatrix(NodeList nodelist) {
//        int calcM = 0, calcN = 0;
//        int[] td = new int[10];
//        double[][] matrix = new double[10][10];
//
//
//        for (int i = 0; i < nodelist.getLength(); i++) {
//            Node node = nodelist.item(i);
//
//            if (node.getNodeType() != Node.TEXT_NODE && node.getNodeName().equals("row")) {
//                calcM++;
//                NodeList tdList = node.getChildNodes();
//
//                for (int j = 0; j < tdList.getLength(); j++) {
//                    Node tdNode = tdList.item(j);
//
//                    if (tdNode.getNodeType() != Node.TEXT_NODE && tdNode.getNodeName().equals("column")) {
//                        td[calcM - 1]++;
//                        try {
//                            matrix[calcM - 1][td[calcM - 1] - 1] = Double.parseDouble(tdNode.getTextContent().replace(',', '.'));
//                        } catch (NumberFormatException e) {
////                            printMatrix(null, "Неверный формат элемента при задании матрицы");
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
////            printMatrix(null, "Матрица введена некорректно (число элементов в строке не совпадает)");
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

    public Errors getErrors() {
        if (errors == null)
            return new Errors(null);
        return errors;
    }
}