import java.util.List;

public class Operation {
    private String name;
    private Type type;  // сколько операндов?
    private List<Object> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Object> getList() {
        return list;
    }

    public void addToList(Object obj) {
        list.add(obj);
    }
}

enum Type{
    one, two, many
}