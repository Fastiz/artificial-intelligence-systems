package src.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Individual implements Cloneable {
    private int generation;
    public static int maxLocus = 6;

    private double height;
    private Equipment helm, breastplate, weapon, gauntlet, boots;

    public Individual(double height, Equipment helm, Equipment breastplate, Equipment weapon, Equipment gauntlet, Equipment boots){
        this.height = height;
        this.helm = helm;
        this.breastplate = breastplate;
        this.weapon = weapon;
        this.gauntlet = gauntlet;
        this.boots = boots;
    }

    public Individual(){

    }

    public Individual(Individual individual) {
        this.height = individual.height;
        this.helm = new Equipment(individual.getHelm());
        this.breastplate = new Equipment(individual.getBreastplate());
        this.weapon = new Equipment(individual.getWeapon());
        this.gauntlet = new Equipment(individual.getGauntlet());
        this.boots = new Equipment(individual.getBoots());
    }

    public void setEquipmentLocus(List<Equipment> equipmentLocus){
        this.helm = equipmentLocus.get(0);
        this.breastplate = equipmentLocus.get(1);
        this.weapon = equipmentLocus.get(2);
        this.gauntlet = equipmentLocus.get(3);
        this.boots = equipmentLocus.get(4);
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBoots(Equipment boots) {
        this.boots = boots;
    }

    public void setBreastplate(Equipment breastplate) {
        this.breastplate = breastplate;
    }

    public void setGauntlet(Equipment gauntlet) {
        this.gauntlet = gauntlet;
    }

    public void setHelm(Equipment helm) {
        this.helm = helm;
    }

    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    public double getHeight() {
        return height;
    }

    public Equipment getBoots() {
        return boots;
    }

    public Equipment getBreastplate() {
        return breastplate;
    }

    public Equipment getGauntlet() {
        return gauntlet;
    }

    public Equipment getHelm() {
        return helm;
    }

    public Equipment getWeapon() {
        return weapon;
    }

    public List<Equipment> getEquipmentLocus(){
        return new ArrayList<>(Arrays.asList(this.helm, this.breastplate, this.weapon, this.gauntlet, this.boots));
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getGeneration() {
        return generation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Double.compare(that.height, height) == 0 &&
                Objects.equals(helm, that.helm) &&
                Objects.equals(breastplate, that.breastplate) &&
                Objects.equals(weapon, that.weapon) &&
                Objects.equals(gauntlet, that.gauntlet) &&
                Objects.equals(boots, that.boots);
    }

    @Override
    public String toString() {
        return "Individual{" +
                "height=" + height +
                ", helm=" + helm.toString() +
                ", breastplate=" + breastplate.toString() +
                ", weapon=" + weapon.toString() +
                ", gauntlet=" + gauntlet.toString() +
                ", boots=" + boots.toString() +
                '}';
    }
}
