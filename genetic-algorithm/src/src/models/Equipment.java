package src.models;

import java.util.Objects;

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

    public Equipment(Equipment equipment) {
        this(equipment.id, equipment.force, equipment.agility, equipment.expertise, equipment.resistance, equipment.vitality);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id &&
                Double.compare(equipment.force, force) == 0 &&
                Double.compare(equipment.agility, agility) == 0 &&
                Double.compare(equipment.expertise, expertise) == 0 &&
                Double.compare(equipment.resistance, resistance) == 0 &&
                Double.compare(equipment.vitality, vitality) == 0;
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
