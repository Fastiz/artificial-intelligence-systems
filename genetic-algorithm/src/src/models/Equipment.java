package src.models;

public class Equipment {
    private int id;
    private final double force, agility, expertise, resistance, vitality;

    public Equipment(int id, double force, double agility, double expertise, double resistance, double vitality){
        this.id = id;
        this.force = force;
        this.agility = agility;
        this.expertise = expertise;
        this.resistance = resistance;
        this.vitality = vitality;
    }

    public int getId() {
        return id;
    }

    public double getAgility() {
        return agility;
    }

    public double getExpertise() {
        return expertise;
    }

    public double getForce() {
        return force;
    }

    public double getResistance() {
        return resistance;
    }

    public double getVitality() {
        return vitality;
    }
}
