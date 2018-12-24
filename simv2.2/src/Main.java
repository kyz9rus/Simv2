import models.CalcModel;
import models.InputModel;
import models.OutputModel;
import objects.Error;
import objects.ParseException;
import objects.Resource;

public class Main {
    public static void main(String[] args) {

        Resource inputResource = new Resource("input.xml");
        Resource outputResource = new Resource("output.xhtml");

        InputModel inputModel;
        CalcModel calcModel = null;
        OutputModel outputModel;

        try {
            inputModel = new InputModel(inputResource);
            inputModel.parse();

            calcModel = new CalcModel(inputModel.getExpression());
            calcModel.calculate();

            outputModel = new OutputModel(calcModel.getResult(), outputResource, null);
        }
        catch (ParseException | NullPointerException e1) {
            outputModel = new OutputModel(outputResource, new Error(e1.getMessage()));
            outputModel.print();

            System.out.println("Error in parsing: " + e1.getMessage());
        }

        outputModel.print();
    }
}
