
package com.alfredointerian.calculadoragrafica;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;

public class EjemploJFreeChart extends JFrame {

    public EjemploJFreeChart(String title) {
        super(title);

        // 1. Crear el conjunto de datos (Dataset)
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Ventas", "Enero");
        dataset.addValue(15, "Ventas", "Febrero");
        dataset.addValue(30, "Ventas", "Marzo");
        dataset.addValue(22, "Ventas", "Abril");

        // 2. Crear el gráfico de líneas
        JFreeChart chart = ChartFactory.createLineChart(
                "Rendimiento Mensual",  // Título
                "Mes",                  // Etiqueta Eje X
                "Cantidad",             // Etiqueta Eje Y
                dataset                 // Datos
        );

        // 3. Colocar el gráfico en un panel de Swing
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public static void main(String[] args) {
        EjemploJFreeChart ventana = new EjemploJFreeChart("Ejemplo de Gráfico de Líneas");
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}