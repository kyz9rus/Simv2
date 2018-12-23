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
    private Expression expression;
    private Resource resource;

    private boolean hasOperations = false;

    private MatrixInfo matrixInfo;
    private boolean isMatrixInfoInit = false;

    public InputModel(Resource resource) {
        this.resource = resource;
    }

    public Expression getExpression() {
        return expression;
    }

    public void parse() throws ParseException {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(resource.getFile().getName());

            expression = new Expression();

            Node root = document.getDocumentElement();

            expression = getExpressionFromFile(root);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new ParseException("Ошибка при парсинге файла input.xml");
        }
    }

    private List<Operand> getListOfOperands(Node node) throws ParseException {
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

                        if (!isMatrixInfoInit) {
                            initMatrixInfo((Matrix) matrix);
                            isMatrixInfoInit = true;
                        }

                        operands.add(matrix);

                        break;
                    case "number":
                        try {
                            Operand number = new Number(Double.parseDouble(subNode.getTextContent().replace(',', '.')));
                            operands.add(number);
                        } catch (NumberFormatException e) {
                            throw new ParseException("NumberFormatException: " + e.getMessage());
                        }

                        break;
                    case "power":
                        try {
                            Operand power = new Power(Integer.parseInt(subNode.getTextContent()));
                            operands.add(power);
                        } catch (NumberFormatException e) {
                            throw new ParseException("NumberFormatException: " + e.getMessage());
                        }

                        break;
                    default:
                        throw new ParseException("Неопознанный тег");
                }
            }

        }

        return operands;
    }

    private void initMatrixInfo(Matrix matrix) {
        int nestingLevel = 0;
        int[] M;
        int[] N;

        Matrix tempMaptrix = matrix;

        while (tempMaptrix.getElement(0, 0) instanceof Matrix) {
            nestingLevel++;

            tempMaptrix = (Matrix) tempMaptrix.getElement(0, 0);
        }

        nestingLevel++;

        M = new int[nestingLevel];
        N = new int[nestingLevel];

        tempMaptrix = matrix;
        for (int i = 0; i < nestingLevel; i++) {

            M[i] = tempMaptrix.getM();
            N[i] = tempMaptrix.getN();

            if (i != nestingLevel - 1)
                tempMaptrix = (Matrix) matrix.getElement(0, 0);
        }

        matrixInfo = new MatrixInfo(nestingLevel, M, N);
    }

    private Expression getExpressionFromFile(Node node) throws ParseException {
        Expression expression = new Expression();

        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);

            if (subNode.getNodeType() != Node.TEXT_NODE) {
                switch (subNode.getNodeName()) {
                    case "add":
                        expression.setOperation(new Operation("add"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations)
                            expression.setSubExpression(null);
                        else {
                            hasOperations = false;
                            expression.setSubExpression(getExpressionFromFile(subNode));
                        }

                        break;
                    case "sub":
                        expression.setOperation(new Operation("sub"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations)
                            expression.setSubExpression(null);
                        else {
                            hasOperations = false;
                            expression.setSubExpression(getExpressionFromFile(subNode));
                        }

                        break;
                    case "mul":
                        expression.setOperation(new Operation("mul"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations)
                            expression.setSubExpression(null);
                        else {
                            hasOperations = false;
                            expression.setSubExpression(getExpressionFromFile(subNode));
                        }

                        break;
                    case "transpose":
                        expression.setOperation(new Operation("transpose"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations) {
                            if (expression.getOperands().size() > 1)
                                throw new ParseException("Оператор transpose должен иметь только 1 операнд");

                            if (!(expression.getOperands().get(0) instanceof Matrix))
                                throw new ParseException("Оператор transpose может работать только с матрицами");

                            expression.setSubExpression(null);
                        } else {
                            if (expression.getOperands().size() > 0)
                                throw new ParseException("Оператор transpose должен иметь только 1 операнд");

                            hasOperations = false;
                            expression.setSubExpression(getExpressionFromFile(subNode));
                        }
                        break;
                    case "mulNumber":
                        expression.setOperation(new Operation("mulNumber"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations) {
                            if (expression.getOperands().size() > 2)
                                throw new ParseException("Оператор mulNumber должен иметь только 2 операнда");

                            if (!(expression.getOperands().get(0) instanceof Matrix))
                                throw new ParseException("Первым операндом оператора mulNumber должна быть матрица");

                            if (!(expression.getOperands().get(1) instanceof Number))
                                throw new ParseException("Вторым операндом оператора mulNumber должно быть число");

                            expression.setSubExpression(null);
                        } else {
                            if (expression.getOperands().size() > 1 || expression.getOperands().size() == 0)
                                throw new ParseException("Оператор mulNumber должен иметь 2 операнда");

                            if (!(expression.getOperands().get(0) instanceof Number))
                                throw new ParseException("Вторым операндом оператора mulNumber должно быть число");

                            hasOperations = false;
                            expression.setSubExpression(getExpressionFromFile(subNode));
                        }

                        break;
                    case "expontiate":
                        expression.setOperation(new Operation("expontiate"));

                        expression.setOperands(getListOfOperands(subNode));

                        if (!hasOperations) {
                            if (expression.getOperands().size() > 2)
                                throw new ParseException("Оператор expontiate должен иметь только 2 операнда");

                            if (!(expression.getOperands().get(0) instanceof Matrix))
                                throw new ParseException("Первым операндом оператора expontiate должна быть матрица");

                            if (!(expression.getOperands().get(1) instanceof Power))
                                throw new ParseException("Вторым операндом оператора expontiate должно быть степень матрицы (power)");

                            expression.setSubExpression(null);
                        } else {
                            if (expression.getOperands().size() > 1 || expression.getOperands().size() == 0)
                                throw new ParseException("Оператор expontiate должен иметь 2 операнда");

                            if (!(expression.getOperands().get(0) instanceof Power))
                                throw new ParseException("Вторым операндом оператора expontiate должно быть степень матрицы (power)");

                            hasOperations = false;
                            expression.setSubExpression(getExpressionFromFile(subNode));
                        }

                        break;
                    case "number":
                        if (subNode.getNodeName().equals("expression"))
                            throw new ParseException("Число не может находиться в корневом элементе");

                        break;
                    case "power":
                        if (subNode.getNodeName().equals("expression"))
                            throw new ParseException("Степень матрицы не может находиться в корневом элементе");

                        break;
                    case "matrix":
                        if (subNode.getNodeName().equals("expression"))
                            throw new ParseException("Матрица находиться в корневом элементе");

                        break;
                    default:
                        throw new ParseException("Неопознанный тег");
                }
            }
        }
        return expression;
    }

    Operand getElement(NodeList nodeList) throws ParseException {
        int calcM = 0, calcN = 0;
        int[] td = new int[Constants.MATRIX_MAX_SIZE];
        Operand[][] matrix = new Operand[Constants.MATRIX_MAX_SIZE][Constants.MATRIX_MAX_SIZE];

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
                            if (tdNode.getTextContent().contains("\n"))
                                matrix[calcM - 1][td[calcM - 1] - 1] = getElement(tdNode.getChildNodes().item(1).getChildNodes());
                            else
                                matrix[calcM - 1][td[calcM - 1] - 1] = new Number(Double.parseDouble(tdNode.getTextContent().replace(',', '.')));

                        } catch (NumberFormatException e) {
                            throw new ParseException("Неверный формат элемента при задании матрицы");
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

        if (!check)
            throw new ParseException("Матрица введена некорректно (число элементов в строке не совпадает)");


        calcN = td[0];
        Matrix<Operand> resultMatrix = new Matrix<>(calcM, calcN);
        for (int i = 0; i < calcM; i++)
            for (int j = 0; j < calcN; j++)
                resultMatrix.setElement(i, j, matrix[i][j]);


        return resultMatrix;
    }

    public MatrixInfo getMatrixInfo() {
        return matrixInfo;
    }
}