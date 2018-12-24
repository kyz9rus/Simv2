package models;

import objects.Error;
import objects.Matrix;
import objects.Number;
import objects.Resource;

import java.io.FileWriter;
import java.io.IOException;

public class OutputModel {
    private Matrix resultMatrix;
    private Resource resource;
    private Error error;

    public OutputModel(Matrix resultMatrix, Resource resource, Error error){
        this.resultMatrix = resultMatrix;
        this.resource = resource;
        this.error = error;
    }

    public OutputModel(Resource resource, Error error){
        this.resource = resource;
        this.error = error;
    }

    public void print(){
        try (FileWriter fileWriter = new FileWriter(resource.getFile().getName())) {
            if (error != null){
                fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
                        "<head>\n" +
                        "    <title>MathML</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                        "        <mrow>\n" +
                        "            <mi>RESULT:</mi>\n" +
                        "            <mo> ERROR (" + error.getMessage() + ")</mo>\n" +
                        "        </mrow>\n" +
                        "    </math>\n" +
                        "</body>\n" +
                        "</html>");
            } else {
                fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
                        "<head>\n" +
                        "    <title>MathML</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                        "        <mi>RESULT</mi>\n" +
                        "        <mo>=</mo>\n");
                fileWriter.write(printMatrix(fileWriter, resultMatrix));

                fileWriter.write("</math>\n" +
                        "</body>\n" +
                        "</html>");

                System.out.println("Результат успешно записан в файл!");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String printMatrix(FileWriter fileWriter, Matrix matrix) throws IOException {
        String resultPrint = "";

        if (matrix.getElement(0, 0) instanceof Matrix) {
            resultPrint += "<mfenced open=\"[\" close=\"]\">\n<mtable columnalign=\"center\">";

            for (int i = 0; i < matrix.getM(); i++) {
                resultPrint += "<mtr>\n";

                for (int j = 0; j < matrix.getN(); j++)
                    resultPrint += printMatrix(fileWriter, (Matrix) matrix.getElement(i, j));

                resultPrint += "</mtr>\n";
            }

            resultPrint += "</mtable>\n</mfenced>";
        } else {
            Matrix<Number> matrix1 = (Matrix<Number>) matrix;

            resultPrint += "<mfenced open=\"[\" close=\"]\">\n<mtable columnalign=\"center\">";

            for (int i = 0; i < matrix1.getM(); i++) {
                resultPrint += "<mtr>\n";

                for (int j = 0; j < matrix1.getN(); j++) {
                    resultPrint += "<mtd>" + String.format("%.4f", matrix1.getElement(i, j).getNumber()) + "</mtd>\n";
                }
                resultPrint += "</mtr>\n";
            }

            resultPrint += "</mtable>\n</mfenced>";
        }

        return resultPrint;
    }
}