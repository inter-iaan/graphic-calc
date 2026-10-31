package com.alfredointerian.calculadoragrafica.viewmodel;

import com.alfredointerian.calculadoragrafica.logic.Operator;
import java.util.ArrayList;

/**
 *
 * @author José Alfredo Interián Polanco
 * Intermediario entre la vista del usuario y la información obtenida mediante la base de datos
 */
public class StartViewModel {
    private ArrayList<Operator> operators = new ArrayList<>();
    
    public StartViewModel() {
        operators = new ArrayList<>() {{
            add(new Operator("Movimiento Rectilineo Uniforme", "${v}(Velocidad)[m/s] = ${d}(Distancia)[m] / ${t}(Tiempo)[s]"));
            add(new Operator("Movimiento Rectilineo Uniformemente Acelerado", "${a}(Aceleración)[m/s²] = (${V_final}(Velocidad Final)[m/s] - ${V_inicial}(Velocidad Inicial)[m/s])/(${t_f}(Tiempo final)[s] - ${t_i}(Tiempo inicial)[s]) "));
            add(new Operator("Movimiento Circular Uniforme", "${v} = ${d} / ${t}"));
            add(new Operator("Reflexión", "${theta_i}(Ángulo de Incidencia)[ ] = ${theta_r}(Ángulo de Reflexión)[ ]"));
        }};
    }

    public ArrayList<Operator> getOperators() {
        return operators;
    }
}
