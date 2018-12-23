package objects;

import java.util.Objects;

public class Operation {
    private String name;

    Operation(){}

    public Operation(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Operation operation = (Operation) o;
//
//        return name.equals(operation.getName());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }
}