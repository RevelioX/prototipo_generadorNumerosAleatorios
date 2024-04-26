package backend.org.PruebasBondad;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;


public class PruebaChiCuadrado {
  private String distribucion;
  private ArrayList<Double> frecuenciasEsperadas = new ArrayList<>();
  private Double media;
  private Double desviacion;
  private int n;

  public Double calculoChi(List<String> intervalos, List<Long> frecuencias, String distrib, Double desv, Double med) {
    this.distribucion = distrib;
    this.desviacion = desv;
    this.media = med;
    this.n = (int) frecuencias.stream().mapToLong(Long::longValue).sum();
    Double chiTabla = 0.0;

    ArrayList<Double> frecuenciasEsperadasTemporales = new ArrayList<>();
    List<String> intervalosCombinados = new ArrayList<>();
    List<Long> frecuenciasObservadasCombinadas = new ArrayList<>();




      if (distribucion.equals("Normal")) {
        for (int i = 0; i < intervalos.size(); i++) {
          String[] partes = intervalos.get(i).split(" - ");
          Double limInf = parseLimit(partes[0]);
          Double limSup = parseLimit(partes[1]);

          Double marcaClase = (limInf + limSup) / 2;
          Double probabilidad = (1 / (desv * Math.sqrt(2 * Math.PI))) *
                  Math.exp(-0.5 * Math.pow((marcaClase - med) / desv, 2))*(limSup-limInf);
          Double frecuenciaEsperada = probabilidad * n;

          frecuenciasEsperadasTemporales.add(frecuenciaEsperada);
          intervalosCombinados.add(intervalos.get(i));
          frecuenciasObservadasCombinadas.add(frecuencias.get(i));
        }

      // Asegurar que todas las frecuencias esperadas superen un mínimo establecido antes de imprimir los resultados
      asegurarFrecuenciasMinimas(frecuenciasEsperadasTemporales, intervalosCombinados, frecuenciasObservadasCombinadas, 5);
      int gradosLibertad = intervalosCombinados.size() - 1 - 0;
      chiTabla = chiTabla(gradosLibertad,0.05);



  } else if (distribucion.equalsIgnoreCase("Exponencial")) {
        double lambda = media;
        for (int i = 0; i < intervalos.size(); i++) {
          String[] partes = intervalos.get(i).split("-");
          double limInf = Double.parseDouble(partes[0]);
          double limSup = Double.parseDouble(partes[1]);


          Double probabilidad = (1- Math.exp(-lambda * limSup)) - (1 - Math.exp(-lambda * limInf));
          if (probabilidad <= 0) {
            continue;
          }
          Double frecuenciaEsperada = probabilidad * n;

          frecuenciasEsperadasTemporales.add(frecuenciaEsperada);
          intervalosCombinados.add(intervalos.get(i));
          frecuenciasObservadasCombinadas.add(frecuencias.get(i));
        }

        asegurarFrecuenciasMinimas(frecuenciasEsperadasTemporales, intervalosCombinados, frecuenciasObservadasCombinadas, 5);

        int gradosLibertad = intervalosCombinados.size() - 1 - 0;
        chiTabla = chiTabla(gradosLibertad,0.05);



    } else if (distribucion.equals("Uniforme")) {
      for (int i = 0; i < intervalos.size(); i++) {
        Double frecuenciaEsperada = (double) (n / intervalos.size());

        frecuenciasEsperadasTemporales.add(frecuenciaEsperada);
        intervalosCombinados.add(intervalos.get(i));
        frecuenciasObservadasCombinadas.add(frecuencias.get(i));

      }

      asegurarFrecuenciasMinimas(frecuenciasEsperadasTemporales, intervalosCombinados, frecuenciasObservadasCombinadas, 5);
      int gradosLibertad = intervalosCombinados.size() - 1 - 0;
      chiTabla = chiTabla(gradosLibertad,0.05);
    }

    Double chiCalculado = 0.0;
    for (int i = 0; i < frecuenciasEsperadasTemporales.size(); i++) {
      chiCalculado += Math.pow(frecuenciasObservadasCombinadas.get(i) - frecuenciasEsperadasTemporales.get(i), 2) /
              frecuenciasEsperadasTemporales.get(i);
    }

    for (int i = 0; i < frecuenciasEsperadasTemporales.size(); i++) {
      System.out.println("Intervalo: " + intervalosCombinados.get(i) +
              ", Frecuencia Esperada: " + frecuenciasEsperadasTemporales.get(i) +
              ", Frecuencia Observada: " + frecuenciasObservadasCombinadas.get(i));
    }

    System.out.println("Chi calculado: " + chiCalculado);
    System.out.println("Calculo Chi tabla: "+chiTabla);

    if (chiCalculado < chiTabla)  {
      System.out.println("La hipotesis se acepta ");
    } else{
      System.out.println("La hipotesis se rechaza ");
    }

    PruebaKs pruebaks = new PruebaKs(frecuenciasEsperadasTemporales,frecuenciasObservadasCombinadas, n);


    return chiCalculado;

  }

  public  double chiTabla(int gradosLibertad, double alfa) {
    // Crear una distribución chi cuadrado
    ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(gradosLibertad);

    // Calcular el valor crítico
    return chiSquaredDistribution.inverseCumulativeProbability(1 - alfa);
  }

  private void asegurarFrecuenciasMinimas(ArrayList<Double> frecuenciasEsperadas, List<String> intervalos, List<Long> frecuenciasObservadas, double minimoEsperado) {
    int i = 0;
    while (i < frecuenciasEsperadas.size()) {
      if (frecuenciasEsperadas.get(i) < minimoEsperado) {
        if (i < frecuenciasEsperadas.size() - 1) {  // Combina hacia adelante si es posible
          frecuenciasEsperadas.set(i, frecuenciasEsperadas.get(i) + frecuenciasEsperadas.remove(i + 1));
          frecuenciasObservadas.set(i, frecuenciasObservadas.get(i) + frecuenciasObservadas.remove(i + 1));
          String nuevoIntervalo = intervalos.get(i).split("-")[0] + "-" + intervalos.remove(i + 1).split("-")[1];
          intervalos.set(i, nuevoIntervalo);
          continue;  // Re-evaluar el mismo índice después de la combinación
        } else if (i > 0) {  // Combina hacia atrás si es el último elemento y el adelante no es posible
          int lastIndex = i;
          frecuenciasEsperadas.set(lastIndex - 1, frecuenciasEsperadas.get(lastIndex - 1) + frecuenciasEsperadas.remove(lastIndex));
          frecuenciasObservadas.set(lastIndex - 1, frecuenciasObservadas.get(lastIndex - 1) + frecuenciasObservadas.remove(lastIndex));
          String nuevoIntervalo = intervalos.get(lastIndex - 1).split("-")[0] + "-" + intervalos.remove(lastIndex).split("-")[1];
          intervalos.set(lastIndex - 1, nuevoIntervalo);
          i--;  // Mueve el índice hacia atrás para re-evaluar el nuevo último elemento
        }
      }
      i++;
    }
  }
  private double parseLimit(String limit) {
    limit = limit.replace("(", "").replace(")", "").trim();
    try {
      return Double.parseDouble(limit);
    } catch (NumberFormatException e) {
      System.err.println("Error parsing number: " + limit);
      return 0;
    }
  }

}
