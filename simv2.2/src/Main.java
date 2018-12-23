import models.CalcModel;
import models.InputModel;
import models.OutputModel;
import objects.Error;
import objects.ParseException;
import objects.Resource;

public class Main {
    public static void main(String[] args) {

        InputModel inputModel;
        CalcModel calcModel;
        OutputModel outputModel;

        try {
            inputModel = new InputModel(new Resource("input2.xml"));
            inputModel.parse();

            calcModel = new CalcModel(inputModel.getExpression(), inputModel.getMatrixInfo());
            calcModel.calculate();
        }
        catch (ParseException e1) {
            outputModel = new OutputModel(new Error(e1.getMessage()));
            outputModel.print();

            System.out.println("Error in parsing: " + e1.getMessage());
        }

//        OutputModel outputModel = calcModel.getOutputModel();
//        outputModel.print();
    }
}
