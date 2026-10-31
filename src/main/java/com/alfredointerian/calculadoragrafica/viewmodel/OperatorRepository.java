package com.alfredointerian.calculadoragrafica.viewmodel;

import com.alfredointerian.calculadoragrafica.logic.Operator;
import java.util.List;

/**
 *
 * @author José Alfredo Interián Polanco
 * Define los métodos que debe realizar cualquier base de datos que se conecte con la aplicación
 */
public interface OperatorRepository {
    public List<Operator> fetch();
}
