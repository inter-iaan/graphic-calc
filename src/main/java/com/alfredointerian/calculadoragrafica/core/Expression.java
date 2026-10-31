package com.alfredointerian.calculadoragrafica.core;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Google Gemini AI
 * @author José Alfredo Interián Polanco
 * Evalúa una expresión matemática dada y la convierte en una función programable.
 */
public class Expression {
    private Variable resultVariable;
    private final List<Variable> inputVariables;
    private String rawExpressionRight;

    private static final Pattern VAR_PATTERN = 
        Pattern.compile("\\$\\{([^}]+)\\}(?:\\(([^)]+)\\))?(?:\\[([^\\]]+)\\])?");

    public Expression(String exp) throws ExpressionException {
        this.inputVariables = new ArrayList<>();
        parseEquation(exp);
    }

    /**
     * Parsea la cadena completa y construye la estructura interna.
     */
    private void parseEquation(String exp) throws ExpressionException {
        if (exp == null || !exp.contains("=")) {
            throw new ExpressionException("La expresión debe contener un signo de igualdad '=' para definir la variable de salida.");
        }

        String[] parts = exp.split("=", 2);
        String leftHand = parts[0].trim();
        String rightHand = parts[1].trim();

        Matcher leftMatcher = VAR_PATTERN.matcher(leftHand);
        if (leftMatcher.find()) {
            this.resultVariable = createVariableFromMatcher(leftMatcher);
        } else {
            throw new ExpressionException("La variable de salida en el lado izquierdo de la ecuación es inválida.");
        }

        Matcher rightMatcher = VAR_PATTERN.matcher(rightHand);
        while (rightMatcher.find()) {
            inputVariables.add(createVariableFromMatcher(rightMatcher));
        }

        if (inputVariables.isEmpty()) {
            throw new ExpressionException("No se detectaron variables de entrada en el lado derecho de la ecuación.");
        }

        this.rawExpressionRight = rightHand;
    }

    private Variable createVariableFromMatcher(Matcher matcher) {
        String shortName = matcher.group(1);
        String longName = matcher.group(2) != null ? matcher.group(2) : shortName;
        String unit = matcher.group(3) != null ? matcher.group(3) : "";
        return new Variable(shortName, longName, unit);
    }

    /**
     * Evalúa la función matemática asignando los valores dados a las variables de entrada.
     * 
     * @param values Valores correspondientes a las variables de entrada en orden de aparición
     * @return Variable con el resultado calculado
     * @throws ExpressionException si el número de valores no coincide o hay errores matemáticos
     */
    public Variable evaluate(double... values) throws ExpressionException {
        if (values.length != inputVariables.size()) {
            throw new ExpressionException(String.format(
                "Cantidad de valores incorrecta. Se esperaban %d valores, pero se recibieron %d.",
                inputVariables.size(), values.length
            ));
        }

        Map<String, Double> variableValues = new HashMap<>();
        for (int i = 0; i < inputVariables.size(); i++) {
            inputVariables.get(i).setValue(values[i]);
            variableValues.put(inputVariables.get(i).getShortName(), values[i]);
        }

        Matcher matcher = VAR_PATTERN.matcher(rawExpressionRight);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String shortName = matcher.group(1);
            Double val = variableValues.get(shortName);
            matcher.appendReplacement(sb, String.valueOf(val));
        }
        matcher.appendTail(sb);

        double computedResult = evalMath(sb.toString());
        resultVariable.setValue(computedResult);

        return resultVariable;
    }

    public static Variable format(String exp, double values[]) throws ExpressionException {
        Expression expression = new Expression(exp);
        return expression.evaluate(values);
    }

    public Variable getResultVariable() { return resultVariable; }
    public List<Variable> getInputVariables() { return inputVariables; }
    
    private double evalMath(final String str) throws ExpressionException {
        try {
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < str.length()) throw new RuntimeException("Carácter inesperado: " + (char)ch);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    while (true) {
                        if      (eat('+')) x += parseTerm();
                        else if (eat('-')) x -= parseTerm();
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    while (true) {
                        if      (eat('*')) x *= parseFactor();
                        else if (eat('/')) {
                            double denominator = parseFactor();
                            if (denominator == 0) throw new ArithmeticException("División entre cero.");
                            x /= denominator;
                        }
                        else return x;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return +parseFactor();
                    if (eat('-')) return -parseFactor();

                    double x;
                    int startPos = this.pos;
                    if (eat('(')) {
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(str.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Símbolo no reconocido: " + (char)ch);
                    }

                    if (eat('^')) x = Math.pow(x, parseFactor());

                    return x;
                }
            }.parse();
        } catch (Exception e) {
            throw new ExpressionException("Error al evaluar la expresión aritmética: " + e.getMessage());
        }
    }
}