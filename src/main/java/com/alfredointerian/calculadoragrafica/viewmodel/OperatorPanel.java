package com.alfredointerian.calculadoragrafica.viewmodel;

import com.alfredointerian.calculadoragrafica.core.Expression;
import com.alfredointerian.calculadoragrafica.core.ExpressionException;
import com.alfredointerian.calculadoragrafica.core.Variable;
import com.alfredointerian.calculadoragrafica.logic.Operator;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author José Alfredo Interián Polanco
 * Crea la estructura del JPanel necesario para mostrar una expresión dada
 */

public class OperatorPanel extends JPanel {
    public OperatorPanel (String title, List<Variable> userVariables, Variable resultVar, Operator operator) {
        ResultEvent resultEvent = new ResultEvent();
    
        this.operator = operator;
            
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        
        fixedButtonGroup = new ButtonGroup();
        
        gbc.anchor = GridBagConstraints.LINE_START;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        titleLabel = new JLabel(title);
        userVariablesLabel = new JLabel("Variables de entorno");
        resultVariableLabel = new JLabel("Conclusiones");
        
        titleLabel.setFont(new Font("Segoe UI Semibold", 0, 18));
        userVariablesLabel.setFont(new Font("Segoe UI Light", 0, 14));
        resultVariableLabel.setFont(new Font("Segoe UI Light", 0, 14));
        
        this.add(titleLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        this.add(userVariablesLabel, gbc);
        
        int i = 0;
        for (; i < userVariables.size(); i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 2;
            
            VariableView variableView = new VariableView(userVariables.get(i), resultEvent);
            
            variableView.setButtonGroup(fixedButtonGroup);
            
            this.add(variableView, gbc);
            
            if (i == 0) {
                variableView.getFixed().setSelected(true);
            }
        }
        
        gbc.gridx = 0;
        gbc.gridy = i + 3;
        
        this.add(resultVariableLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = i + 4;
        
        resultView = new VariableView(resultVar, resultEvent);
        resultView.getValue().setText("0.0");
        resultView.ignore();
        
        resultEvent.addResultListener(() -> {
            Variable result;
            try {
                ArrayList<Double> valuesList = new ArrayList<>();
                
                for (Variable variable : userVariables) {
                    valuesList.add(variable.getValue());
                }
                
                double[] values = valuesList.stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray();

                result = Expression.format(operator.getFunction(), values);
                resultView.getValue().setText(result.getValue()+"");

            } catch (ExpressionException ex) {
                resultView.getValue().setText("0.0");
                System.err.println("No se pudo realizar el formateo de la expresión correspondiente. Finalizando ejecución.\n\n" + ex.getMessage());
            }            
        });
        
        this.add(resultView, gbc);
    }
        
    private final VariableView resultView;
    private final JLabel titleLabel;
    private final JLabel userVariablesLabel;
    private final JLabel resultVariableLabel;
    private final ButtonGroup fixedButtonGroup;
    private Operator operator;
}