package src.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gen {
    public static int maxLocus = 6;

    private double height;
    private Equipment helm, breastplate, weapon, gauntlet, boots;

    public Gen(double height, Equipment helm, Equipment breastplate, Equipment weapon, Equipment gauntlet, Equipment boots){
        this.height = height;
        this.helm = helm;
        this.breastplate = breastplate;
        this.weapon = weapon;
        this.gauntlet = gauntlet;
        this.boots = boots;
    }

    public Gen(){

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
}
