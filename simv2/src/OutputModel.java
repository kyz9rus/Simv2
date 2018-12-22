import java.io.FileWriter;
import java.io.IOException;

class OutputModel {
    private static Matrix resultMatrix;
    private static String ERROR_MESSAGE;

    OutputModel(Matrix matrix, String message){
        resultMatrix = matrix;
        ERROR_MESSAGE = message;
    }

    void print(){
        try (FileWriter fileWriter = new FileWriter("output.xhtml")){

            if (ERROR_MESSAGE == null) {
                fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
                        "<head>\n" +
                        "    <title>MathML</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                        "        <mrow>\n" +
                        "            <mi>RESULT</mi>\n" +
                        "            <mo>=</mo>\n" +
                        "            <mfenced open=\"[\" close=\"]\"><mtable>\n");

                for (int i = 0; i < resultMatrix.getM(); i++) {
                    fileWriter.write("<mtr>\n");
                    for (int j = 0; j < resultMatrix.getN(); j++) {
                        fileWriter.write("<mtd>" + String.format("%.4f", resultMatrix.getElement(i, j)) + "</mtd>\n");
                    }
                    fileWriter.write("</mtr>\n");
                }

                fileWriter.write("</mtable>\n" +
                        "            </mfenced>\n" +
                        "        </mrow>\n" +
                        "    </math>\n" +
                        "</body>\n" +
                        "</html>");
                System.out.println("Результат успешно записан в файл!");
            }
            else{
                fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
                        "<head>\n" +
                        "    <title>MathML</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                        "        <mrow>\n" +
                        "            <mi>RESULT</mi>\n" +
                        "            <mo>=ERROR (" + ERROR_MESSAGE + ")</mo>\n" +
                        "        </mrow>\n" +
                        "    </math>\n" +
                        "</body>\n" +
                        "</html>");
            }

        }
        catch (IOException e){
            System.out.println("Запись в файл не удалась.");
            System.exit(1);
        }
    }
}