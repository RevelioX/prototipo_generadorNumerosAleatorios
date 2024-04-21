package backend.org.boundary;

import backend.org.PruebasBondad.PruebaChiCuadrado;
import backend.org.PruebasBondad.PruebaKs;
import org.apache.commons.math3.stat.Frequency;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Histograma {
    private final Map<String, Long> distributionMap;
    private final int numIntervalos;

    public Histograma( List<Double> dataSet, int numIntervalos) {
      distributionMap = new TreeMap<>();
//      this.classWidth = widthClassCalc(dataSet);
      this.numIntervalos = numIntervalos;
      processRawData(dataSet);

      List<Map.Entry<String, Long>> arr = new ArrayList<>(distributionMap.entrySet());
      int n = arr.size();
      for (int i = 0; i < n - 1; i++) {
        boolean swapped = false;
        for (int j = 0; j < n - i - 1; j++) {
          String key = arr.get(j).getKey();
          String[] parts = key.split("-");
          String secondPart = parts[1];
          double segundoElementoInt = Double.parseDouble(secondPart);

          String key2 = arr.get(j + 1).getKey();
          String[] parts2 = key2.split("-");
          String secodPart2 = parts2[0];
          double segundoElementoInt2 = Double.parseDouble(secodPart2);

          if (segundoElementoInt > segundoElementoInt2) {
            Map.Entry<String, Long> temp = arr.get(j);
            arr.set(j, arr.get(j + 1));
            arr.set(j + 1, temp);
            swapped = true;
          }
        }
        if (!swapped)
          break;
      }

      System.out.println(arr);



      List<String> xData = new ArrayList<>();
      List<Long> yData = new ArrayList<>();
      for (Map.Entry<String, Long> entry : arr) {
        String key = entry.getKey();
        Long yValue = entry.getValue();
        xData.add(key);
        yData.add(Long.parseLong(String.valueOf(yValue)));
      }
      PruebaChiCuadrado pruebaChiCuadrado = new PruebaChiCuadrado();
      pruebaChiCuadrado.calculoChi(xData, yData);
      PruebaKs pruebaKs = new PruebaKs();
      pruebaKs.calculoKs(xData, yData);

      CategoryChart chart = buildChart(xData, yData);
      JTable table = buildTable(xData, yData);
      displayChartAndTable(chart, table);
    }



  private CategoryChart buildChart(List<String> xData, List<Long> yData) {

      CategoryChart chart = new CategoryChartBuilder().width(1000).height(600)
              .title("Distribucion")
              .xAxisTitle("Intervalos")
              .yAxisTitle("Frecuencia")
              .build();

      chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
      chart.getStyler().setAvailableSpaceFill(0.99);
      chart.getStyler().setOverlapped(true);
      chart.getStyler().setXAxisLabelRotation(45);


      chart.addSeries("frecuencia", xData, yData);

      return chart;
    }
  private JTable buildTable(List<String> xData, List<Long> yData) {
    String[] columnNames = {"Intervalo", "Frecuencia"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    for (int i = 0; i < xData.size(); i++) {
      model.addRow(new Object[]{xData.get(i), yData.get(i)});
    }
    return new JTable(model);
  }

  private void displayChartAndTable(CategoryChart chart, JTable table) {
    JPanel chartPanel = new XChartPanel<>(chart);
    JScrollPane tableScrollPane = new JScrollPane(table);
    tableScrollPane.setPreferredSize(new Dimension(400, 600));

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanel, tableScrollPane);
    splitPane.setDividerLocation(900);

    JFrame frame = new JFrame("Distribución y Frecuencia");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(splitPane);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void processRawData(List<Double> datasetList) {
    Frequency frequency = new Frequency();
    datasetList.forEach(frequency::addValue);

    Optional<Double> max = datasetList.stream().max(Double::compareTo);
    Optional<Double> min = datasetList.stream().min(Double::compareTo);

    if (max.isPresent() && min.isPresent()) {
      double rango = max.get() - min.get();
      double anchoIntervalo = rango / numIntervalos;

      for (int i = 0; i < numIntervalos; i++) {
        double lowerBoundary = min.get() + (i * anchoIntervalo);
        double upperBoundary = lowerBoundary + anchoIntervalo;

        long observationFrequency = 0;
        for (Double value : datasetList) {
          if (value >= lowerBoundary && value < upperBoundary) {
            observationFrequency++;
          }
        }

        String bin = String.format("%.2f-%.2f", lowerBoundary, upperBoundary).replace(",", ".");
        distributionMap.put(bin, observationFrequency);
      }
    }
  }
}
