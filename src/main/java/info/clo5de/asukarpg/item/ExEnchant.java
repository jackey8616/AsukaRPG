package info.clo5de.asukarpg.item;

import java.util.Random;

public class ExEnchant {

    private String name;
    private double ability, effect;

    public ExEnchant (String name, double ability, double effect) {
        this.name = name;
        this.ability = ability;
        this.effect = effect;
    }

    public ExEnchant (String name, int ability, int effect) {
        this(name, ability / 100.0D, effect / 100.0D);
    }

    public ExEnchant (String name, double ability) {
        this(name, ability, 0.0D);
    }

    public ExEnchant (String name, int ability) {
        this(name, ability / 100.0D, 0.0D);
    }

    public String getName () {
        return this.name;
    }

    public double getAbility () {
        return this.ability;
    }

    public void setAbility (double ability) {
        this.ability = ability;
    }

    public double getEffect () {
        return this.effect;
    }

    public void setEffect (double effect) {
        this.effect = effect;
    }

    public boolean isTriggered () {
        return new Random().nextDouble() <= this.ability;
    }
}