package src.models;

public class Equipment {
    private final double force, agility, expertise, resistance, vitality;

    public Equipment(double force, double agility, double expertise, double resistance, double vitality){
        this.force = force;
        this.agility = agility;
        this.expertise = expertise;
        this.resistance = resistance;
        this.vitality = vitality;
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
