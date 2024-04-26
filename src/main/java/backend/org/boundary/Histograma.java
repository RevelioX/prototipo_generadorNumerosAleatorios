package backend.org.boundary;

import backend.org.intervalos.Intervalos;
import backend.org.PruebasBondad.PruebaChiCuadrado;
import org.apache.commons.math3.stat.Frequency;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.List;
import java.util.Map;

public class Histograma {
  private final Map<String, Long> distributionMap;
  private final int numIntervalos;

  private List<String> xData = new ArrayList<>();
  private List<Long> yData = new ArrayList<>();

  public Histograma(List<Double> dataSet, int numIntervalos, double media, Double desviacion, String tipoDistribucion) {
    distributionMap = new TreeMap<>();
    this.numIntervalos = numIntervalos;
    processRawData(dataSet);

    List<Map.Entry<String, Long>> arr = new ArrayList<>(distributionMap.entrySet());
    Intervalos intervalos = new Intervalos();
    List<Map.Entry<String, Long>> intervalos1 = intervalos.calcularIntervalos(arr);

    for (Map.Entry<String, Long> entry : intervalos1) {
      String key = entry.getKey();
      Long yValue = entry.getValue();
      xData.add(key);
      yData.add(yValue);
    }

    PruebaChiCuadrado pruebaChiCuadrado = new PruebaChiCuadrado();
    pruebaChiCuadrado.calculoChi(xData, yData, tipoDistribucion, desviacion, media);

  }

  public CategoryChart buildChart() {
    CategoryChart chart = new CategoryChartBuilder().width(1000).height(600)
            .title("Distribucion")
            .xAxisTitle("Intervalos")
            .yAxisTitle("Frecuencia")
            .build();

    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
    chart.getStyler().setAvailableSpaceFill(0.99);
    chart.getStyler().setOverlapped(true);
    chart.getStyler().setXAxisLabelRotation(45);

    chart.addSeries("Frecuencia", xData, yData);

    return chart;
  }

  public JTable buildTable() {
    String[] columnNames = {"Intervalo", "Frecuencia"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    for (int i = 0; i < xData.size(); i++) {
      model.addRow(new Object[]{xData.get(i), yData.get(i)});
    }
    return new JTable(model);
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
        String bin = formatInterval(lowerBoundary, upperBoundary);
        distributionMap.put(bin, observationFrequency);
      }
    }
  }

  private String formatInterval(double start, double end) {
    String formattedStart = formatNumber(start);
    String formattedEnd = formatNumber(end);
    return formattedStart + " - " + formattedEnd;
  }

  private String formatNumber(double number) {
    String formatted = String.format(Locale.US, "%.2f", number);
    if (number < 0) {
      formatted = "(" + formatted + ")";
    }
    return formatted;
  }

}