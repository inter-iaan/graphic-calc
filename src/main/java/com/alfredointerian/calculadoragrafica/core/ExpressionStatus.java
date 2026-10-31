package com.alfredointerian.calculadoragrafica.core;

/**
 *
 * @author José Alfredo Interián Polanco
 * Indica el estado en el que se encuentra el lector de la expresión
 * 
 * NOTHING: Estado inicial, no se ha leído ningún valor o se espera una nueva lectura
 * READING_VAR: Se encuentra leyendo una variable de la expresión
 * SUM: Encontró una operación de suma
 * SUB: Encontró una operación de resta
 */
public enum ExpressionStatus {
    NOTHING, READING_VAR, SUM, SUB;
}
