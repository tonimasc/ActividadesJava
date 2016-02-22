package edu.upc.dsa;

/**
 * Created by toni on 22/02/16.
 */
public abstract class AbstractShape implements Shape {
    private String type;

    public AbstractShape(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "I'm a " + type;
    }

}
