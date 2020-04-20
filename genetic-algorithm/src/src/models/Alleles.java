package src.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alleles {
    private final List<Equipment> helms, breastplates, weapons, gauntlets, boots;

    public Alleles(String folder, String helmsFilename, String breastplatesFilename, String weaponsFilename,
                   String gauntletsFilename, String bootsFilename) throws IOException{

        this.helms = new ArrayList<>();
        this.breastplates = new ArrayList<>();
        this.weapons = new ArrayList<>();
        this.gauntlets = new ArrayList<>();
        this.boots = new ArrayList<>();

        this.loadFromFile(this.helms, folder + helmsFilename);
        this.loadFromFile(this.breastplates, folder + breastplatesFilename);
        this.loadFromFile(this.weapons, folder + weaponsFilename);
        this.loadFromFile(this.gauntlets, folder + gauntletsFilename);
        this.loadFromFile(this.boots, folder + bootsFilename);
    }

    private void loadFromFile(List<Equipment> list, String filePath) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            br.readLine();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] values = line.split("\\s+");
                list.add(new Equipment(Integer.parseInt(values[0]), Double.parseDouble(values[1]),
                        Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]),
                        Double.parseDouble(values[5])));
            }


        }
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
