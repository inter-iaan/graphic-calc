package com.alfredointerian.calculadoragrafica.core;

/**
 * Contiene la información de una Variable física para el programa
 * @author José Alfredo Interián Polanco
 */
public class Variable {
    private double value;
    private final String shortName;
    private final String longName;
    private final String unit;
    private boolean fixed;

    public Variable(String shortName, String longName, String unit) {
        this.shortName = shortName;
        this.longName = longName;
        this.unit = unit;
        this.value = 0.0;
        this.fixed = false;
    }

    public double getValue() { return value; }
    
    public void setValue(double value) { this.value = value;    }

    public String getShortName() { return shortName; }
    public String getLongName() { return longName; }
    public String getUnit() { return unit; }
    public boolean getFixed () { return fixed; }
    
    public void setFixed (boolean fixed) { this.fixed = fixed; }

    @Override
    public String toString() {
        return String.format("%s (%s) = %.4f [%s]", shortName, longName, value, unit != null ? unit : "");
    }
}