package objects;

public class Number extends Operand {
    private Double number;

    public Number(Double number) {
        this.number = number;
    }

    public Double getNumber() {
        return number;
    }

    public Double add(Double value) {
        return number + value;
    }
}