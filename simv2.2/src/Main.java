import models.InputModel;
import objects.Resource;

public class Main {
    public static void main(String[] args) {
        InputModel inputModel = new InputModel(new Resource("input2.xml"));
        inputModel.parse();

//        CalcModel calcModel = new CalcModel(inputModel);
//        calcModel.calculate();
//
//        OutputModel outputModel = calcModel.getOutputModel();
//        outputModel.print();
    }
}
