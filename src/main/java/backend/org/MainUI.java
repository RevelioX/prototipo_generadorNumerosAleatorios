package backend.org;

import backend.org.boundary.Histograma;
import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosExponencial;
import backend.org.generators.GeneradorNumerosNormales;
import backend.org.generators.GeneradorNumerosUniformes;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainUI extends JFrame {
    private JTextField intervalField;
    private JButton generateButton;
    private JPanel chartPanel;
    private JComboBox<String> generatorSelector;
    private JTextField param1Field;
    private JTextField param2Field;
    private JLabel param1Label;
    private JLabel param2Label;
    private CategoryChart chart;
    private JTable table; // Agregado aquí

    public MainUI() {
        super("Generador de Histograma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Ingrese tamaño de intervalo:"));
        intervalField = new JTextField(5);
        inputPanel.add(intervalField);

        inputPanel.add(new JLabel("Seleccione la Distribución:"));
        String[] generators = {"Normal", "Uniforme", "Exponencial"};
        generatorSelector = new JComboBox<>(generators);
        generatorSelector.addActionListener(e -> updateParametersUI());
        inputPanel.add(generatorSelector);

        param1Label = new JLabel("Media:");
        param2Label = new JLabel("Desviación:");
        param1Field = new JTextField("0", 10);
        param2Field = new JTextField("0", 10);
        inputPanel.add(param1Label);
        inputPanel.add(param1Field);
        inputPanel.add(param2Label);
        inputPanel.add(param2Field);

        generateButton = new JButton("Generar Histograma");
        inputPanel.add(generateButton);
        add(inputPanel, BorderLayout.NORTH);

        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);

        generateButton.addActionListener(e -> generateHistogram());
    }

    private void updateParametersUI() {
        String selectedGenerator = (String) generatorSelector.getSelectedItem();
        if ("Normal".equals(selectedGenerator)) {
            param1Label.setText("Media:");
            param2Label.setText("Desviación:");
            param1Field.setText("0");
            param2Field.setText("0");
            param2Field.setVisible(true);
            param2Label.setVisible(true);
        } else if ("Uniforme".equals(selectedGenerator)) {
            param1Label.setText("Mínimo:");
            param2Label.setText("Máximo:");
            param1Field.setText("0");
            param2Field.setText("0");
            param2Field.setVisible(true);
            param2Label.setVisible(true);
        } else {
            param2Field.setVisible(false);
            param2Label.setVisible(false);
            param1Label.setText("Media:");
        }
    }

    private void generateHistogram() {
        String param1Text = param1Field.getText();
        String param2Text = param2Field.getText();
        String intervaloText = intervalField.getText();

        if (!intervaloText.isEmpty() && !param1Text.isEmpty()) {
            int intervalos;
            double param1;
            double param2;
            try {
                intervalos = Integer.parseInt(intervaloText);
                System.out.println("Intervalos: " + intervalos);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing intervaloText: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            try {
                param1 = Double.parseDouble(param1Text);
                System.out.println("Param1: " + param1);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing param1Text: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Verificación para evitar NumberFormatException si param2Text está vacío
            if (!param2Text.isEmpty()) {
                try {
                    param2 = Double.parseDouble(param2Text);
                    System.out.println("Param2: " + param2);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing param2Text: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
            } else {
                param2 = 1.0; // Valor predeterminado si param2Text está vacío
                System.out.println("Param2 (default): " + param2);
            }

            String selectedGenerator = (String) generatorSelector.getSelectedItem();

            List<Double> datos = simulateData(selectedGenerator, param1, param2);
            if (datos != null) {
                Histograma histograma = new Histograma(datos, intervalos, param1, param2, selectedGenerator);
                chart = histograma.buildChart();
                table = histograma.buildTable();
                chartPanel.removeAll();
                chartPanel.add(new XChartPanel<>(chart), BorderLayout.CENTER);
                chartPanel.add(new JScrollPane(table), BorderLayout.EAST);
                revalidate();
                repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos de entrada.");
        }
    }


    private List<Double> simulateData(String selectedGenerator, double param1, double param2) {
        Generador generador;
        switch(selectedGenerator) {
            case "Normal":
                generador = new GeneradorNumerosNormales(param1, param2);
                break;
            case "Uniforme":
                generador = new GeneradorNumerosUniformes(param1, param2);
                break;
            default:
                generador = new GeneradorNumerosExponencial(param1);
                break;
        }
        generador.generarValor(1000000);
        return generador.getAll();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }
}
