package src.models;


public class Gen {
    private final double height;
    private final Equipment helm, breastplate, weapon, gauntlet, boots;

    public Gen(double height, Equipment helm, Equipment breastplate, Equipment weapon, Equipment gauntlet, Equipment boots){
        this.height = height;
        this.helm = helm;
        this.breastplate = breastplate;
        this.weapon = weapon;
        this.gauntlet = gauntlet;
        this.boots = boots;
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
}
