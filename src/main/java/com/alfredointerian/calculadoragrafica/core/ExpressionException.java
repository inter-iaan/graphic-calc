package com.alfredointerian.calculadoragrafica.core;

/**
 *
 * @author José Alfredo Interián Polanco
 * Tira excepciones en casos específicos en las que las expresiones dadas son inváldas.
 */
public class ExpressionException extends Exception {
    public ExpressionException(String message) {
        super(message);
    }
}
