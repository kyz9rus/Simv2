package objects;

import java.util.ArrayList;
import java.util.List;

public class Errors {
    private List<String> messages;

    public Errors(){
        messages = new ArrayList<>();
    }

    public Errors(List<String> messages){
        this.messages = messages;
    }

    public void addError(String message){
        messages.add(message);
    }

}
