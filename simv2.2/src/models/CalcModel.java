package models;

public class CalcModel{
    private InputModel inputModel;
    private OutputModel outputModel;

    public CalcModel(InputModel inputModel){
        this.inputModel = inputModel;
    }

    public OutputModel getOutputModel() {
        return outputModel;
    }

    void calculate(){

    }
}