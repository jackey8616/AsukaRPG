package info.clo5de.asukarpg.item;

public class ExEnchant {

    private String name;
    private double ability, effect;

    ExEnchant (String name, double ability, double effect) {
        this.name = name;
        this.ability = ability;
        this.effect = effect;
    }

    ExEnchant (String name, double ability) {
        this(name, ability, 0);
    }

    public String getName () {
        return this.name;
    }

    public double getAbility () {
        return this.ability;
    }

    public double getEffect () {
        return this.effect;
    }
}