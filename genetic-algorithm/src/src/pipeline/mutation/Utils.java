package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Equipment;
import src.models.Individual;

import java.util.List;
import java.util.Random;

public class Utils {
    private static final int HEIGHT = 0;
    private static final int HELMET = 1;
    private static final int BREASTPLATE = 2;
    private static final int WEAPON = 3;
    private static final int GAUNTLET = 4;
    private static final int BOOTS = 5;

    public static void applyMutation(Individual individual, int index, Alleles alleles) {
        Random random = new Random();
        switch (index) {
            case HEIGHT: {
                double height = random.nextDouble()*(Alleles.MAX_HEIGHT-Alleles.MIN_HEIGHT) + Alleles.MIN_HEIGHT;
                individual.setHeight(height);
            }
            case HELMET: {
                List<Equipment> helmets = alleles.getHelms();
                int randomIndex = random.nextInt(helmets.size());
                individual.setHelm(helmets.get(randomIndex));
            }
            case BREASTPLATE: {
                List<Equipment> breastPlate = alleles.getBreastplates();
                int randomIndex = random.nextInt(breastPlate.size());
                individual.setBreastplate(breastPlate.get(randomIndex));
            }
            case WEAPON: {
                List<Equipment> weapons = alleles.getWeapons();
                int randomIndex = random.nextInt(weapons.size());
                individual.setWeapon(weapons.get(randomIndex));
            }
            case GAUNTLET: {
                List<Equipment> gauntlets = alleles.getGauntlets();
                int randomIndex = random.nextInt(gauntlets.size());
                individual.setGauntlet(gauntlets.get(randomIndex));
            }
            case BOOTS: {
                List<Equipment> boots = alleles.getBoots();
                int randomIndex = random.nextInt(boots.size());
                individual.setBoots(boots.get(randomIndex));
            }
        }
    }
}
