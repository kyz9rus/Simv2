package objects;

public class Power extends Operation{
    private Integer power;

    public Power (){}

    public Power (Integer power){
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }
}