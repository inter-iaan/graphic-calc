package com.alfredointerian.calculadoragrafica.logic;

/**
 *
 * @author José Alfredo Interián Polanco
 * Contiene los datos de una operación/fórmula matemática
 */
public class Operator {
    private String name;
    private String function;

    public Operator(String name, String function) {
        this.name = name;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public String getFunction() {
        return function;
    }
}
