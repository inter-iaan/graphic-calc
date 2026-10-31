package com.alfredointerian.calculadoragrafica.viewmodel;

import com.alfredointerian.calculadoragrafica.core.Variable;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * @author José Alfredo Interián Polanco
 * Posiciona los elementos de una clase Variable para que se puedan mostrar en la UI
 * @see Variable
 */
public class VariableView extends JPanel {
    /**
     * Muestra los elementos en una disposición específica para su control.
     * Estrictamente relacionado con la dirección de memoria original.
     * 
     * Nota: Cuando se edita el valor de la variable por el campo de texto, un evento actualiza el valor almacenado en variable automáticamente.
     * @param var 
     * @param evt 
     */
    public VariableView(Variable var, ResultEvent evt) {
        referredVariable = var;
        errorLabel = new JLabel("Verifique este campo de texto.");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        title = new JLabel(var.getLongName());
        subtitle = new JLabel(var.getShortName());
        value = new JTextField(15);
        unit = new JLabel(var.getUnit());
        fixed = new JRadioButton("Referencia");
        
        Font segoeLight = new Font("Segoe UI", 0, 14);
        
        title.setFont(segoeLight);
        subtitle.setFont(subtitle.getFont().deriveFont(Font.ITALIC, 14.0f));
        unit.setFont(segoeLight);
        value.setFont(segoeLight);
        fixed.setFont(segoeLight);

        value.addActionListener((ActionEvent e) -> {
            String tfContent = value.getText();
            if (isValid(tfContent)) {
                var.setValue(Double.parseDouble(tfContent));
                errorLabel.setVisible(false);
            } else {
                errorLabel.setVisible(true);
            }
        });
        
        AbstractDocument doc = (AbstractDocument) value.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (string == null) return;
                super.insertString(fb, offset, string.replaceAll("[^0-9.]", ""), attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (text == null) return;
                super.replace(fb, offset, length, text.replaceAll("[^0-9.]", ""), attrs);
            }
        });
        
        value.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { onUpdateText(evt); }

            @Override
            public void removeUpdate(DocumentEvent e) { onUpdateText(evt); }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4); 

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(errorLabel, gbc);

        gbc.gridwidth = 1;

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));

        headerPanel.add(title);
        headerPanel.add(Box.createHorizontalStrut(5)); 
        headerPanel.add(subtitle);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(headerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.LINE_START;
        this.add(value, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(unit, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1; 
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(fixed, gbc);
    }
    
    private void onUpdateText (ResultEvent evt) {
        try {
            if (!ignored) {
                String text = value.getText();
                referredVariable.setValue((!text.isEmpty() ? Double.parseDouble(text) : 0));
                evt.notifyListeners();
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "No se pueden ejecutar operaciones si los campos están vacíos.");
        }
    }
    
    /**
     * Habilita la visualización de la variable como una de resultados.
     */
    public void ignore () {
        fixed.setVisible(false);
        value.setEditable(false);
        ignored = true;
    }
    
    /**
     * Verifica si una cadena no está vacía y si contiene únicamente valores numéricos
     * @return Integridad de la cadena dada.
     */
    private boolean isValid (String text) { return (!text.isBlank() && text.matches("\\d+")); }
    
    public void setButtonGroup (ButtonGroup fixedGroup) { 
        buttonGroup = fixedGroup; 
        fixedGroup.add(this.fixed);
    }
    
    public Variable getReferredVariable () { return referredVariable; }
    public JRadioButton getFixed () { return fixed; }
    public JTextField getValue () { return value; }
    
    private final JLabel errorLabel;
    private final JLabel title;
    private final JLabel subtitle;
    private final JTextField value;
    private final JLabel unit;
    private final JRadioButton fixed;
    private ButtonGroup buttonGroup;
    private final Variable referredVariable;
    private boolean ignored = false;
}
