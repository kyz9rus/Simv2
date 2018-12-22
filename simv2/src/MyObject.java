class MyObject<T, String> {
    private T object;
    private String string;

    MyObject(T object, String string){
        this.object = object;
        this.string = string;
    }

    T getObject(){
        return object;
    }

    String getString(){
        return string;
    }
}
