package backend.org.boundary;

import org.apache.commons.math3.stat.Frequency;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;
import java.util.*;

public class Histograma {
    private final Map<String, Long> distributionMap;
    private final int classWidth;

    public Histograma(int classWidth, List<Double> dataSet) {
      distributionMap = new TreeMap<>();
      this.classWidth = classWidth;
      Map distributionMap = processRawData(dataSet);
      List yData = new ArrayList();
      yData.addAll(distributionMap.values());
      List xData = Arrays.asList(distributionMap.keySet().toArray());
      CategoryChart chart = buildChart(xData, yData);
      new SwingWrapper<>(chart).displayChart();

    }

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

    private Map processRawData(List<Double> datasetList) {

      Frequency frequency = new Frequency();
      datasetList.forEach(d -> frequency.addValue(Double.parseDouble(d.toString())));
      datasetList.stream()
              .map(d -> Double.parseDouble(d.toString()))
              .distinct()
              .forEach(observation -> {
                long observationFrequency = frequency.getCount(observation);
                int upperBoundary = (observation > classWidth)
                        ? Math.multiplyExact( (int) Math.ceil(observation / classWidth), classWidth)
                        : classWidth;
                int lowerBoundary = (upperBoundary > classWidth)
                        ? Math.subtractExact(upperBoundary, classWidth)
                        : 0;

                String bin = lowerBoundary + "-" + upperBoundary;

                updateDistributionMap(lowerBoundary, bin, observationFrequency);

              });
      return distributionMap;
    }

  private void updateDistributionMap(int lowerBoundary, String bin, long observationFrequency) {
    int prevLowerBoundary = (lowerBoundary > classWidth) ? lowerBoundary - classWidth : 0;
    String prevBin = prevLowerBoundary + "-" + lowerBoundary;

    if (!distributionMap.containsKey(prevBin)) {
      distributionMap.put(prevBin, 0L);
    }

    if (!distributionMap.containsKey(bin)) {
      distributionMap.put(bin, observationFrequency);
    } else {
      long oldFrequency = distributionMap.get(bin);
      distributionMap.put(bin, oldFrequency + observationFrequency);
    }
  }


}
