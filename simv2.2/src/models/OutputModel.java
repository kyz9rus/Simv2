package models;

import objects.Error;
import objects.Expression;
import objects.Resource;

import java.io.FileWriter;
import java.io.IOException;

public class OutputModel {
    private Expression expression;
    private Resource resource;
    private Error error;

    public OutputModel(Expression expression, Resource resource, Error error){
        this.expression = expression;
        this.resource = resource;
        this.error = error;
    }

    public OutputModel(Error error){
        this.error = error;
    }

    public void print(){
        try (FileWriter fileWriter = new FileWriter("output.xhtml")) {
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
                System.out.println("OKEY");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Error getErrors() {
        return error;
    }
}