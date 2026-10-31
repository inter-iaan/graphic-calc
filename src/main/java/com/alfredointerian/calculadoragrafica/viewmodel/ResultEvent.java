package com.alfredointerian.calculadoragrafica.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Alfredo Interián Polanco
 * Almacena los eventos relacionados con el evento de resultados.
 */
public class ResultEvent {
    private List<ResultListener> listeners;
    
    public ResultEvent() { this.listeners = new ArrayList<>(); }
    
    public void addResultListener (ResultListener listener) {
        listeners.add(listener);
    }
    
    public void notifyListeners () {
        listeners.forEach(listener -> {
            listener.calculate();
        });
    }
}
