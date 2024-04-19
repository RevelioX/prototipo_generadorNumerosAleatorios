package backend.org.boundary;

import org.apache.commons.math3.stat.Frequency;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;
import java.util.*;

public class Histograma {
    private final Map<String, Long> distributionMap;
//    private final double classWidth;
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

      CategoryChart chart = buildChart(xData, yData);
      new SwingWrapper<>(chart).displayChart();



    }

//  private double widthClassCalc(List<Double> valores){
//      Optional<Double> max = valores.stream().max(Double::compareTo);
//      Optional<Double> min = valores.stream().min(Double::compareTo);
//      if (max.isPresent()){
//        double rango = max.get() - min.get();
//        int cantidad = (int) Math.round(Math.sqrt(valores.size()));
//        System.out.println(cantidad);
//        System.out.println(rango);
//        return (rango/cantidad);
//      }
//      return 0;
//  }

  private CategoryChart buildChart(List<String> xData, List<Long> yData) {

      CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
              .title("Distribucion")
              .xAxisTitle("Intervalos")
              .yAxisTitle("Frecuencia")
              .build();

      chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
      chart.getStyler().setAvailableSpaceFill(0.99);
      chart.getStyler().setOverlapped(true);


      chart.addSeries("frecuencia", xData, yData);

      return chart;
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

//  private void updateDistributionMap(double lowerBoundary, String bin, long observationFrequency) {
////    int prevLowerBoundary = (lowerBoundary > classWidth) ? lowerBoundary - classWidth : 0;
////    String prevBin = prevLowerBoundary + "-" + lowerBoundary;
////
////    if (!distributionMap.containsKey(prevBin)) {
////      distributionMap.put(prevBin, 0L);
////    }
//
//    if (!distributionMap.containsKey(bin)) {
//      distributionMap.put(bin, observationFrequency);
//    } else {
//      long oldFrequency = distributionMap.get(bin);
//      distributionMap.put(bin, oldFrequency + observationFrequency);
//    }
//  }


}
