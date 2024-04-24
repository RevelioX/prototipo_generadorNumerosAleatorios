package backend.org;

import backend.org.boundary.Histograma;
import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosExponencial;
import backend.org.generators.GeneradorNumerosNormales;
import backend.org.generators.GeneradorNumerosUniformes;
import java.util.List;
import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {
    private JTextField intervalField;
    private JButton generateButton;
    private JTextArea resultArea;
    private JPanel chartPanel;
    private JComboBox<String> generatorSelector;
    private JTextField param1Field;
    private JTextField param2Field;
    private JLabel param1Label;
    private JLabel param2Label;

    public Main() {
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



        generateButton = new JButton("Generar");

        inputPanel.add(generateButton);
        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.SOUTH);

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
            /*param1Field.setText("0");
            param2Field.setText("0");*/
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
        try {
            int intervalos = Integer.parseInt(intervalField.getText());
            double param1 = Double.parseDouble(param1Field.getText());
            double param2 = param2Field.isVisible() ? Double.parseDouble(param2Field.getText()) : 1.0;
            String selectedGenerator = (String) generatorSelector.getSelectedItem();
            System.out.println(selectedGenerator);
            new Histograma(simulateData(), intervalos,  param1,  param2, selectedGenerator);

            chartPanel.validate();


        } catch (NumberFormatException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
        }
    }

    private List<Double> simulateData() {

        String selectedGenerator = (String) generatorSelector.getSelectedItem();
        double param1 = Double.parseDouble(param1Field.getText());
        double param2 = param2Field.isVisible() ? Double.parseDouble(param2Field.getText()) : 1.0;

        Generador generador;
        switch(selectedGenerator){
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
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}