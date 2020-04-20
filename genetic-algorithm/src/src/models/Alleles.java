package src.models;

import java.util.ArrayList;
import java.util.List;

public class Alleles {
    private final List<Equipment> helms, breastplates, weapons, gauntlets, boots;

    public Alleles(String helmsPath, String breastplatesPath, String weaponsPath, String gauntletsPath, String bootsPath){
        this.helms = new ArrayList<>();
        this.breastplates = new ArrayList<>();
        this.weapons = new ArrayList<>();
        this.gauntlets = new ArrayList<>();
        this.boots = new ArrayList<>();

        this.loadFromFile(this.helms, helmsPath);
        this.loadFromFile(this.breastplates, helmsPath);
        this.loadFromFile(this.weapons, helmsPath);
        this.loadFromFile(this.gauntlets, helmsPath);
        this.loadFromFile(this.boots, helmsPath);
    }

    private void loadFromFile(List<Equipment> list, String filePath){
        //TODO: implement
    }

    public List<Equipment> getBreastplates() {
        return breastplates;
    }

    public List<Equipment> getBoots() {
        return boots;
    }

    public List<Equipment> getGauntlets() {
        return gauntlets;
    }

    public List<Equipment> getHelms() {
        return helms;
    }

    public List<Equipment> getWeapons() {
        return weapons;
    }
}
