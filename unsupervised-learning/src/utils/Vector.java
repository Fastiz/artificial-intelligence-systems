package utils;

import java.util.Arrays;

public class Vector {
    private final double[] elements;

    public Vector(double[] elements){
        this.elements = elements;
    }

    public static Vector zero(int dim){
        double[] elements = new double[dim];
        Arrays.fill(elements, 0);

        return new Vector(elements);
    }

    public int getDimension(){
        return elements.length;
    }

    public static Vector scalarMultiplication(Vector vec, double scalar){
        double[] newValues = new double[vec.elements.length];
        for(int i = 0; i < vec.elements.length; i++){
            newValues[i] = vec.elements[i] * scalar;
        }
        return new Vector(newValues);
    }

    public static Vector sum(Vector v1, Vector v2){
        if(v1.getDimension() != v2.getDimension()) {
            throw new IllegalArgumentException("The two vectors have different dimensions");
        }

        double[] newValues = new double[v1.elements.length];
        for(int i = 0; i < v1.elements.length; i++){
            newValues[i] = v1.elements[i] + v2.elements[i];
        }

        return new Vector(newValues);
    }

    public static Vector subtract(Vector v1, Vector v2){
        return sum(v1, scalarMultiplication(v2, -1));
    }

    public static double distance(Vector v1, Vector v2){
        if(v1.getDimension() != v2.getDimension()) {
            throw new IllegalArgumentException("The two vectors have different dimensions");
        }

        double aux = 0;
        for(int i=0; i<v1.elements.length; i++){
            aux += Math.pow(v1.elements[i] - v2.elements[i], 2);
        }

        return Math.sqrt(aux);
    }
}
